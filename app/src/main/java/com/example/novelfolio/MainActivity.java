package com.example.novelfolio;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button buttonLOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLOGIN = findViewById(R.id.btnLogin);

        buttonLOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }


    public void viewForgotPAssword(View view) {
        Toast.makeText(this, "FORGOT PASSWORD", Toast.LENGTH_SHORT).show();
    }

    public void viewRegisterClicked(View view) {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }
}