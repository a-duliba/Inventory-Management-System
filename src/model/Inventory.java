package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {

        Part isPartFound = null;

        for(Part part : allParts) {
            if (part.getId() == partId) {
                isPartFound = part;
            }
        }
        return isPartFound;
    }

    public static Product lookupProduct(int productId) {

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

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

    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {

        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {

        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {

        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

    private static int tempPartId = 0;

    private static int tempProductId = 0;

    public static int getTempPartId() {
        return ++tempPartId;
    }

    public static int getTempProductId() {
        return ++tempProductId;
    }

}
