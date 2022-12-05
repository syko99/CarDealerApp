package edu.metrostate.cardealer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class VehicleListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_list);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(CarDealerApplication.dealerList.printFullInventory());
        title.setMovementMethod(new ScrollingMovementMethod());
    }

    public void showDialog(Vehicle vehicle) {

        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("My alert")
                .setCancelable(false)
                .setTitle("Vehicle ID: " + vehicle.getId())
                .setMessage("Model: " + vehicle.getModel())
                .setPositiveButton("OK", (dialog1, id) -> dialog1.dismiss()).create();

        dialog.show();


    }
}