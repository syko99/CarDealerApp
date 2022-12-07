package edu.metrostate.cardealer;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.view.View;

import org.w3c.dom.Document;

public class MainActivity extends AppCompatActivity {

    CarDealerApplication app = (CarDealerApplication) getApplication();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.add_dealer_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddDealerActivity.class));
            }
        });

        findViewById(R.id.show_vehicle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the intent with the new activity
                Intent intent = new Intent(MainActivity.this, VehicleListActivity.class);

                // Launch the new Activity
                startActivity(intent);
            }
        });

        findViewById(R.id.import_file_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the intent with the new activity
                Intent intent = new Intent(MainActivity.this, FileChooserActivity.class);

                // Launch the new Activity
                startActivity(intent);
            }

        });

        findViewById(R.id.export_dealer_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the intent with the new activity
                Intent intent = new Intent(MainActivity.this, ExportDealerActivity.class);

                // Launch the new Activity
                startActivity(intent);
            }

        });

        findViewById(R.id.edit_dealer_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the intent with the new activity
                Intent intent = new Intent(MainActivity.this, editDealerActivity.class);

                // Launch the new Activity
                startActivity(intent);
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Exporter.exportSaveFile(getExternalFilesDir(null).getAbsolutePath());
    }
}