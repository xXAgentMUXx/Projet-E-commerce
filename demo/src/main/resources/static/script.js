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
        const userId = 1; // À récupérer dynamiquement si possible
    
        const response = await fetch("http://localhost:8080/cart/add", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId: userId, productId: productId, quantity: 1 })
        });
    
        const responseText = await response.text();
        console.log("Réponse du serveur:", responseText);
    
        if (response.ok) {
            alert("Produit ajouté au panier !");
            loadCart(); 
        } else {
            alert("Erreur lors de l'ajout au panier : " + responseText);
        }
    }

    // Charger le panier
    async function loadCart() {
        const userId = 1; // À remplacer par l'ID de l'utilisateur connecté
        const response = await fetch(`http://localhost:8080/users/${userId}/orders`);
        
        if (!response.ok) {
            alert("Erreur lors du chargement du panier");
            return;
        }
        
        const orders = await response.json();
        cartItems.innerHTML = "";
    
        if (orders.length === 0) {
            cartItems.innerHTML = "<p>Votre panier est vide.</p>";
            return;
        }
    
        // Prend la dernière commande (non confirmée si l'API le permet)
        const lastOrder = orders[orders.length - 1];
    
        lastOrder.items.forEach(item => {
            const cartItem = document.createElement("div");
            cartItem.innerHTML = `
                ${item.name} - ${item.price}€ - Quantité: ${item.quantity}
                <button onclick="removeFromCart(${item.productID})">Retirer</button>
            `;
            cartItems.appendChild(cartItem);
        });
    }

    // Retirer un article du panier
    async function removeFromCart(productId) {
        const response = await fetch("http://localhost:8080/cart/remove", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId: userId, productId: productId })
        });
    
        if (response.ok) {
            alert("Produit retiré du panier !");
            loadCart(); 
        } else {
            const error = await response.text();
            alert("Erreur lors de la suppression: " + error);
        }
    }
    

    // Passer une commande
    checkoutButton.addEventListener("click", async function () {
        const userId = 1; // ID utilisateur réel à récupérer dynamiquement
    
        // Récupérer les IDs des produits dans le panier
        const cartResponse = await fetch(`http://localhost:8080/cart/${userId}`);
        if (!cartResponse.ok) {
            alert("Erreur lors de la récupération du panier.");
            return;
        }
    
        const cart = await cartResponse.json();
        const productIds = Object.keys(cart.items).map(id => parseInt(id)); // Convertit en tableau d'entiers
    
        if (productIds.length === 0) {
            alert("Votre panier est vide !");
            return;
        }
    
        // Envoi de la commande
        const response = await fetch("http://localhost:8080/orders/place", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId: userId, productIds: productIds })
        });
    
        if (response.ok) {
            alert("Commande passée avec succès !");
            loadCart(); // Recharger le panier
            loadOrders(); // Recharger la liste des commandes
        } else {
            const error = await response.text();
            alert("Erreur lors de la commande: " + error);
        }
    });

    // Charger les commandes
    async function loadOrders() {
        const userId = 1; // Récupérer l'ID réel de l'utilisateur connecté
        const response = await fetch(`http://localhost:8080/users/${userId}/orders`);
        
        if (!response.ok) {
            alert("Erreur lors du chargement des commandes");
            return;
        }
    
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
        const Stockquantity = document.getElementById("product-quantity").value;

        const response = await fetch("http://localhost:8080/products", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, price, image, Stockquantity})
        });

        if (response.ok) {
            alert("Produit ajouté !");
            document.getElementById("product-form").reset();
            loadProducts(); 
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
