package model;

public class Outsourced extends Part {

    private String companyName;


    public Outsourced(int id, int stock, int min, int max, String name, String companyName, double price) {
        super(id, stock, min, max, name, price);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
