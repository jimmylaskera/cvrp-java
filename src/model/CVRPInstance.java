package model;

public class CVRPInstance {
    private final String name;
    private final int dimension;
    private int[][] distMatrix;
    private int[] demand;
    private int capacity;
    private int vehicles;

    public CVRPInstance(String n, int dim) {
        name = n;
        dimension = dim;
        distMatrix = new int[dimension][dimension];
        demand = new int[dimension];
    }

    public String getName() {
        return name;
    }

    public int getDimension() {
        return dimension;
    }

    public int[][] getDistMatrix() {
        return distMatrix;
    }

    public void setDistMatrix(int[][] dist) {
        this.distMatrix = dist;
    }

    public int[] getDemand() {
        return demand;
    }

    public void setDemand(int[] dmd) {
        this.demand = dmd;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getVehicles() {
        return vehicles;
    }

    public void setVehicles(int vehicles) {
        this.vehicles = vehicles;
    }

    
}
