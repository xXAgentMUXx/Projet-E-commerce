PREREQUIT :

Extension VSC à avoir installer :
- Debugger for Java
- Extension Pack for Java
- Maven for Java
- Project Manager for Java
- Test Runner for Java

- Gradle for Java

- Spring Boot Dashboard
- Spring Boot Tools
- Spring Initializr Java Support

Version de Java à avoir : Java 23
    Guide installation :
    - Regarde la version deja installer (si installer) : javac -version

    (Si pas ajouter)
    - Ajouter le repo de openjdk : sudo add-apt-repository ppa:openjdk-r/ppa
    - Mettre a jour le système : sudo apt update
    - Installer java 23 : sudo apt install openjdk-23-jdk
    - Regarder si java 23 est bien selectionner avec : "sudo update-alternatives --config java" et "sudo update-alternatives --config javac"

Lorsque tout est bien installer et fonctionnelle :

- Lancement avec : ./gradlew bootRun

Une fois gradle lancer :
- Créé un nouveau terminal (bash)

Dans ce terminal mettre ces commande :

SOUS LINUX !!!! :
# Enregistrement d'un utilisateur
curl -X POST "http://localhost:8080/users/register" \
     -H "Content-Type: application/json" \
     -d '{"username": "john_doe", "email": "john.doe@example.com", "password": "secure123", "role": "REGULAR"}'

# Connexion d'un utilisateur
curl -X POST "http://localhost:8080/users/login" \
     -H "Content-Type: application/json" \
     -d '{"email": "john.doe@example.com", "password": "secure123"}'

# Ajout d'un produit
curl -X POST "http://localhost:8080/products" \
     -H "Content-Type: application/json" \
     -d '{"productID": 101, "name": "Laptop", "price": 999.99, "stock": 10}'

# Récupérer un produit
curl -X GET "http://localhost:8080/products"

# Récupérer un produit spécifique
curl -X GET "http://localhost:8080/products/1"

# Ajout d'un produit au panier
curl -X POST "http://localhost:8080/cart/add" \
     -H "Content-Type: application/json" \
     -d '{"userId": 1, "productId": 1, "quantity": 2}'

# Suppression d'un produit du panier
curl -X POST "http://localhost:8080/cart/remove" \
     -H "Content-Type: application/json" \
     -d '{"userId": 1, "productId": 1}'

# Passer une commande
curl -X POST "http://localhost:8080/orders/place" \
     -H "Content-Type: application/json" \
     -d '{"userId": 1, "productIds": [1], "quantities": [2]}'

# Récupérer les commandes d'un utilisateur
curl -X GET "http://localhost:8080/users/1/orders"

# Récupérer une commande spécifique
curl -X GET "http://localhost:8080/orders/1"


SOUS WINDOWS !!!! :
# Enregistrement d'un utilisateur
 Invoke-WebRequest -Uri "http://localhost:8080/users/register" -Method Post -Headers @{"Content-Type"="application/json"} -Body '{"username": "john_doe", "email": "john.doe@example.com", "password": "secure123", "role": "REGULAR"}' -UseBasicParsing

# Connexion d'un utilisateur
 Invoke-WebRequest -Uri "http://localhost:8080/users/login" -Method Post `
-Headers @{"Content-Type"="application/json"} `
-Body '{"email": "john.doe@example.com", "password": "secure123"}' -UseBasicParsing

# Ajout d'un produit
Invoke-WebRequest -Uri "http://localhost:8080/products" -Method Post -Headers @{"Content-Type"="application/json"} -Body '{"productID": 101, "name": "Laptop", "price": 999.99, "stock": 10}'

# Récupérer un produit
Invoke-WebRequest -Uri "http://localhost:8080/products"

# Récupérer un produit spécifique
Invoke-WebRequest -Uri "http://localhost:8080/products/1"

# Ajout d'un produit au panier
Invoke-WebRequest -Uri "http://localhost:8080/cart/add" -Method Post -Body (@{userId=1;
 productId=1; quantity=2} | ConvertTo-Json -Depth 1) -ContentType "application/json"

# Suppression d'un produit du panier
Invoke-WebRequest -Uri "http://localhost:8080/cart/remove" -Method Post -Body (@{userId=1; productId=1} | ConvertTo-Json -Depth 1) -ContentType "application/json"

# Passer une commande
Invoke-WebRequest -Uri "http://localhost:8080/orders/place" -Method Post `
    -Headers @{"Content-Type"="application/json"} `
    -Body '{"userId":1, "productIds":[1], "quantities":[2]}' `
    -UseBasicParsing

# Récupérer les commandes d'un utilisateur
Invoke-WebRequest -Uri "http://localhost:8080/users/1/orders" -Method Get

# Récupérer une commande spécifique
Invoke-WebRequest -Uri "http://localhost:8080/orders/1" -Method Get
