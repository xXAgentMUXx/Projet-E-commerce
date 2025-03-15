document.addEventListener("DOMContentLoaded", function() {
    const productList = document.getElementById("product-list");
    const cartItems = document.getElementById("cart-items");
    const checkoutButton = document.getElementById("checkout");
    
    let cart = [];
    
    const products = [
        { id: 1, name: "Ordinateur Portable", price: 999, image: "laptop.jpg" },
        { id: 2, name: "Smartphone", price: 699, image: "phone.jpg" },
        { id: 3, name: "Casque Audio", price: 199, image: "headphones.jpg" }
    ];
    function displayProducts() {
        productList.innerHTML = "";
        products.forEach(product => {
            const productDiv = document.createElement("div");
            productDiv.classList.add("product");
            productDiv.innerHTML = `
                <img src="${product.image}" alt="${product.name}">
                <h3>${product.name}</h3>
                <p>${product.price}€</p>
                <button onclick="addToCart(${product.id})">Ajouter au panier</button>
            `;
            productList.appendChild(productDiv);
        });
    }
    window.addToCart = function(productId) {
        const product = products.find(p => p.id === productId);
        if (product) {
            cart.push(product);
            updateCart();
        }
    }
    function updateCart() {
        cartItems.innerHTML = "";
        cart.forEach((item, index) => {
            const cartItem = document.createElement("div");
            cartItem.innerHTML = `${item.name} - ${item.price}€ <button onclick="removeFromCart(${index})">Retirer</button>`;
            cartItems.appendChild(cartItem);
        });
    }
    window.removeFromCart = function(index) {
        cart.splice(index, 1);
        updateCart();
    }
    checkoutButton.addEventListener("click", function() {
        alert("Commande passée avec succès !");
        cart = [];
        updateCart();
    });
    displayProducts();
});
