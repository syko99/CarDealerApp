package edu.metrostate.cardealer;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class FileChooserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_chooser_list);

        File[] fileArray = populateFileList();

        final ListView fileList = (ListView) findViewById(R.id.file_list);
        FileListAdapter fileListAdapter = new FileListAdapter(this, fileArray);

        fileList.setAdapter(fileListAdapter);
        fileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                File file = (File) parent.getItemAtPosition(position);
//                Importer.importFile(file);
            }
        });
    }

    public File[] populateFileList(){
        File directory = getExternalFilesDir(null);
        File[] directoryArray = directory.listFiles();
        return directoryArray;
    }
}