package edu.metrostate.cardealer;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class FileChooserActivity extends AppCompatActivity {

    File selectedFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_chooser_list);

        File[] fileArray = populateFileList();

        final ListView fileList = (ListView) findViewById(R.id.export_dealer_list);
        FileChooserAdapter fileListAdapter = new FileChooserAdapter(this, fileArray);

        fileList.setAdapter(fileListAdapter);
        fileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedFile = (File) parent.getItemAtPosition(position);

            }
        });

        findViewById(R.id.import_btn).setOnClickListener(new View.OnClickListener() {

            Toast toast;
            Context context = getApplicationContext();
            CharSequence msg;
            int duration = Toast.LENGTH_SHORT;

            @Override
            public void onClick(View v) {
                if (selectedFile != null) {
                    Importer.importFile(selectedFile);
                    Exporter.exportSaveFile(getExternalFilesDir(null).getAbsolutePath());
                    msg = "Import Successful.";
                    toast = Toast.makeText(context, msg, duration);
                    toast.show();
                } else {
                    msg = "Import Failed.";
                    toast = Toast.makeText(context, msg, duration);
                    toast.show();
                }
            }

        });
    }

    public File[] populateFileList() {
        File directory = getExternalFilesDir(null);
        File[] directoryArray = directory.listFiles();
        return directoryArray;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Exporter.exportSaveFile(getExternalFilesDir(null).getAbsolutePath());
    }
}