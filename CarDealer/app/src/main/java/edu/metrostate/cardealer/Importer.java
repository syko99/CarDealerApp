package edu.metrostate.cardealer;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.commons.io.FilenameUtils;

import com.google.gson.*;

public class Importer {

    private static DealerList dealerList = CarDealerApplication.dealerList;
    private static Gson gson = new GsonBuilder().setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create();
    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private static Pattern pattern = Pattern.compile("E(\\d+)");

    // user chooses file type to import, calls respective import method for file
    // type
    public static void importFile() {

        String filePath = "CarDealer/app/src/main/java/edu/metrostate/cardealer/MASTER_SAVE_FILE.json";
        String fileType = FilenameUtils.getExtension(filePath);

        if (fileType.equalsIgnoreCase("json")) {
            importJSON(filePath);
        } else if (fileType.equalsIgnoreCase("xml")) {
            importXML(filePath);
        } else {
            System.out.println("~~~ Error: File type not supported, must be json or xml.");
        }
    }

    // Reads user selected json file and parses into json objects.
    public static void importJSON(String filePath) {

        // Takes vehicle array and parses to Json objects. Calls importVehicle method on
        // each object.
        try {
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            Map<?, ArrayList<?>> map = gson.fromJson(reader, Map.class);
            ArrayList<?> inventory = map.entrySet().iterator().next().getValue();
            int check = 0;

            // Imports json vehicles into Vehicle objects
            for (Object object : inventory) {
                String jsonObject = gson.toJson(inventory.get(inventory.indexOf(object)));
                jsonObject = jsonObject.replace(".", "");
                jsonObject = pattern.matcher(jsonObject).replaceAll("");
                boolean vloaned = false;
                int vprice = 0;
                String vDealerID = "n/a";
                String vType = "n/a";
                String vManu = "n/a";
                String vMod = "n/a";
                String vID = "n/a";
                String vDealerName = "n/a";
                Boolean vDealerAcq = true;
                long vAcqDate = 0;

                Map<String, String> singleVehicleMap = new HashMap<>();
                String vehicleString = jsonObject.substring(1, jsonObject.length() - 1);
                String[] vehicleToArray = vehicleString.split(",");

                // parses json object strings into a map for variable assignments
                for (String keyvalue : vehicleToArray) {
                    String[] keyandvalue = keyvalue.split(":");
                    String key = keyandvalue[0].trim();
                    String value = keyandvalue[1].trim();
                    key = key.substring(1, key.length() - 1);
                    if (value.charAt(0) == '"') {
                        value = value.substring(1, value.length() - 1).replace("\\u0027", "\'");
                    }
                    singleVehicleMap.put(key, value);
                }

                // variable assignments from map
                for (Map.Entry<String, String> pair : singleVehicleMap.entrySet()) {
                    String key = pair.getKey().toLowerCase();
                    switch (key) {
                        case "price":
                            vprice = Integer.parseInt(pair.getValue());
                            break;
                        case "dealership_id":
                            vDealerID = pair.getValue();
                            break;
                        case "vehicle_type":
                            vType = pair.getValue();
                            break;
                        case "vehicle_manufacturer":
                            vManu = pair.getValue();
                            break;
                        case "vehicle_model":
                            vMod = pair.getValue();
                            break;
                        case "vehicle_id":
                            vID = pair.getValue();
                            break;
                        case "dealership_name":
                            vDealerName = pair.getValue();
                            break;
                        case "acquisition_date":
                            vAcqDate = Long.parseLong(pair.getValue());
                            break;
                        case "loaned":
                            vloaned = Boolean.parseBoolean(pair.getValue());
                            break;
                        case "dealers_acquisition":
                            vDealerAcq = Boolean.parseBoolean(pair.getValue());
                            break;
                        default:
                            break;
                    }
                }

                // creating and importing vehicles
                Vehicle vehicle = new Vehicle(vDealerID, vType, vManu, vMod, vID, vprice, vAcqDate);
                vehicle.setPrice(vehicle.getPrice() / 10);
                if (vloaned) {
                    vehicle.loan();
                }
                if (singleVehicleMap.keySet().contains("vehicle_id")) {
                    importVehicle(vehicle);
                } else {
                    importEmptyDealer(vDealerID);
                }
                for (Dealer dealer : dealerList.getDealerList()) {
                    if (dealer.getDealerId().equalsIgnoreCase(vDealerID)) {
                        dealerList.changeDealerName(vDealerID, vDealerName);
                        dealer.setAcquisitionEnabled(vDealerAcq);
                    }
                }
                check++;
            }

            // User feedback on import
            if (check == inventory.size()) {
                System.out.println("\nImport successful.\n");
            } else {
                System.out.println("~~~ Error: Import may be missing information.");
            }

        } catch (NullPointerException e) {
            System.out.println("\nLoaded empty file.\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Reads user selected XML file and parses elements into lists for iterative
    // object creation.
    public static void importXML(String filePath) {

        try {
            // Creates and formats the document element for parsing.
            Date date = new Date();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));
            document.getDocumentElement().normalize();
            int check = 0;

            // Parse "Dealer" elements
            NodeList xmlDealerList = document.getElementsByTagName("Dealer");
            for (int i = 0; i < xmlDealerList.getLength(); i++) {
                Node dealerNode = xmlDealerList.item(i);
                String dealerID = dealerNode.getAttributes().getNamedItem("id").getNodeValue();
                if (!dealerList.dealerExist(dealerID)) {
                    dealerList.addDealer(new Dealer(dealerID));
                }
                NodeList vehicleList = dealerNode.getChildNodes();
                // Parse Vehicle elements in each dealer
                for (int j = 0; j < vehicleList.getLength(); j++) {
                    Node vehicleNode = vehicleList.item(j);
                    if (vehicleNode.getNodeName().equalsIgnoreCase("name")) {
                        dealerList.changeDealerName(dealerID, vehicleNode.getTextContent().replace("’", "'"));
                    } else if (vehicleNode.getNodeName().equalsIgnoreCase("vehicle")) {
                        String vType = vehicleNode.getAttributes().getNamedItem("type").getNodeValue();
                        String vID = vehicleNode.getAttributes().getNamedItem("id").getNodeValue();
                        int vPrice = 0;
                        String vManu = "";
                        String vModel = "";

                        // Parse variable elements in vehicle node
                        NodeList vehicleProperyList = vehicleNode.getChildNodes();
                        for (int k = 0; k < vehicleProperyList.getLength(); k++) {
                            Node propertyNode = vehicleProperyList.item(k);
                            if (propertyNode.getNodeName().equalsIgnoreCase("price")) {
                                vPrice = Integer.parseInt(propertyNode.getTextContent());
                            } else if (propertyNode.getNodeName().equalsIgnoreCase("make")) {
                                vManu = propertyNode.getTextContent();
                            } else if (propertyNode.getNodeName().equalsIgnoreCase("model")) {
                                vModel = propertyNode.getTextContent();
                            }
                        }

                        // create vehicle and add to dealer
                        Vehicle vehicle = new Vehicle(dealerID, vType, vManu, vModel, vID, vPrice, date.getTime());
                        importVehicle(vehicle);
                    }
                }
                check++;
            }

            // User feedback on import
            if (check == xmlDealerList.getLength()) {
                System.out.println("\nImport successful.\n");
            } else {
                System.out.println("~~~ Error: Import may be missing information.");
            }

        } catch (NullPointerException e) {
            System.out.println("\nLoaded empty file.\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Imports vehicle into dealers, creates dealers as necessary.
    private static void importVehicle(Vehicle vehicle) {
        if (!dealerList.dealerExist(vehicle.getDealerId())) {
            dealerList.addDealer(new Dealer(vehicle.getDealerId()));
        }
        dealerList.addToDealer(vehicle.getDealerId(), vehicle);
    }

    private static void importEmptyDealer(String dealerID) {
        if (!dealerList.dealerExist(dealerID)) {
            dealerList.addDealer(new Dealer(dealerID));
        }
    }
}
