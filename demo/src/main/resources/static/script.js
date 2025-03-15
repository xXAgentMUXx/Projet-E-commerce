document.addEventListener("DOMContentLoaded", function () {
    const productList = document.getElementById("product-list");
    const cartItems = document.getElementById("cart-items");
    const checkoutButton = document.getElementById("checkout");
    const orderList = document.getElementById("order-list");

    // Charger les produits
    async function loadProducts() {
        const response = await fetch("http://localhost:8080/products");
        const products = await response.json();

        productList.innerHTML = ""; 
        products.forEach(product => {
            const productDiv = document.createElement("div");
            productDiv.classList.add("product");
            productDiv.innerHTML = `
                <img src="${product.image || 'default.jpg'}" alt="${product.name}">
                <h3>${product.name}</h3>
                <p>${product.price}€</p>
                <button onclick="addToCart(${product.id})">Ajouter au panier</button>
            `;
            productList.appendChild(productDiv);
        });
    }

    // Ajouter au panier
    async function addToCart(productId) {
        const response = await fetch("http://localhost:8080/cart/add", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ productId })
        });

        if (response.ok) {
            alert("Produit ajouté au panier !");
            loadCart(); 
        } else {
            alert("Erreur lors de l'ajout au panier");
        }
    }

    // Charger le panier
    async function loadCart() {
        const response = await fetch("http://localhost:8080/cart");
        const cart = await response.json();

        cartItems.innerHTML = ""; 
        cart.forEach(item => {
            const cartItem = document.createElement("div");
            cartItem.innerHTML = `
                ${item.name} - ${item.price}€
                <button onclick="removeFromCart(${item.id})">Retirer</button>
            `;
            cartItems.appendChild(cartItem);
        });
    }

    // Retirer un article du panier
    async function removeFromCart(productId) {
        const response = await fetch("http://localhost:8080/cart/remove", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ productId })
        });

        if (response.ok) {
            alert("Produit retiré du panier !");
            loadCart(); 
        } else {
            alert("Erreur lors de la suppression");
        }
    }

    // Passer une commande
    checkoutButton.addEventListener("click", async function () {
        const response = await fetch("http://localhost:8080/orders/create", {
            method: "POST"
        });

        if (response.ok) {
            alert("Commande passée avec succès !");
            loadCart(); 
            loadOrders(); 
        } else {
            alert("Erreur lors de la commande");
        }
    });

    // Charger les commandes
    async function loadOrders() {
        const response = await fetch("http://localhost:8080/orders");
        const orders = await response.json();

        orderList.innerHTML = "";
        orders.forEach(order => {
            const orderDiv = document.createElement("div");
            orderDiv.innerHTML = `<p>Commande #${order.id} - Total: ${order.total}€</p>`;
            orderList.appendChild(orderDiv);
        });
    }

    // Ajouter un produit (formulaire)
    document.getElementById("product-form").addEventListener("submit", async function(event) {
        event.preventDefault();

        const name = document.getElementById("product-name").value;
        const price = document.getElementById("product-price").value;
        const image = document.getElementById("product-image").value;

        const response = await fetch("http://localhost:8080/products", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, price, image })
        });

        if (response.ok) {
            alert("Produit ajouté !");
            document.getElementById("product-form").reset();
            loadProducts(); // Recharge les produits
        } else {
            alert("Erreur lors de l'ajout du produit.");
        }
    });

    // Connexion utilisateur
    document.getElementById("login-form").addEventListener("submit", async function(event) {
        event.preventDefault();

        const email = event.target.querySelector("input[type='email']").value;
        const password = event.target.querySelector("input[type='password']").value;

        const response = await fetch("http://localhost:8080/users/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            alert("Connexion réussie !");
            window.location.reload();
        } else {
            alert("Échec de la connexion, vérifiez vos informations.");
        }
    });
    document.getElementById("register-form").addEventListener("submit", async function(event) {
        event.preventDefault();
    
        const username = document.getElementById("register-username").value;
        const email = document.getElementById("register-email").value;
        const password = document.getElementById("register-password").value;
    
        const response = await fetch("http://localhost:8080/users/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, email, password })
        });
    
        if (response.ok) {
            alert("Inscription réussie ! Vous pouvez maintenant vous connecter.");
            document.getElementById("register-form").reset();
        } else {
            alert("Erreur lors de l'inscription !");
        }
    });
    loadProducts();
    loadCart();
    loadOrders();
    window.addToCart = addToCart;
});
