package model;

public class InHouse extends Part {

    private int machineId;

    public InHouse(int id, int stock, int min, int max, int machineId, String name, double price) {
        super(id, stock, min, max, name, price);
        this.machineId = machineId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
