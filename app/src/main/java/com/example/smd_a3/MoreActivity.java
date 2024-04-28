package com.example.smd_a3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MoreActivity extends AppCompatActivity {
    Button btnBin, btnLogout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_activity);

        Intent intent=getIntent();
        String username = intent.getStringExtra("name");
        String pass = intent.getStringExtra("pass");
        init();

        btnBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MoreActivity.this, RecycleBin_activity.class);
                intent.putExtra("name",username);
                intent.putExtra("pass",pass);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MoreActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    void init()
    {
        btnBin=findViewById(R.id.btnBin);
        btnLogout=findViewById(R.id.btnLogout);
    }
}
