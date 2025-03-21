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
        console.log("No user logged in. Resetting the cart.");
        localStorage.removeItem("cart");
    } else {
        console.log("User logged in, ID:", userId);
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
            throw new Error("Incorrect email or password");
        }
        const user = await response.json(); // Retrieve user data (ID)
        localStorage.setItem("userId", user.id); // Store user ID in localStorage
        alert("Login successful!");
        location.reload(); // Reload the page to refresh the session
    } catch (error) {
        alert("Login error: " + error.message);
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
        alert("Registration successful!");
    } else {
        alert("Registration error");
    }
}

// Load products from the API
async function loadProducts() {
    try {
        const response = await fetch(`${API_BASE_URL}/products`);
        const products = await response.json();
        displayProducts(products); // Display products after loading
    } catch (error) {
        console.error("Error loading products:", error);
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
            <button onclick="addToCart(${product.id})">Add to Cart</button>
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
        alert("You are not an admin. Please log in.");
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
            throw new Error("You are not an admin, please log in.");
        }
        alert("Product added successfully!");
        loadProducts(); // Reload the products list after adding a product
    } catch (error) {
        alert(error.message);
    }
}

// Delete a product (Admin only)
async function deleteProduct(productId) {
    const userId = localStorage.getItem("userId"); 

    if (!userId) {
        alert("You must be logged in as an administrator to delete a product!");
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
        alert("Product deleted successfully!");
        loadProducts(); // Reload the products list after deletion
    } else {
        const errorText = await response.text();
        alert("❌ " + errorText);
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
    alert("Product added to cart!");
    displayCart(); // Update the cart display
}

// Display the cart
function displayCart() {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    const cartContainer = document.getElementById("cart-items");
    cartContainer.innerHTML = "";

    if (cart.length === 0) {
        cartContainer.innerHTML = "<p>Your cart is empty.</p>";
        return;
    }
    cart.forEach(item => {
        const itemElement = document.createElement("div");
        itemElement.classList.add("cart-item");
        
        itemElement.innerHTML = `
            <p>Product ${item.productId} - Quantity: ${item.quantity} </p>
            <button onclick="removeFromCart(${item.productId})">Remove</button>
        `;
        cartContainer.appendChild(itemElement);
    });
}

// Place an order
async function placeOrder() {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];

    if (cart.length === 0) {
        alert("Your cart is empty!");
        return;
    }
    const productIds = cart.map(item => item.productId); // Get Product IDs
    const quantities = cart.map(item => item.quantity); // Get quantities

    let userId = localStorage.getItem("userId");
    if (!userId) {
        alert("Please log in before placing an order.");
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
            alert("Some products in your cart are no longer available!");
            checkCartValidity(); // Check for cart validity
            return;
        }
    } catch (error) {
        console.error("Error checking products before order:", error);
        return;
    }
    console.log("Sending order with:", { userId, productIds, quantities }); // Log order details

    try {
        const response = await fetch(`${API_BASE_URL}/orders/place`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId: parseInt(userId), productIds, quantities }) // Send quantities as well
        });
        if (!response.ok) {
            throw new Error(await response.text());
        }
        alert("Order placed successfully!");
        localStorage.removeItem("cart"); // Clear the cart after successful order
        displayCart();
    } catch (error) {
        console.error("Error placing order:", error);
        alert("Error placing order: " + error.message);
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
    alert("Product removed from cart.");
}

// Load and display user orders
async function loadUserOrders() {
    let userId = localStorage.getItem("userId");

    if (!userId) {
        document.getElementById("order-list").innerHTML = "<p>Please log in to view your orders.</p>";
        return;
    }
    try {
        const response = await fetch(`${API_BASE_URL}/users/${userId}/orders`);
        if (!response.ok) {
            throw new Error("Error loading orders");
        }
        const orders = await response.json();
        displayUserOrders(orders);
    } catch (error) {
        console.error("Error loading orders:", error);
        document.getElementById("order-list").innerHTML = "<p>Unable to load orders.</p>";
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
                    const productName = item.productname || item.name || "Unknown product";
                    const productPrice = item.price || 0;
                    const quantity = order.quantities[index] || 1; 
                    const totalPrice = (productPrice * quantity).toFixed(2); 

                    return `<li>${productName} - Unit price: ${productPrice}€ (x${quantity}) - Total: ${totalPrice}€</li>`;
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
