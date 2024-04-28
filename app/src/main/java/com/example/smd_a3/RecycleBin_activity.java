package com.example.smd_a3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleBin_activity extends AppCompatActivity {

    ArrayList<passwordModel> binPasswords;
    RecyclerView rvBin;
    MyAdaptor adaptor;
    Button btnRestore,btnDeleted;
    String username;
    String pass;
    int userid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_bin);

        Intent intent=getIntent();
        username = intent.getStringExtra("name");
        pass = intent.getStringExtra("pass");

        init();

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restorePassword();
            }
        });

        btnDeleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deletePermanently();
            }
        });

    }
    void init()
    {
        btnRestore=findViewById(R.id.btnRestore);
        btnDeleted=findViewById(R.id.btnDeleted);
        binPasswords=new ArrayList<>();
        rvBin=findViewById(R.id.rvBin);
        rvBin.setHasFixedSize(true);


        MyDatabaseHelper database = new MyDatabaseHelper(this);
        database.open();
        userid= database.getUserId(username,pass);
        Log.d("Main activity", "userid: " + userid);

        binPasswords = database.readAllPasswordsDeleted(userid);
        database.close();

        rvBin.setLayoutManager(new LinearLayoutManager(this));
        adaptor=new MyAdaptor(binPasswords,this,this);
        rvBin.setAdapter(adaptor);
    }
    void deletePermanently(){
        MyDatabaseHelper database = new MyDatabaseHelper(this);
        database.open();
        database.deletePasswordDataPermanently(userid);
        database.close();
    }
    void restorePassword()
    {

    }
}
