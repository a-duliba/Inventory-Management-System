package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    //PRIVATE VARIABLES
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int tempPartId = 0;

    private static int tempProductId = 0;

    //PUBLIC METHODS

    //adds part to allParts ObservableList.
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    //adds product to allProducts ObservableList.
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    //searches for part based on Id, initializes isPartFound with value encase not found, and goes through allParts observable list and returns all id's found.
    public static Part lookupPart(int partId) {

        Part isPartFound = null;

        for(Part part : allParts) {
            if (part.getId() == partId) {
                isPartFound = part;
            }
        }
        return isPartFound;
    }

    //searches for product based on id, goes through allProducts Observable list and returns all id's found. returns null if none found.
    public static Product lookupProduct(int productId) {

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    //searches part observable list and returns parts based on their name(if there is a string value).
    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> searchedPart = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                searchedPart.add(part);
            }
        }
        if (searchedPart.isEmpty()) {
            return allParts;
        }
        return searchedPart;
    }

    //searches product observable list and returns products based on their name(if there is a string value).
    public static ObservableList<Product> lookupProduct(String productName) {

        ObservableList<Product> searchProducts = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                searchProducts.add(product);
            }
        }
        if (searchProducts.isEmpty()) {
            return allProducts;
        }
        return searchProducts;
    }

    //sets a selected parts index from all allParts observable list.
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    //sets a selected products index from all allProducts observable list.
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    //deletes a selected part from all allParts observable list.
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    //deletes a selected product from all allProducts observable list.
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    //gets all parts from allParts observable list.
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    //gets all products from all allProducts observable list.
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    //gets temporary part id and increments it.
    public static int getTempPartId() {
        return ++tempPartId;
    }

    //gets temporary product id and increments it.
    public static int getTempProductId() {
        return ++tempProductId;
    }

}
