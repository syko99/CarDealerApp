package edu.metrostate.cardealer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Vehicle {

    // instance variables in same order of JSON file
    private String dealership_id;
    private String vehicle_type;
    private String vehicle_manufacturer;
    private String vehicle_model;
    private String vehicle_id;
    private int price;
    private Long acquisition_date;
    private boolean loaned;

    // namespaces
    static List<String> types = Arrays.asList("suv", "sedan", "pickup", "sports car");

    public Vehicle(String dealership_id, String vehicle_type, String vehicle_manufacturer, String vehicle_model,
                   String vehicle_id, int price, long acquisitionDate) {
        this.dealership_id = dealership_id;
        this.vehicle_type = vehicle_type;
        this.vehicle_manufacturer = vehicle_manufacturer;
        this.vehicle_model = vehicle_model;
        this.vehicle_id = vehicle_id;
        this.price = price;
        this.acquisition_date = acquisitionDate;
        this.loaned = false;
    }

    public String getType() {
        return vehicle_type;
    }

    public void setType(String type) {
        if (types.contains(type)) {
            this.vehicle_type = type;
        } else {
            System.out.println("Error: the type does not exist. Options are: suv, sedan, pickup, sports car.");
        }
    }

    public String getId() {
        return vehicle_id;
    }

    public void setId(String id) {
        this.vehicle_id = id;
    }

    public String getManufacturer() {
        return vehicle_manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.vehicle_manufacturer = manufacturer;
    }

    public String getModel() {
        return vehicle_model;
    }

    public void setModel(String model) {
        this.vehicle_model = model;
    }

    public Date getAcquisitionDate() {
        return new Date(acquisition_date);
    }

    public void setAcquisitionDate(String acquisitionDate) {
        this.acquisition_date = Long.valueOf(acquisitionDate);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDealerId() {
        return dealership_id;
    }

    public void setDealerId(String dealerID) {
        this.dealership_id = dealerID;
    }

    public void setLoaned(boolean status) {
        this.loaned = status;
    }

    public void loan() {
        this.loaned = true;
    }

    public void unloan() {
        this.loaned = false;
    }

    public boolean getLoanStatus() {
        return loaned;
    }

    public String toString() {
        String vehicle = "\nVehicle Id: " + this.getId() + " || Manufacturer: " + this.getManufacturer() + " || Model: "
                + this.getModel() + " || Type: " + this.getType() + " || Price: $" + this.getPrice();
        if (this.loaned) {
            vehicle += " || Rental Status: loaned out";
        } else {
            vehicle += " || Rental Status: available";
        }
        vehicle += " || Acquisition Date: " + this.getAcquisitionDate();
        return vehicle;
    }
}