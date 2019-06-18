package com.hutchgroup.elog.filesharing;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashSet;

public class ListActivity extends Activity implements FileRecycleAdapter.IFile {

    private RecyclerView recyclerView;
    private FileRecycleAdapter rAdapter;
    private Button btnCheckForUpdate;
    private EditText searchEditText;
    private ArrayList<FileBean> fileList;
    private HashSet<String> fileType = new HashSet<String>();
    private Spinner sp_searchoption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initialize();
    }

    private void initialize() {

        FileRecycleAdapter.mlistner = this;

        recyclerView = (RecyclerView) findViewById(R.id.lvFile);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        FileDataGet();
        btnCheckForUpdate = (Button) findViewById(R.id.btnCheckForUpdate);
        btnCheckForUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // backupDB();
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        searchEditText =(EditText) findViewById(R.id.searchEditText);
        sp_searchoption =(Spinner) findViewById(R.id.sp_searchoption);

        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
               filter(cs.toString().toLowerCase());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });





      for(FileBean file : fileList)
      {
          fileType.add(file.getFileType());
      }


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>(fileType));


        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sp_searchoption.setAdapter(dataAdapter);

        sp_searchoption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //stuff here to handle item selection
                String item = parent.getItemAtPosition(position).toString();
                filterByType(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    void filter(String text){
        ArrayList<FileBean> temp = new ArrayList();
        for(FileBean d: fileList){
            if( d.getFileName().toLowerCase().contains(text) ){

                temp.add(d);
            }
        }
        //update recyclerview
        rAdapter.updateList(temp);
    }

    void filterByType(String text){
        ArrayList<FileBean> temp = new ArrayList();
        for(FileBean d: fileList){


            if( d.getFileType().equalsIgnoreCase(text) ){

                temp.add(d);
            }
        }
        //update recyclerview
        rAdapter.updateList(temp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ListActivity.this != null) {
            initialize();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ListActivity.this != null) {
            // refresh list
            initialize();
        }
    }

    private void FileDataGet() {

        fileList = FileDB.getDownloadedFiles(this); // including power unit
        rAdapter = new FileRecycleAdapter(fileList);
        recyclerView.setAdapter(rAdapter);
    }

    private String backupDB() {
        String backupDBPath = "";
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + getPackageName() + "/databases/FileDatabase.db";
                backupDBPath = "Backup.db"; // LogFile.DATABASE_BACKUP_NAME;
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }
        return backupDBPath;
    }

    @Override
    public void onClick(FileBean file) {
        String type = file.getFileExtension();
        //Depends on file type open application
        try {
            if (type.equals(".pdf")) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/kmax/" + file.getFileName() + file.getFileExtension())),
                        "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } else if (type.equals(".mp4")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/kmax/" + file.getFileName() + file.getFileExtension())),
                        "video/mp4");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } else if (type.equals(".png") || type.equals(".jpeg")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/kmax/" + file.getFileName() + file.getFileExtension())),
                        "image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
