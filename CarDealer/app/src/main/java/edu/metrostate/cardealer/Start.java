/* ICS 372 group 3 Project
 * Full description in README
 */

package edu.metrostate.cardealer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Start extends Application {

    public static final String SAVE_FILE = "MASTER_SAVE_FILE.json";

    public static void main(String[] args) throws FileNotFoundException {

        // Load save file and Call the Launch method for JavaFX
        Importer.importJSON(SAVE_FILE);
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/SceneBuilder.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, Color.DARKGRAY);

            stage.setTitle("Dealership Tracking System");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}