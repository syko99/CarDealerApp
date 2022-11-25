package edu.metrostate.cardealer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ExportDealerAdapter extends ArrayAdapter<Dealer> {
    public ExportDealerAdapter(Context context, Dealer[] dealerArray) {
        super(context, R.layout.export_dealer_item, dealerArray);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.export_dealer_item, parent, false);
        }

        String dealerIdText = "ID: " + getItem(position).getDealerId();
        String dealerNameText = "Name: " + getItem(position).getName();

        TextView idView = convertView.findViewById(R.id.export_dealer_id);
        TextView nameView = convertView.findViewById(R.id.export_dealer_name);

        idView.setText(dealerIdText);
        nameView.setText(dealerNameText);

        return convertView;
    }

}