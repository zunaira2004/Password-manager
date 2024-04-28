package com.example.smd_a3;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class addPasswordActivity extends AppCompatActivity {

    EditText etNameAdd, etPassAdd, etLinkAdd;
    Button btnAddData;
    String name,pass;
    int userID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_password);

        Intent intent=getIntent();
        name = intent.getStringExtra("name");
        pass=intent.getStringExtra("pass");


        init();


        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addData();

                Intent intent = new Intent(addPasswordActivity.this, MainActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("pass",pass);
                startActivity(intent);
                finish();
            }
        });

    }
    void init()
    {
        etNameAdd=findViewById(R.id.etNameAdd);
        etPassAdd=findViewById(R.id.etPassAdd);
        etLinkAdd=findViewById(R.id.etLinkAdd);

        btnAddData=findViewById(R.id.btnAddData);

    }
    void addData(){
        String username= etNameAdd.getText().toString();
        String password= etPassAdd.getText().toString();
        String link= etLinkAdd.getText().toString();

//        Log.d("addPasswordActivity", "userId: " + uId);


        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper.open();
        userID= myDatabaseHelper.getUserId(name,pass);

        myDatabaseHelper.insertPasswordData(username, password,link, userID);

        myDatabaseHelper.close();

    }
}
