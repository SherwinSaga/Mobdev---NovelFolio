package com.example.novelfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.novelfolio.Fragment.NovelNotesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewNotes extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView title;
    TextView content;
    String dbTitle;
    String dbContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        title = findViewById(R.id.viewNoteTitle);
        content = findViewById(R.id.viewNoteContent);
        String docId = getIntent().getStringExtra("docId");
        String novelDocId = getIntent().getStringExtra("novelDocId");
        Button btnEdit = findViewById(R.id.btnEditNote);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewNotes.this, AddNoteTemplate.class);
                intent.putExtra("title", dbTitle);
                intent.putExtra("content", dbContent);
                intent.putExtra("docId", docId);
                intent.putExtra("novelDocId", novelDocId);
                startActivity(intent);
                finish();
            }
        });
        getNote(docId);
    }

    public void getNote(String docId) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        CollectionReference dbNotes = db.collection("users").document(currentUser.getUid()).collection("notes");
        dbNotes.document(docId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        dbTitle = document.getString("title");
                        dbContent = document.getString("content");
                        title.setText("Title : "+ dbTitle);
                        content.setText(dbContent);
                    }
                } else {
                    Log.w("MainAct", "Error getting documents.", task.getException());
                }
            }
        });
    }
}