document.addEventListener("DOMContentLoaded", () => {
    loadProducts();
    document.getElementById("login-form").addEventListener("submit", loginUser);
    document.getElementById("register-form").addEventListener("submit", registerUser);
    document.getElementById("product-form").addEventListener("submit", addProduct);
    document.getElementById("checkout").addEventListener("click", placeOrder);

    checkUserSession(); // Vérifier si l'utilisateur est connecté
    displayCart(); // Mettre à jour l'affichage du panier
});

// Définition de l'URL de l'API
const API_BASE_URL = "http://localhost:8080";

// Vérifier si un utilisateur est connecté et vider le panier si non connecté
function checkUserSession() {
    const userId = localStorage.getItem("userId");
    if (!userId) {
        console.log("Aucun utilisateur connecté. Réinitialisation du panier.");
        localStorage.removeItem("cart");
    } else {
        console.log("Utilisateur connecté, ID:", userId);
    }
}

// Connexion utilisateur
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
            throw new Error("Email ou mot de passe incorrect");
        }

        const user = await response.json(); // Récupérer les données utilisateur (ID)
        localStorage.setItem("userId", user.id); // Stocker l'ID utilisateur
        alert("Connexion réussie !");
        location.reload(); // Recharger la page pour actualiser la session
    } catch (error) {
        alert("Erreur de connexion : " + error.message);
    }
}

// Inscription utilisateur
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
        alert("Erreur d'inscription");
    }
}

// Charger les produits depuis l'API
async function loadProducts() {
    try {
        const response = await fetch(`${API_BASE_URL}/products`);
        const products = await response.json();
        displayProducts(products);
    } catch (error) {
        console.error("Erreur lors du chargement des produits:", error);
    }
}

// Afficher les produits
function displayProducts(products) {
    const productContainer = document.getElementById("product-list");
    productContainer.innerHTML = "";
    products.forEach(product => {
        const productElement = document.createElement("div");
        productElement.classList.add("product");
        productElement.innerHTML = `
            <h3>${product.name}</h3>
            <p>Prix: $${product.price}</p>
            <p>Stock: ${product.stock}</p>
            <button onclick="addToCart(${product.id})">Ajouter au panier</button>
        `;
        productContainer.appendChild(productElement);
    });
}

// Ajouter un produit via le formulaire
async function addProduct(event) {
    event.preventDefault();

    const name = document.getElementById("product-name").value;
    const price = parseFloat(document.getElementById("product-price").value);
    const stock = parseInt(document.getElementById("product-quantity").value);
    const userId = localStorage.getItem("userId"); 

    if (!userId) {
        alert("⛔️ Vous devez être connecté en tant qu'administrateur pour ajouter un produit !");
        return;
    }

    const response = await fetch(`${API_BASE_URL}/admin/products`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "userId": userId 
        },
        body: JSON.stringify({ name, price, stock })
    });

    const data = await response.json();
    if (response.ok) {
        alert("✅ Produit ajouté avec succès !");
        loadProducts();
    } else {
        alert("❌ " + data);
    }
}

async function deleteProduct(productId) {
    const userId = localStorage.getItem("userId"); 

    if (!userId) {
        alert("⛔️ Vous devez être connecté en tant qu'administrateur pour supprimer un produit !");
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
        alert("✅ Produit supprimé avec succès !");
        loadProducts();
    } else {
        const errorText = await response.text();
        alert("❌ " + errorText);
    }
}

// Ajouter un produit au panier
function addToCart(productId) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    const existingProduct = cart.find(item => item.productId === productId);
    if (existingProduct) {
        existingProduct.quantity += 1;
    } else {
        cart.push({ productId, quantity: 1 });
    }
    localStorage.setItem("cart", JSON.stringify(cart));
    alert("Produit ajouté au panier !");
    displayCart();
}

