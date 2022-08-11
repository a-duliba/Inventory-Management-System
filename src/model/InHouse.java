package model;

/** @author Andrew Duliba : 08/09/2022 C482 SOFTWARE-1 Inventory Management System. **/

//extends allows for types, variables, and methods to be inherited by Part super class
public class InHouse extends Part {


    //PRIVATE VARIABLES

    /** In-House Machine ID. **/
    private int machineId;

    //CONSTRUCTOR

    /** In-House Constructor with machine id variable added.
     *
     * @param id Part ID
     * @param stock Part Stock.
     * @param min Part Min
     * @param max Part Max
     * @param machineId Part Machine ID
     * @param name Part Name
     * @param price Part Price. **/
    public InHouse(int id, int stock, int min, int max, int machineId, String name, double price) {
        super(id, stock, min, max, name, price);
        this.machineId = machineId;
    }

    //SETTERS - GETTERS

    /** Gets Machine ID.
     *
     * @return Part Machine ID. **/
    public int getMachineId() {
        return machineId;
    }

    /** Sets Machine ID
     *
     * @param machineId Part Machine ID. **/
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
