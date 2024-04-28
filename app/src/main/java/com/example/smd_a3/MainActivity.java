package com.example.smd_a3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvPasswords;
    ArrayList<passwordModel> passwords;
    MyAdaptor myAdaptor;
    FloatingActionButton fabAdd;
    String username;
    String pass;
    Button btnMore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        username = intent.getStringExtra("name");
        pass=intent.getStringExtra("pass");

        //this is null
        Log.d("Main activity", "username: " + username);
        Log.d("Main activity", "Password: " + pass);


        init();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,addPasswordActivity.class);
                intent.putExtra("name",username);
                intent.putExtra("pass",pass);
                startActivity(intent);
                finish();
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MoreActivity.class);
                intent.putExtra("name",username);
                intent.putExtra("pass",pass);
                startActivity(intent);
            }
        });
    }
    void init()
    {

        btnMore=findViewById(R.id.btnMore);
        rvPasswords=findViewById(R.id.rvPasswords);
        fabAdd=findViewById(R.id.fabAdd);
        rvPasswords.setHasFixedSize(true);
        passwords=new ArrayList<>();

        MyDatabaseHelper database = new MyDatabaseHelper(this);
        database.open();
        int userid= database.getUserId(username,pass);
        Log.d("Main activity", "userid: " + userid);

        passwords = database.readAllPasswords(userid);
        database.close();

        rvPasswords.setLayoutManager(new LinearLayoutManager(this));
        myAdaptor=new MyAdaptor(passwords,this);
        rvPasswords.setAdapter(myAdaptor);
    }
}