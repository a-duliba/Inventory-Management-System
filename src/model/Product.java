package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    //PRIVATE VARIABLES
    private ObservableList <Part> associatedParts = FXCollections.observableArrayList();
    private int id, stock, min, max;
    private String name;
    private double price;

    //CONSTRUCTOR
    public Product(int id, int stock, int min, int max, String name, double price) {
        this.id = id;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.name = name;
        this.price = price;
    }

    //SETTERS - GETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    //Adds all associated parts to an observable list
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    //METHODS

    //Deletes the associated part the user selects from the associated parts observable list.
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        return false;
    }

}
