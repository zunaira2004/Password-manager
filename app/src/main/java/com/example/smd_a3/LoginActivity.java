package com.example.smd_a3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button btnSignup, btnLogin;
    EditText etNameLogin, etPassLogin;
    boolean flag=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        init();


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAccount(); // Call loginAccount() here
            }
        });

    }
    protected void init()
    {
        btnSignup=findViewById(R.id.btnSignup);
        btnLogin=findViewById(R.id.btnLogin);
        etNameLogin=findViewById(R.id.etNameLogin);
        etPassLogin=findViewById(R.id.etPassLogin);

    }
    protected void loginAccount()
    {
        String name = etNameLogin.getText().toString().trim();
        String pass = etPassLogin.getText().toString();

        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper.open();

        try {
            flag = myDatabaseHelper.checkLogin(name, pass);
            if (flag) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);


                intent.putExtra("name",name);
                intent.putExtra("pass",pass);

                startActivity(intent);
                finish();
            } else {
                // Credentials are incorrect, show error message
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("LoginError", "Error logging in: " + e.getMessage());
            Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show();
        } finally {
            myDatabaseHelper.close();
        }
    }
}
