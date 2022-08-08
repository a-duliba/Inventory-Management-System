package model;

//extends allows for types, variables, and methods to be inherited by Part super class
public class Outsourced extends Part {

    //PRIVATE VARIABLES
    private String companyName;

    //CONSTRUCTOR
    public Outsourced(int id, int stock, int min, int max, String name, String companyName, double price) {
        super(id, stock, min, max, name, price);
        this.companyName = companyName;
    }
    //SETTERS - GETTERS
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
