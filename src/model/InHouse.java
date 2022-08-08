package model;

//extends allows for types, variables, and methods to be inherited by Part super class
public class InHouse extends Part {

    //PRIVATE VARIABLES
    private int machineId;

    //CONSTRUCTOR
    public InHouse(int id, int stock, int min, int max, int machineId, String name, double price) {
        super(id, stock, min, max, name, price);
        this.machineId = machineId;
    }

    //SETTERS - GETTERS
    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
