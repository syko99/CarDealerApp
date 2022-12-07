package edu.metrostate.cardealer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EditDealerAdapter extends ArrayAdapter<Dealer> {
    public EditDealerAdapter(Context context, List<Dealer> dealerList) {
        super(context, R.layout.dealer_item, dealerList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dealer_item, parent, false);
        }
        String dealerIdText = "ID: " + getItem(position).getDealerId();
        String dealerNameText = "Name: " + getItem(position).getName();

        TextView id = convertView.findViewById(R.id.dealer_id);
        TextView name = convertView.findViewById(R.id.dealer_name);

        id.setText(dealerIdText);
        name.setText(dealerNameText);

        return convertView;
    }
}
