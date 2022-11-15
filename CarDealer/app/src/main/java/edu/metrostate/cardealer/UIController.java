package edu.metrostate.cardealer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UIController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    String sendDealerID;
    String recipDealerID;
    String transferVehicleID;

    @FXML
    private TextField dealerIDbox, vehicleTypeBox, manufacturerBox, modelBox, vehicleIDbox, priceBox, dateBox,
            sendingDealerID, recipientDealerID, modDealerNameDealerID, modDealerNameDealerName, rentDealerID,
            rentVehicleID, newDealerIDText, newDealerNameText, removeDealerIDText;

    @FXML
    private TextArea textDisplay;

    @FXML
    private Button exitButton, transferVehicleSubmit;

    @FXML
    private Label transferVStatusLabel, acqResult, modResult, rentResult, newDealerResult, removeDealerResult,
            addVehicleStatusLabel;

    public static FileChooser fileChooser = new FileChooser();
    public static DealerList dealerList = new DealerList();
    public static Scanner scanner = new Scanner(System.in);

    public void importButton(ActionEvent e) {
        Importer.importFile();
    }

    // Switch to the Add Vehicle Scene
    public void addVehicleButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AddVehicleScene.fxml"));
        root = (Parent) loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void submitVehicleDetails(ActionEvent event) {

        List<String> types = Arrays.asList("suv", "sedan", "pickup", "sports car");

        String dealerID = null;
        String vehicleType = null;
        String manufacturer = null;
        String model = null;
        String vehicleID = null;
        int price = 0;
        Long date = null;

        try {
            dealerID = dealerIDbox.getText();
            vehicleType = vehicleTypeBox.getText();
            manufacturer = manufacturerBox.getText();
            model = modelBox.getText();
            vehicleID = vehicleIDbox.getText();
            price = Integer.parseInt(priceBox.getText());
            date = Long.parseLong(dateBox.getText());
        } catch (Exception e) {
            System.out.println(e);
            addVehicleStatusLabel.setText("Error: Please enter data in the correct format");
        }

        if (!dealerList.dealerExist(dealerID)) {
            addVehicleStatusLabel.setText("Error: Dealer does not exist. Please enter a valid dealer ID.");
        } else if (!dealerList.getDealer(dealerID).getAcquisitionEnabled()) {
            addVehicleStatusLabel.setText(
                    "Error: Dealer does not have vehicle acquisition enabled. Ensure dealer has aquisition enabled, or enter a different dealer ID.");
        } else if (!types.contains(vehicleType)) {
            addVehicleStatusLabel.setText("Error: Incorrect vehicle type. Please enter a valid vehicle type.");
        } else if (dealerList.getDealer(dealerID).vehicleExists(vehicleID)) {
            addVehicleStatusLabel
                    .setText("Error: This vehicle is already in the inventory. Enter a different Vehicle ID");
        } else {
            dealerList.addToDealer(dealerID,
                    new Vehicle(dealerID, vehicleType, manufacturer, model, vehicleID, price, date));
            addVehicleStatusLabel.setText("Your vehicle has been added to the inventory.");

            dealerIDbox.clear();
            vehicleTypeBox.clear();
            manufacturerBox.clear();
            modelBox.clear();
            vehicleIDbox.clear();
            priceBox.clear();
            dateBox.clear();
        }

    }

    // Switch to the Add Vehicle Scene
    public void dealerAcquisitionButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("VehicleAcquisitionScene.fxml"));
        root = (Parent) loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // Switch to the Export Inventory Scene
    public void exportInventoryButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ExportInventoryScene.fxml"));
        root = (Parent) loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // Export the dealer to file
    public void exportDealerToFile(ActionEvent event) throws FileNotFoundException {
        String dealerID = dealerIDbox.getText();

        Exporter.exportDealerJson(dealerID);
    }

    // Switch to the Display Inventory Scene
    public void displayInventoryButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("DisplayInventoryScene.fxml"));
        root = (Parent) loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // Prints full inventory to screen
    public void displayInventoryTextButton(ActionEvent event) throws IOException {
        String inventoryText;

        inventoryText = dealerList.printFullInventory();

        textDisplay.setText(inventoryText);

    }

    // Switch to the Modify Rental Status Scene
    public void modifyStatusButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ModifyRentalStatusScene.fxml"));
        root = (Parent) loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // sets given vehicle's rental status to "loaned" out
    public void loanedRentButton(ActionEvent event) {

        String dealerID = null;
        String vehicleID = null;

        try {
            dealerID = rentDealerID.getText();
            vehicleID = rentVehicleID.getText();
        } catch (Exception e) {
            System.out.println(e);
            rentResult.setText("Error: Please enter data in the correct format");
            rentDealerID.clear();
            rentVehicleID.clear();
        }

        if (!dealerList.dealerExist(dealerID)) {
            rentResult.setText("Error: Dealer does not exist. Please enter a valid Dealer ID.");
        } else {
            if (dealerList.modifyRentalStatus(dealerID, vehicleID, true)) {
                rentResult.setText("Vehicle rental status updated");
                rentDealerID.clear();
                rentVehicleID.clear();
            } else {
                rentResult.setText("Vehicle not found, please enter valid Dealer ID and Vehicle ID");
                rentDealerID.clear();
                rentVehicleID.clear();
            }
        }
    }

    // sets vehicles rental status to "available"
    public void availableRentButton(ActionEvent event) {

        String dealerID = null;
        String vehicleID = null;

        try {
            dealerID = rentDealerID.getText();
            vehicleID = rentVehicleID.getText();
        } catch (Exception e) {
            System.out.println(e);
            rentResult.setText("Error: Please enter data in the correct format");
            rentDealerID.clear();
            rentVehicleID.clear();
        }

        if (!dealerList.dealerExist(dealerID)) {
            rentResult.setText("Error: Dealer does not exist. Please enter a valid Dealer ID.");
        } else {
            if (dealerList.modifyRentalStatus(dealerID, vehicleID, false)) {
                rentResult.setText("Vehicle rental status updated");
                rentDealerID.clear();
                rentVehicleID.clear();
            } else {
                rentResult.setText("Vehicle not found, please enter valid Dealer ID and Vehicle ID");
                rentDealerID.clear();
                rentVehicleID.clear();
            }
        }
    }

    // sets dealer's vehicle acquisition to true (accepts vehicles)
    public void enableVehicleAcquisition(ActionEvent event) {

        String dealer = null;

        try {
            dealer = dealerIDbox.getText();
        } catch (Exception e) {
            System.out.println(e);
            acqResult.setText("Error: Please enter data in the correct format");
        }

        if (!dealerList.dealerExist(dealer)) {
            acqResult.setText("Error: Dealer does not exist. Please enter a valid Dealer ID.");
        } else {
            dealerList.dealerAcquisition(dealer, true);
            acqResult.setText("Dealer Acquisition Enabled");
            dealerIDbox.clear();
        }

    }

    // sets dealer's vehicle acquisition to false (does not accept vehicles)
    public void disableVehicleAcquisition(ActionEvent event) {

        String dealer = null;

        try {
            dealer = dealerIDbox.getText();
        } catch (Exception e) {
            System.out.println(e);
            acqResult.setText("Error: Please enter data in the correct format");
        }

        if (!dealerList.dealerExist(dealer)) {
            acqResult.setText("Error: Dealer does not exist. Please enter a valid Dealer ID.");
        } else {
            dealerList.dealerAcquisition(dealer, false);
            acqResult.setText("Dealer Acquisition Disabled");
            dealerIDbox.clear();
        }

    }

    // Switch to the Modify Dealer Name Scene
    public void modifyDealerNameButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ModifyDealerNameScene.fxml"));
        root = (Parent) loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // changes dealers name
    public void modDealerNameSubmitButton(ActionEvent event) throws IOException {
        String dealerId = null;
        String dealerName = null;

        try {
            dealerId = modDealerNameDealerID.getText();
            dealerName = modDealerNameDealerName.getText();
        } catch (Exception e) {
            System.out.println(e);
            modResult.setText("Error: Please enter data in the correct format");
        }

        if (!dealerList.dealerExist(dealerId)) {
            modResult.setText("Error: Dealer does not exist. Please enter a valid Dealer ID.");
            modDealerNameDealerID.clear();
            modDealerNameDealerName.clear();
        } else {
            dealerList.changeDealerName(dealerId, dealerName);
            modResult.setText("Dealer Name Updated");
            modDealerNameDealerID.clear();
            modDealerNameDealerName.clear();
        }
    }

    // Switch to the Transfer Vehicle Scene
    public void transferVehicleButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("TransferVehicleScene.fxml"));
        root = (Parent) loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // transfer vehicle between two dealers
    public void transferVehicleSubmit(ActionEvent event) {

        Dealer sendingDealer = null;
        Dealer recipientDealer = null;
        Vehicle vehicle;

        try {
            sendDealerID = sendingDealerID.getText();
            recipDealerID = recipientDealerID.getText();
            transferVehicleID = vehicleIDbox.getText();
        } catch (Exception e) {
            System.out.println(e);
            transferVStatusLabel.setText("Error: Please enter data in the correct format");
        }

        if (!dealerList.dealerExist(sendDealerID)) {
            transferVStatusLabel
                    .setText("Error: Sending Dealer ID does not exist. Please enter a valid Sending Dealer ID.");
        } else if (!dealerList.dealerExist(recipDealerID)) {
            transferVStatusLabel
                    .setText("Error: Recipient Dealer ID does not exist. Please enter a valid Recipient Dealer ID.");

        } else {

            for (Dealer dealer : dealerList.dealerList) {
                if (dealer.getDealerId().equalsIgnoreCase(sendDealerID)) {
                    sendingDealer = dealer;
                } else if (dealer.getDealerId().equalsIgnoreCase(recipDealerID)) {
                    recipientDealer = dealer;
                }
            }
            if (!sendingDealer.vehicleExists(transferVehicleID)) {
                transferVStatusLabel.setText("Error: Vehicle ID does not exist. Please enter a valid Vehicle ID.");
            }
            transferVehicleID = sendingDealer.vehicleCheckLoop(transferVehicleID);
            vehicle = sendingDealer.getVehicle(transferVehicleID);
            sendingDealer.removeVehicle(transferVehicleID);
            recipientDealer.addVehicle(vehicle);
            if (!sendingDealer.vehicleExists(transferVehicleID) && recipientDealer.vehicleExists(transferVehicleID)) {
                System.out.println("Transfer Successful.");
                transferVStatusLabel.setText("Transfer Successful.");

                sendingDealerID.clear();
                recipientDealerID.clear();
                vehicleIDbox.clear();
            }

        }

    }

    // clears text inputs
    public void addVehicleClearButton(ActionEvent event) {

        dealerIDbox.clear();
        vehicleTypeBox.clear();
        manufacturerBox.clear();
        modelBox.clear();
        vehicleIDbox.clear();
        priceBox.clear();
        dateBox.clear();

    }

    public void addDealerButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AddDealerScene.fxml"));
        root = (Parent) loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // adds a new dealer to the dealer list
    public void addDealerSubmitButton(ActionEvent event) throws IOException {

        String dealerID = null;
        String dealerName = null;

        try {
            dealerID = newDealerIDText.getText();
            dealerName = newDealerNameText.getText();
            if (dealerID.length() < 1) {
                newDealerResult.setText("Error: Dealer ID can not be empty");
                newDealerIDText.clear();
                newDealerNameText.clear();
            } else if (dealerList.dealerExist(dealerID)) {
                newDealerResult.setText("Error: Dealer already exists");
                newDealerIDText.clear();
                newDealerNameText.clear();
            } else {
                if (dealerName.length() < 1) {
                    Dealer dealer = new Dealer(dealerID);
                    dealerList.addDealer(dealer);
                    newDealerResult.setText("New Dealer Added");
                    newDealerIDText.clear();
                    newDealerNameText.clear();
                } else {
                    Dealer dealer = new Dealer(dealerID, dealerName);
                    dealerList.addDealer(dealer);
                    newDealerResult.setText("New Dealer Added");
                    newDealerIDText.clear();
                    newDealerNameText.clear();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            newDealerResult.setText("Error: Please enter data in the correct format");
            newDealerIDText.clear();
            newDealerNameText.clear();
        }

    }

    public void removeDealerButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("RemoveDealerScene.fxml"));
        root = (Parent) loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // removes dealer from the dealer list
    public void removeDealerSubmitButton(ActionEvent event) throws IOException {

        String dealerID = null;

        try {
            dealerID = removeDealerIDText.getText();
        } catch (Exception e) {
            System.out.println(e);
            removeDealerResult.setText("Error: Please enter data in the correct format");
            removeDealerIDText.clear();
        }

        if (dealerID.equals("")) {
            removeDealerResult.setText("Error: Dealer ID can not be empty");
            removeDealerIDText.clear();
        } else if (dealerList.dealerExist(dealerID)) {
            dealerList.removeDealer(dealerID);
            removeDealerResult.setText("Dealer Removed");
            removeDealerIDText.clear();
        } else {
            removeDealerResult.setText("Error: Dealer does not exist");
            removeDealerIDText.clear();
        }

    }

    // Switch back to UI Scene
    public void backButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/SceneBuilder.fxml"));
        Parent root = (Parent) loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // closes window and terminates program
    public void exitButton(ActionEvent event) throws IOException {

        stage = (Stage) exitButton.getScene().getWindow();
        Exporter.exportSaveFile();
        stage.close();
        System.exit(1);
    }

}
