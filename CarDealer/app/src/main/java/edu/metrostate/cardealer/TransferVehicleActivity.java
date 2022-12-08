package edu.metrostate.cardealer;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.metrostate.cardealer.databinding.ActivityTransferVehicleBinding;

public class TransferVehicleActivity extends AppCompatActivity {

    EditText sendingDealerId, receivingDealerId, vehicleId;
    Button back, transferVehicle;

    Dealer sendingDealer = null;
    Dealer recipientDealer = null;
    Vehicle vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_vehicle);

        sendingDealerId = findViewById(R.id.editSendingDealerId);
        receivingDealerId = findViewById(R.id.editReceivingDealerId);
        vehicleId = findViewById(R.id.editVehicleId);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the intent with the new activity
                Intent intent = new Intent(TransferVehicleActivity.this, MainActivity.class);

                // Launch the new Activity
                startActivity(intent);

            }
        });
    }

}