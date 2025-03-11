public class Main {
    public static void main(String[] args) {

/* TEST POUR User
        // Création d'un utilisateur
        User user1 = new User("Alice", "alice@example.com", "password123");

        // Inscription de l'utilisateur
        user1.register();

        // Tentative de connexion avec un mauvais mot de passe
        user1.login("wrongpassword");

        // Connexion avec le bon mot de passe
        user1.login("password123");

        // Ajout de commandes à l'historique
        user1.addOrder("Laptop");
        user1.addOrder("Smartphone");

        // Affichage de l'historique des commandes
        user1.viewOrderHistory();
*/

// TEST POUR Product
        // Création d'un produit
        Product product1 = new Product("Ordinateur Portable", 101, 799.99, 10);

        // Affichage des détails du produit
        System.out.println(product1.getProductDetails());

        // Achat de 3 produits
        System.out.println("\nTentative d'achat de 3 unités...");
        product1.updateStock(3);
        System.out.println(product1.getProductDetails());

        // Achat de 8 produits (doit échouer)
        System.out.println("\nTentative d'achat de 8 unités...");
        product1.updateStock(8);
        System.out.println(product1.getProductDetails());

        // Mise à jour du prix
        System.out.println("\nMise à jour du prix...");
        product1.setPrice(749.99);
        System.out.println(product1.getProductDetails());
    }
}