package edu.metrostate.cardealer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class editDealerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dealer);

        EditDealerAdapter adapter = new EditDealerAdapter(this,CarDealerApplication.dealerList.getDealerList());

        ListView dealerList = findViewById(R.id.edit_dealer_list);

        dealerList.setAdapter(adapter);


    }








}