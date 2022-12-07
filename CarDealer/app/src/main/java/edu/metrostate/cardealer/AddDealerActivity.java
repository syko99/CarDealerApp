package edu.metrostate.cardealer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddDealerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dealer);

        Button addDealerButton = findViewById(R.id.btnAddDealer);
        CheckBox acquisitionCheckBox = findViewById(R.id.checkBox);
        EditText dealerNameEditText = findViewById(R.id.etDealershipName);
        EditText dealerIdEditText = findViewById(R.id.etDealershipId);

        addDealerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dealerId = dealerIdEditText.getText().toString();
                String dealerName = dealerNameEditText.getText().toString();
                boolean acquisitionValue = acquisitionCheckBox.isChecked();
                Toast.makeText(AddDealerActivity.this, "Id:"+dealerId+" Name:"+dealerName, Toast.LENGTH_SHORT).show();
            }
        });

    }
}