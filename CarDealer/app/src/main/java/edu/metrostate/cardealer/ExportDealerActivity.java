package edu.metrostate.cardealer;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class ExportDealerActivity extends AppCompatActivity {
    Dealer selectedDealer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_dealer_list);

        Dealer[] dealerArray = populateDealerArray();

        final ListView dealerListview = (ListView) findViewById(R.id.export_dealer_list);
        ExportDealerAdapter dealerAdapter = new ExportDealerAdapter(this, dealerArray);

        dealerListview.setAdapter(dealerAdapter);
        dealerListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedDealer = (Dealer) parent.getItemAtPosition(position);

            }
        });

        findViewById(R.id.export_btn).setOnClickListener(new View.OnClickListener() {

            Toast toast;
            Context context = getApplicationContext();
            CharSequence msg;
            int duration = Toast.LENGTH_SHORT;


            @Override
            public void onClick(View v) {
                if (selectedDealer != null) {
                    Exporter.exportDealerJson(getExternalFilesDir(null).getAbsolutePath() + "/",  selectedDealer.getDealerId());
                    msg = "Export Successful.";
                    toast = Toast.makeText(context, msg, duration);
                    toast.show();
                } else {
                    msg = "Export Failed.";
                    toast = Toast.makeText(context, msg, duration);
                    toast.show();
                }
            }

        });
    }

    public Dealer[] populateDealerArray() {
        List<Dealer> dealerList = CarDealerApplication.dealerList.getDealerList();
        Dealer[] dealerArray = new Dealer[dealerList.size()];
        dealerArray = dealerList.toArray(dealerArray);
        return dealerArray;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Exporter.exportSaveFile(getExternalFilesDir(null).getAbsolutePath());
    }
}
