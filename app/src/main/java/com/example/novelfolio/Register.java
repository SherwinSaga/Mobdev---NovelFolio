package com.example.novelfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button btnREGISTER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        btnREGISTER = findViewById(R.id.btnRegister);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText userName = findViewById(R.id.userName);

        btnREGISTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = email.getText().toString();

                mAuth.createUserWithEmailAndPassword(strEmail, password.getText().toString())
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser authUser = mAuth.getCurrentUser();
                                    User user = new User(userName.getText().toString() ,strEmail);
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    DocumentReference userRef = db.collection("users").document(authUser.getUid());
                                    userRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    System.out.print("sucess");
                                                    // Document successfully created
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    System.out.print(e.getMessage());
                                                    // Error creating document
                                                }
                                            });
                                    Intent intent = new Intent(Register.this, MainScreen.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }


}