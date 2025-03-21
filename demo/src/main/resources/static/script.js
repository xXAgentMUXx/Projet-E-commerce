document.addEventListener("DOMContentLoaded", () => {
    loadProducts();  // Load products when the page is ready
    document.getElementById("login-form").addEventListener("submit", loginUser); // Add event listener for login form
    document.getElementById("register-form").addEventListener("submit", registerUser); // Add event listener for registration form
    document.getElementById("product-form").addEventListener("submit", addProduct); // Add event listener for product form
    document.getElementById("checkout").addEventListener("click", placeOrder); // Add event listener for checkout button

    checkUserSession(); // Check if the user is logged in
    displayCart(); // Update the cart display
});

// Define the API URL
const API_BASE_URL = "http://localhost:8080";

// Check if the user is logged in and clear the cart if not logged in
function checkUserSession() {
    const userId = localStorage.getItem("userId");
    if (!userId) {
        console.log("Aucun utilisateur connecté. Réinitialisation du panier.");
        localStorage.removeItem("cart");
    } else {
        console.log("Utilisateur connecté, ID :", userId);
    }
}

// User login
async function loginUser(event) {
    event.preventDefault();
    const email = event.target[0].value;
    const password = event.target[1].value;

    try {
        const response = await fetch(`${API_BASE_URL}/users/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });
        if (!response.ok) {
            throw new Error("Adresse email ou mot de passe incorrect");
        }
        const user = await response.json(); // Retrieve user data (ID)
        localStorage.setItem("userId", user.id); // Store user ID in localStorage
        alert("Connexion réussi");
        location.reload(); // Reload the page to refresh the session
    } catch (error) {
        alert("Erreur de connexion: " + error.message);
    }
}

// User registration
async function registerUser(event) {
    event.preventDefault();
    const username = document.getElementById("register-username").value;
    const email = document.getElementById("register-email").value;
    const password = document.getElementById("register-password").value;
    const role = document.getElementById("register-role").value;

    const response = await fetch(`${API_BASE_URL}/users/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, email, password, role })
    });
    if (response.ok) {
        alert("Inscription réussie !");
    } else {
        alert("Erreur d'enregistrement");
    }
}

// Load products from the API
async function loadProducts() {
    try {
        const response = await fetch(`${API_BASE_URL}/products`);
        const products = await response.json();
        displayProducts(products); // Display products after loading
    } catch (error) {
        console.error("Erreur lors du chargement des produits :", error);
    }
}

// Display the products on the page
function displayProducts(products) {
    const productContainer = document.getElementById("product-list");
    productContainer.innerHTML = ""; // Clear the current product list
    products.forEach(product => {
        const productElement = document.createElement("div");
        productElement.classList.add("product");
        productElement.innerHTML = `
            <h3>${product.name}</h3>
            <p>Price: $${product.price}</p>
            <p>Stock: ${product.stock}</p>
            <button onclick="addToCart(${product.id})">Ajouter au panier</button>
        `;
        productContainer.appendChild(productElement);
    });
}

// Add a product via the product form (Admin only)
async function addProduct(event) {
    event.preventDefault();

    const userId = localStorage.getItem("userId");
    const userRole = localStorage.getItem("userRole");

    // Check if the user is logged in and is an admin
    if (!userId || userRole !== "ADMIN") {
        alert("Vous n'êtes pas administrateur. Veuillez vous connecter.");
        return;
    }
    const name = document.getElementById("product-name").value;
    const price = parseFloat(document.getElementById("product-price").value);
    const stock = parseInt(document.getElementById("product-quantity").value);

    try {
        const response = await fetch(`${API_BASE_URL}/admin/products`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "userId": userId
            },
            body: JSON.stringify({ name, price, stock })
        });
        if (!response.ok) {
            throw new Error("Vous n'êtes pas un administrateur, veuillez vous connecter.");
        }
        alert("Produit ajouté avec succès !");
        loadProducts(); // Reload the products list after adding a product
    } catch (error) {
        alert(error.message);
    }
}

