package edu.metrostate.cardealer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.google.gson.*;

public class Exporter {

    private static DealerList dealerList = CarDealerApplication.dealerList;
    private static Gson exportGson = new GsonBuilder().setPrettyPrinting().create();
    private static PrintWriter output;

    // For single dealer exporting to a file
    public static void exportDealerJson(String filepath, String dealerID) {
        try {
            for (Dealer dealer : dealerList.getDealerList()) {

                if (dealerID.equals(dealer.getDealerId())) {
                    String dealerName = dealer.getName();
                    File exportedFile = new File(filepath + dealerID + ".json");
                    output = new PrintWriter(exportedFile);
                    output.println("{\n\"dealer_inventory\":[");

                    for (Vehicle vehicle : dealer.getInventory()) {
                        String vString = exportGson.toJson(vehicle);
                        vString = vString.substring(1, vString.length() - 1).trim() + ",\n\"dealership_name\": \""
                                + dealerName + "\"";
                        output.print("{" + vString + "\n}");

                        if (!(dealer.getInventory().indexOf(vehicle) == dealer.getInventory().size() - 1)) {
                            output.println(",");
                        }
                    }

                    output.println("]\n}");
                    output.close();
                    System.out.println("... Exported dealer inventory as " + dealerID + ".json\n");
                    return;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Exporting master save file including all dealers
    public static void exportSaveFile(String filePath){
        try {
            File exportedFile = new File(filePath + "/" + CarDealerApplication.SAVE_FILE);
            output = new PrintWriter(exportedFile);
            output.println("{\n\"master_inventory\":[");

            for (Dealer dealer : dealerList.getDealerList()) {
                String dealerName = dealer.getName();

                if (dealer.getInventory().size() >= 1) {

                    for (Vehicle vehicle : dealer.getInventory()) {
                        String vString = exportGson.toJson(vehicle);
                        vString = vString.substring(1, vString.length() - 1).trim() + ",\n\"dealership_name\": \""
                                + dealerName
                                + "\"" + ",\n\"dealers_acquisition\": " + dealer.getAcquisitionEnabled();
                        output.print("{" + vString + "\n}");

                        if (!(dealer.getInventory().indexOf(vehicle) == dealer.getInventory().size() - 1)) {
                            output.println(",");
                        }
                    }
                } else {
                    String vString = "";
                    vString += "\n\"dealership_id\": \"" + dealer.getDealerId() + "\"";
                    vString += ",\n\"dealership_name\": \"" + dealer.getName() + "\"";
                    vString += ",\n\"dealership_name\": " + dealer.getAcquisitionEnabled();
                    output.print("{" + vString + "\n}");
                }

                if (!(dealerList.getDealerList().indexOf(dealer) == dealerList.getDealerList().size() - 1)) {
                    output.println(",");
                }

            }

            output.println("\n]\n}");
            output.close();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
