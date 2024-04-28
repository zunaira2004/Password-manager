package com.example.smd_a3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    Button btnSign;
    EditText etNameSign, etPassSign, etRepassSign, etEmailSign, etPhoneSign;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        init();


        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();

                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    protected void init() {
        btnSign = findViewById(R.id.btnSign);
        etNameSign = findViewById(R.id.etNameSign);
        etPassSign = findViewById(R.id.etPassSign);
        etRepassSign = findViewById(R.id.etRepassSign);
        etEmailSign = findViewById(R.id.etEmailSign);
        etPhoneSign=findViewById(R.id.etPhoneSign);
    }
    protected void registerAccount()
    {
        String name = etNameSign.getText().toString().trim();
        String pass = etPassSign.getText().toString();
        String repass = etRepassSign.getText().toString();
        String email = etEmailSign.getText().toString();
        String phone = etPhoneSign.getText().toString();

        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper.open();

        if(!pass.equals(repass)) {
            Toast.makeText(this, "Password is not equal to Re-entered password", Toast.LENGTH_SHORT).show();
        }
        else{
            myDatabaseHelper.insertRegisterationData(name,pass,email,phone);
            myDatabaseHelper.close();
        }

    }
}
