package edu.metrostate.cardealer;

import java.util.*;

public class Dealer {

    private List<Vehicle> inventory;
    private boolean acquisitionEnabled;
    private String dealerID;
    private String name;
    private List<Vehicle> loanedVehicles;

    // Two constructors for optional dealer name
    public Dealer(String dealerID) {
        this.inventory = new ArrayList<Vehicle>();
        this.acquisitionEnabled = true;
        this.dealerID = dealerID;
        this.name = "n/a";
        this.loanedVehicles = new ArrayList<Vehicle>();
    }

    public Dealer(String dealerID, String name) {
        this.inventory = new ArrayList<Vehicle>();
        this.acquisitionEnabled = true;
        this.dealerID = dealerID;
        this.name = name;
        this.loanedVehicles = new ArrayList<Vehicle>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicle.setDealerId(dealerID);
        inventory.add(vehicle);
    }

    public void removeVehicle(String vehicleID) {
        for (Vehicle vehicle : inventory) {
            if (vehicle.getId().equalsIgnoreCase(vehicleID)) {
                inventory.remove(vehicle);
                return;
            }
        }
    }

    public boolean vehicleExists(String vehicleID) {
        for (Vehicle vehicle : inventory) {
            if (vehicleID.equalsIgnoreCase(vehicle.getId())) {
                return true;
            }
        }
        return false;
    }

    // checks existence of vehicle in dealer
    public String vehicleCheckLoop(String ID) {
        String vehicleID = ID;
        if (!vehicleExists(vehicleID)) {
            System.out.println("\n~~~ Error: Vehicle does not exist.");
        }
        return vehicleID;
    }

    // looks for a vehicle in the dealer and returns it if found.
    public Vehicle getVehicle(String vehicleID) {
        Vehicle foundVehicle = null;
        if (!vehicleExists(vehicleID)) {
            System.out.println("~~~ Error: Vehicle does not exist.");
            vehicleID = vehicleCheckLoop(vehicleID);
            getVehicle(vehicleID);
        } else {
            for (Vehicle vehicle : getInventory()) {
                if (vehicle.getId().equalsIgnoreCase(vehicleID)) {
                    foundVehicle = vehicle;
                    break;
                }
            }
        }
        return foundVehicle;
    }

    // Looks for vehicle, sets it's "loaned" status to true
    public void loanVehicle(String vehicleID) {
        if (vehicleExists(vehicleID)) {
            for (Vehicle vehicle : getInventory()) {
                if (vehicle.getId().equalsIgnoreCase(vehicleID)) {
                    loanedVehicles.add(vehicle);
                    vehicle.loan();
                    return;
                }
            }
        }
    }

    // Looks for vehicle, sets it's "loaned" status to false
    public void returnVehicle(String vehicleID) {
        if (vehicleExists(vehicleID)) {
            for (Vehicle vehicle : getInventory()) {
                if (vehicle.getId().equalsIgnoreCase(vehicleID)) {
                    loanedVehicles.remove(vehicle);
                    vehicle.unloan();
                    return;
                }
            }
        }
    }

    public boolean getAcquisitionEnabled() {
        return this.acquisitionEnabled;
    }

    public void setAcquisitionEnabled(boolean status) {
        this.acquisitionEnabled = status;
        return;
    }

    public String getDealerId() {
        return this.dealerID;
    }

    public void setDealerId(String dealerID) {
        this.dealerID = dealerID;
        return;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Vehicle> getInventory() {
        return this.inventory;
    }

    public List<Vehicle> getLoanedVehicles() {
        return this.loanedVehicles;
    }

    public String printInventory() {
        String dealerInventory = "";
        dealerInventory += "-------------------------------------------\n";
        dealerInventory += "Dealer id: " + this.getDealerId() + "\n";
        dealerInventory += "Dealer name: " + this.getName() + "\n";
        dealerInventory += "Vehicle Acquisition Status: ";
        if (this.acquisitionEnabled) {
            dealerInventory += "enabled";
        } else {
            dealerInventory += "disabled";
        }
        for (Vehicle vehicle : inventory) {
            dealerInventory += vehicle.toString();
        }
        return dealerInventory;
    }
}