// Delete a product (Admin only)
async function deleteProduct(productId) {
    const userId = localStorage.getItem("userId"); 

    if (!userId) {
        alert("Vous devez être connecté en tant qu'administrateur pour supprimer un produit !");
        return;
    }
    const response = await fetch(`${API_BASE_URL}/admin/products/${productId}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "userId": userId 
        }
    });
    if (response.ok) {
        alert("Produit supprimé avec succès !");
        loadProducts(); // Reload the products list after deletion
    } else {
        const errorText = await response.text();
        alert(errorText);
    }
}

// Add a product to the cart
function addToCart(productId) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    const existingProduct = cart.find(item => item.productId === productId);
    if (existingProduct) {
        existingProduct.quantity += 1; // Increment quantity if product already in cart
    } else {
        cart.push({ productId, quantity: 1 }); // Add new product to cart
    }
    localStorage.setItem("cart", JSON.stringify(cart));
    alert("Produit ajouté au panier !");
    displayCart(); // Update the cart display
}

// Display the cart
function displayCart() {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    const cartContainer = document.getElementById("cart-items");
    cartContainer.innerHTML = "";

    if (cart.length === 0) {
        cartContainer.innerHTML = "<p>Votre panier est vide.</p>";
        return;
    }
    cart.forEach(item => {
        const itemElement = document.createElement("div");
        itemElement.classList.add("cart-item");
        
        itemElement.innerHTML = `
            <p>Product ${item.productId} - Quantity: ${item.quantity} </p>
            <button onclick="removeFromCart(${item.productId})">Supprimer</button>
        `;
        cartContainer.appendChild(itemElement);
    });
}

// Place an order
async function placeOrder() {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];

    if (cart.length === 0) {
        alert("Votre panier est vide!");
        return;
    }
    const productIds = cart.map(item => item.productId); // Get Product IDs
    const quantities = cart.map(item => item.quantity); // Get quantities

    let userId = localStorage.getItem("userId");
    if (!userId) {
        alert("Veuillez vous connecter avant de passer une commande.");
        return;
    }

    // Verify that the products exist in the database BEFORE sending the order
    try {
        const response = await fetch(`${API_BASE_URL}/products`);
        const products = await response.json();
        const validProductIds = products.map(product => product.id);

        // Filter out invalid products
        const validCartItems = productIds.filter(id => validProductIds.includes(id));

        if (validCartItems.length !== productIds.length) {
            alert("Certains produits dans votre panier ne sont plus disponibles !");
            checkCartValidity(); // Check for cart validity
            return;
        }
    } catch (error) {
        console.error("Erreur lors de la vérification des produits avant la commande :", error);
        return;
    }
    console.log("Envoi de la commande avec :", { userId, productIds, quantities }); // Log order details

    try {
        const response = await fetch(`${API_BASE_URL}/orders/place`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId: parseInt(userId), productIds, quantities }) // Send quantities as well
        });
        if (!response.ok) {
            throw new Error(await response.text());
        }
        alert("Commande passée avec succès !");
        localStorage.removeItem("cart"); // Clear the cart after successful order
        displayCart();
    } catch (error) {
        console.error("Erreur lors de la passation de la commande :", error);
        alert("Erreur lors de la passation de la commande :" + error.message);
    }
}

// Call this function on page load to validate cart
document.addEventListener("DOMContentLoaded", () => {
    checkCartValidity();
});

// Remove a product from the cart
function removeFromCart(productId) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    cart = cart.filter(item => item.productId !== productId); // Remove product from cart
    localStorage.setItem("cart", JSON.stringify(cart));
    displayCart(); // Update the cart display
    alert("Produit retiré du panier.");
}

// Load and display user orders
async function loadUserOrders() {
    let userId = localStorage.getItem("userId");

    if (!userId) {
        document.getElementById("order-list").innerHTML = "<p>Veuillez vous connecter pour voir vos commandes.</p>";
        return;
    }
    try {
        const response = await fetch(`${API_BASE_URL}/users/${userId}/orders`);
        if (!response.ok) {
            throw new Error("Erreur lors du chargement des commandes");
        }
        const orders = await response.json();
        displayUserOrders(orders);
    } catch (error) {
        console.error("Erreur lors du chargement des commandes :", error);
        document.getElementById("order-list").innerHTML = "<p>Impossible de charger les commandes.</p>";
    }
}

// Display the user's orders
function displayUserOrders(orders) {
    const orderContainer = document.getElementById("order-list");
    orderContainer.innerHTML = "";

    if (orders.length === 0) {
        orderContainer.innerHTML = "<p>No orders placed.</p>";
        return;
    }
    orders.forEach(order => {
        const orderElement = document.createElement("div");
        orderElement.classList.add("order");
    
        orderElement.innerHTML = `
            <h3>Order ID: ${order.orderID}</h3>
            <p>Status: ${order.status}</p>
            <ul>
                ${order.items.map((item, index) => {
                    const productName = item.productname || item.name || "Produit inconnu";
                    const productPrice = item.price || 0;
                    const quantity = order.quantities[index] || 1; 
                    const totalPrice = (productPrice * quantity).toFixed(2); 

                    return `<li>${productName} - Prix unité: ${productPrice}€ (x${quantity}) - Total: ${totalPrice}€</li>`;
                }).join("")}
            </ul>
            <hr>
        `;
        orderContainer.appendChild(orderElement);
    });
}

// Call function to load user orders on page load
document.addEventListener("DOMContentLoaded", () => {
    loadUserOrders();
});
