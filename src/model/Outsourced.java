package model;

/** @author Andrew Duliba : 08/09/2022 C482 SOFTWARE-1 Inventory Management System. **/

//extends allows for types, variables, and methods to be inherited by Part super class
public class Outsourced extends Part {

    //PRIVATE VARIABLES

    /** Outsourced Company Name. **/
    private String companyName;

    //CONSTRUCTOR

    /** Outsourced Constructor with company name variable added.
     *
     * @param id Part ID
     * @param stock Part Stock.
     * @param min Part Min
     * @param max Part Max
     * @param name Part Name
     * @param companyName Part Company Name
     * @param price Part Price. **/
    public Outsourced(int id, int stock, int min, int max, String name, String companyName, double price) {
        super(id, stock, min, max, name, price);
        this.companyName = companyName;
    }
    //SETTERS - GETTERS

    /** Gets Company Name.
     *
     * @return Part Company Name. **/
    public String getCompanyName() {
        return companyName;
    }

    /** Sets Company Name
     *
     * @param companyName Part Company Name. **/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
