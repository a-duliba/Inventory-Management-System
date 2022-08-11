package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** @author Andrew Duliba : 08/09/2022 C482 SOFTWARE-1 Inventory Management System. **/

public class Inventory {

    //PRIVATE VARIABLES

    /** Observable list, type Part, called allParts. **/
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** Observable list, type Product, called allProducts. **/
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Temporary Part ID. **/
    private static int tempPartId = 3;

    /** Temporary Product ID. **/
    private static int tempProductId = 3;

    //PUBLIC METHODS

    //adds part to allParts ObservableList.
    /** Adds part to allParts observable list.
     *
     * @param newPart New part. **/
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    //adds product to allProducts ObservableList.
    /** Add product to allProducts observable list.
     *
     * @param newProduct New Product. **/
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    //searches for part based on Id, initializes isPartFound with value encase not found, and goes through allParts observable list and returns all id's found.
    /** Searches parts based on their Part ID.
     *
     * @param partId Part ID.
     * @return Part. **/
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
    /** Searches products based on their Product ID.
     *
     * @param productId Product ID.
     * @return Product. **/
    public static Product lookupProduct(int productId) {

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    //searches part observable list and returns parts based on their name(if there is a string value).
    /** Searches parts observable list, type Part, based on their Part Name.
     *
     * @param partName Part Name.
     * @return Search Part. **/
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
    /** Searches products observable list, type Product, based on their Product Name.
     *
     * @param productName Product Name.
     * @return Search Product. **/
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
    /** Updates selected part based on its index.
     *
     * @param index Index of allParts observable list.
     * @param selectedPart Selected Part. **/
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    //sets a selected products index from all allProducts observable list.
    /** Updates selected product based on its index.
     *
     * @param index Index of allParts observable list.
     * @param newProduct Selected Part. **/
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    //deletes a selected part from all allParts observable list.
    /** Deletes parts that are selected.
     *
     * @param selectedPart Selected Part.
     * @return true. **/
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    //deletes a selected product from all allProducts observable list.
    /** Deletes products that are selected.
     *
     * @param selectedProduct Selected Part.
     * @return true. **/
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    //gets all parts from allParts observable list.
    /** Gets all parts in allParts observable list, type Part.
     *
     * @return Observable List called allParts. **/
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    //gets all products from all allProducts observable list.
    /** Gets all products in allProducts observable list, type Product.
     *
     * @return Observable List called allProducts. **/
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    //gets temporary part id and increments it.
    /** Gets Temporary Part ID.
     *
     * Increments the ID.
     *
     * @return Temporary Part ID. **/
    public static int getTempPartId() {
        return ++tempPartId;
    }

    //gets temporary product id and increments it.
    /** Gets Temporary Product ID.
     *
     * Increments the ID.
     *
     * @return Temporary Product ID. **/
    public static int getTempProductId() {
        return ++tempProductId;
    }

}
