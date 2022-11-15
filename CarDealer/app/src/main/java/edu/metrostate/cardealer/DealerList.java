package edu.metrostate.cardealer;

import java.util.*;

public class DealerList {

    public List<Dealer> dealerList;

    public DealerList() {
        this.dealerList = new ArrayList<Dealer>();
    }

    public void addDealer(Dealer dealer) {
        dealerList.add(dealer);
        return;
    }

    // adding vehicle to a dealer if it exists
    public void addToDealer(String dealerID, Vehicle vehicle) {
        for (Dealer dealer : dealerList) {
            if (dealer.getDealerId().equalsIgnoreCase(dealerID) && !dealer.vehicleExists(vehicle.getId())) {
                dealer.addVehicle(vehicle);
            }
        }
    }

    // changes a given dealer's name
    public void changeDealerName(String dealerID, String newName) {
        for (Dealer dealer : dealerList) {
            if (dealer.getDealerId().equalsIgnoreCase(dealerID)) {
                dealer.setName(newName);
                return;
            }
        }
    }

    // checks for existence of dealer in the entire dealer list
    public boolean dealerExist(String dealerID) {
        for (Dealer dealer : dealerList) {
            if (dealerID.equalsIgnoreCase(dealer.getDealerId())) {
                return true;
            }
        }
        return false;
    }

    // removes dealer from the entire dealer list
    public void removeDealer(String dealerID) {
        for (int i = 0; i < dealerList.size(); i++) {
            if (dealerID.equalsIgnoreCase(dealerList.get(i).getDealerId())) {
                dealerList.remove(i);
            }
        }
        return;
    }

    // used to toggle a dealer's vehicle acquisition status (if they can or cannot accept vehicles)
    public void dealerAcquisition(String dealerID, boolean status) {
        DealerList dealerList = this;

        if (dealerList.dealerExist(dealerID)) {
            for (Dealer dealer : dealerList.getDealerList()) {
                if (dealer.getDealerId().equalsIgnoreCase(dealerID)) {
                    dealer.setAcquisitionEnabled(status);
                }
            }
        } else {
            System.out.println("~~~ Acquisition Error: dealer " + dealerID + " could not be found.");
        }
    }

    // returns a dealer object from the dealer list if found
    public Dealer getDealer(String dealerID) {
        Dealer extractedDealer = null;
        if (dealerExist(dealerID)) {
            for (Dealer dealer : dealerList) {
                if (dealer.getDealerId().equalsIgnoreCase(dealerID)) {
                    extractedDealer = dealer;
                    break;
                }
            }
        } else {
            System.out.println("~~~ Error: dealer " + dealerID + " could not be found.");
        }
        return extractedDealer;
    }

    public List<Dealer> getDealerList() {
        return this.dealerList;
    }

    public String printFullInventory() {
        String inventoryResults;
        if (dealerList.isEmpty()) {
            inventoryResults = "Dealer List is empty.\n";
            return inventoryResults;
        }
        inventoryResults = "Full Inventory\n";
        for (int i = 0; i < dealerList.size(); i++) {
            inventoryResults += dealerList.get(i).printInventory() + "\n";
        }
        return inventoryResults;
    }

    // if found in the dealer list, changes a vehicles rental status (is it loaned out or not)
    public boolean modifyRentalStatus(String dealerID, String vehicleID, boolean loanedStatus) {
        for (int i = 0; i < dealerList.size(); i++) {
            if (dealerID.equalsIgnoreCase(dealerList.get(i).getDealerId())) {
                for (int j = 0; j < dealerList.get(i).getInventory().size(); j++) {
                    if (vehicleID.equalsIgnoreCase(dealerList.get(i).getInventory().get(j).getId())) {
                        dealerList.get(i).getInventory().get(j).setLoaned(loanedStatus);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}