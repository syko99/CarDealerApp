package edu.metrostate.cardealer;

import android.app.Application;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CarDealerApplication extends Application {
    private final List<Vehicle> vehicleList = new ArrayList<>();
    public static DealerList dealerList = new DealerList();
    public static final String SAVE_FILE = "MASTER_SAVE_FILE.json";

    @Override
    public void onCreate() {
        super.onCreate();
        this.loadSaveFile();
    }


    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public DealerList getDealerList() {
        return dealerList;
    }

    public void loadSaveFile() {
        File externalDir = getExternalFilesDir(null);
        File saveFile = new File(externalDir, SAVE_FILE);
        String filepath = saveFile.getAbsolutePath();
        Importer.importJSON(filepath);
    }

    public void writeFile() {

        //TODO: Remove this code
        // Gets the output path which is /sdcard/Android/data/edu.metrostate.cardealer/files directory
        File externalDir = getExternalFilesDir(null);
        // Establishes the output file as "myfile.txt"
        File outputFile = new File(externalDir, "myfile.txt");

        try {
            Files.createFile(outputFile.toPath());

            // Saves the string "My data" to the file
            Files.write(outputFile.toPath(), "My data".getBytes());

        } catch (IOException ex) {
            Log.e("FileCreation", "Error creating file", ex);
        }

    }



}
