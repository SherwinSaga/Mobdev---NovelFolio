package com.example.novelfolio;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button buttonLOGIN;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        buttonLOGIN = findViewById(R.id.btnLogin);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);

        buttonLOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign-in successful
                                    Toast.makeText(MainActivity.this, "Sign-in successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, MainScreen.class);
                                    startActivity(intent);
                                    // Proceed with your desired logic asd
                                } else {
                                    // Sign-in failed
                                    Toast.makeText(MainActivity.this, "Sign-in failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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