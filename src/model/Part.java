package model;

/** @author Andrew Duliba : 08/09/2022 C482 SOFTWARE-1 Inventory Management System. **/

public abstract class Part {

    //PRIVATE VARIABLES

    /** Part id, stock, min, max. */
    private int id, stock, min, max;

    /** Part name. */
    private String name;

    /** Part price. */
    private double price;

    //CONSTRUCTOR
    /** Part Constructor.
     *
     * @param id Part ID
     * @param stock Part Stock.
     * @param min Part Min
     * @param max Part Max
     * @param name Part Name
     * @param price Part Price. **/
    public Part(int id, int stock, int min, int max, String name, double price) {
        this.id = id;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.name = name;
        this.price = price;
    }

    //SETTERS - GETTERS

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id sets the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock sets the stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min sets the min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max sets the max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name sets the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price sets the price
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
