package edu.metrostate.cardealer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.content.Intent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddVehicleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static DealerList dealerList = new DealerList();

    EditText dealerIdText, manufacturerText, modelText, vehicleIdText, priceText, dateText;
    Spinner vehicleTypeSpinner;
    TextView datePicker;
    DatePickerDialog.OnDateSetListener dateSetListener;
    RadioGroup rentalStatus;
    RadioButton rentalStatusButton;
    Button addVehicle, back;

    String dealerID, vehicleType, manufacturer, model, vehicleID;
    int price;
    long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vehicle_layout);

        dealerIdText = findViewById(R.id.editDealerID);
        vehicleTypeSpinner = findViewById(R.id.spinner_vehicles);
        manufacturerText = findViewById(R.id.editManufacturer);
        modelText = findViewById(R.id.editModel);
        vehicleIdText = findViewById(R.id.editVehicleID);
        priceText = findViewById(R.id.editPrice);
        dateText = findViewById(R.id.dateEditView);
        DatePickerDialog.OnDateSetListener dateSetListener;
        rentalStatus = findViewById(R.id.rentalStatusGroup);
        Button addVehicle = findViewById(R.id.addVehicleButton);
        Button back = findViewById(R.id.backButton);

        /* rentalStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = rentalStatus.getCheckedRadioButtonId();
                rentalStatusButton = findViewById(radioId);
            }
        }); */

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vehicleTypes,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        vehicleTypeSpinner.setAdapter(adapter);
        vehicleTypeSpinner.setOnItemSelectedListener(this);

        addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dealerID = String.valueOf(dealerIdText.getText());
                vehicleType = String.valueOf(vehicleTypeSpinner);
                manufacturer = String.valueOf(manufacturerText.getText());
                model = String.valueOf(modelText.getText());
                vehicleID = String.valueOf(vehicleIdText.getText());
                price = Integer.valueOf(String.valueOf(priceText.getText()));
                date = Long.valueOf(String.valueOf(dateText.getText()));

                if (!dealerList.dealerExist(dealerID)) {
                    Toast.makeText(AddVehicleActivity.this,
                            "Error: Dealer does not exist. Please enter a valid dealer ID.", Toast.LENGTH_SHORT).show();
                } else if (!dealerList.getDealer(dealerID).getAcquisitionEnabled()) {
                    Toast.makeText(AddVehicleActivity.this,
                            "Error: Dealer does not have vehicle acquisition enabled. " +
                                    "Ensure dealer has aquisition enabled, or enter a different dealer ID.", Toast.LENGTH_SHORT).show();
                } else if (dealerList.getDealer(dealerID).vehicleExists(vehicleID)) {
                    Toast.makeText(AddVehicleActivity.this,
                            "Error: This vehicle is already in the inventory. Enter a different Vehicle ID", Toast.LENGTH_SHORT).show();
                } else {
                    dealerList.addToDealer(dealerID,
                            new Vehicle(dealerID, vehicleType, manufacturer, model, vehicleID, price, date));
                    Toast.makeText(AddVehicleActivity.this,
                            "Your vehicle has been added to the inventory.", Toast.LENGTH_SHORT).show();
                }

                dealerIdText.getText().clear();
                manufacturerText.getText().clear();
                modelText.getText().clear();
                vehicleIdText.getText().clear();
                priceText.getText().clear();
                dateText.getText().clear();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the intent with the new activity
                Intent intent = new Intent(AddVehicleActivity.this, MainActivity.class);

                // Launch the new Activity
                startActivity(intent);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
