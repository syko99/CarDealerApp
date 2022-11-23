package edu.metrostate.cardealer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;

public class FileListAdapter extends ArrayAdapter<File> {
    public FileListAdapter(Context context, File[] fileList) {
        super(context, R.layout.file_item, fileList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.file_item, parent, false);
        }

        TextView id = convertView.findViewById(R.id.file_name);
        id.setText(getItem(position).getName());



        return convertView;
    }

}