// Afficher le panier
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
            <p>Produit ${item.productId} - Quantité: ${item.quantity} </p>
            <button onclick="removeFromCart(${item.productId})">Supprimer</button>
        `;

        cartContainer.appendChild(itemElement);
    });
}

// Passer la commande
async function placeOrder() {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];

    if (cart.length === 0) {
        alert("Votre panier est vide !");
        return;
    }

    const productIds = cart.map(item => item.productId);

    let userId = localStorage.getItem("userId");
    if (!userId) {
        alert("Veuillez vous connecter avant de passer une commande.");
        return;
    }

    // Vérifier que les produits existent en base AVANT d'envoyer la commande
    try {
        const response = await fetch(`${API_BASE_URL}/products`);
        const products = await response.json();
        const validProductIds = products.map(product => product.id);

        // Filtrer les produits non valides
        const validCartItems = productIds.filter(id => validProductIds.includes(id));

        if (validCartItems.length !== productIds.length) {
            alert("Certains produits de votre panier ne sont plus disponibles !");
            checkCartValidity(); 
            return;
        }
    } catch (error) {
        console.error("Erreur lors de la vérification des produits avant commande :", error);
        return;
    }

    console.log("Envoi de la commande avec :", { userId, productIds });

    try {
        const response = await fetch(`${API_BASE_URL}/orders/place`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId: parseInt(userId), productIds })
        });

        if (!response.ok) {
            throw new Error(await response.text());
        }

        alert("Commande passée avec succès !");
        localStorage.removeItem("cart"); 
        displayCart();
    } catch (error) {
        console.error("Erreur lors de la commande :", error);
        alert("Erreur lors de la commande : " + error.message);
    }
}

async function checkCartValidity() {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    if (cart.length === 0) return;

    try {
        // Récupérer la liste des produits valides depuis l'API
        const response = await fetch(`${API_BASE_URL}/products`);
        const products = await response.json();
        const validProductIds = products.map(product => product.id);

        const updatedCart = cart.filter(item => validProductIds.includes(item.productId));

        if (updatedCart.length !== cart.length) {
            localStorage.setItem("cart", JSON.stringify(updatedCart)); 
            console.log("Panier mis à jour après le redémarrage de l'API");
        }
    } catch (error) {
        console.error("Erreur lors de la vérification du panier :", error);
    }
}

// Appeler cette fonction au chargement de la page
document.addEventListener("DOMContentLoaded", () => {
    checkCartValidity();
});
function removeFromCart(productId) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    cart = cart.filter(item => item.productId !== productId); // Enlever l'élément dont l'ID correspond au produit à supprimer
    localStorage.setItem("cart", JSON.stringify(cart));
    displayCart(); // Mettre à jour l'affichage du panier
    alert("Produit supprimé du panier.");
}
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
        console.log("📦 Réponse API - Commandes :", orders); // 🛠️ Ajout de logs

        displayUserOrders(orders);
    } catch (error) {
        console.error("Erreur lors du chargement des commandes :", error);
        document.getElementById("order-list").innerHTML = "<p>Impossible de charger les commandes.</p>";
    }
}

function displayUserOrders(orders) {
    const orderContainer = document.getElementById("order-list");
    orderContainer.innerHTML = "";

    if (orders.length === 0) {
        orderContainer.innerHTML = "<p>Aucune commande passée.</p>";
        return;
    }

    orders.forEach(order => {
        const orderElement = document.createElement("div");
        orderElement.classList.add("order");

        orderElement.innerHTML = `
            <h3>Commande ID: ${order.orderID}</h3>
            <p>Status: ${order.status}</p>
            <ul>
                ${order.items.map(item => {
                    const productName = item.productname || item.name || "Produit inconnu";
                    const productPrice = item.price || 0;
                    const quantity = item.quantity || 1;

                    return `<li>${productName} - ${productPrice}€ (x${quantity})</li>`;
                }).join("")}
            </ul>
            <hr>
        `;
        orderContainer.appendChild(orderElement);
    });
}
document.addEventListener("DOMContentLoaded", () => {
    loadUserOrders();
});