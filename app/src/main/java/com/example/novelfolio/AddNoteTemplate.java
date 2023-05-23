package com.example.novelfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddNoteTemplate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_template);
        Button btnSave = findViewById(R.id.btnSaveNote);
        EditText title = findViewById(R.id.addNoteTitle);
        EditText content = findViewById(R.id.addNoteContent);
        String novelDocId = getIntent().getStringExtra("novelDocId");
        String docId = getIntent().getStringExtra("docId");
        title.setText(getIntent().getStringExtra("title"));
        content.setText(getIntent().getStringExtra("content"));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (docId != null) {
                    updateNovelNote(docId,title.getText().toString(), content.getText().toString());
                } else {
                    createNovelNote(novelDocId, title.getText().toString(), content.getText().toString());
                }
                Intent intent = new Intent(AddNoteTemplate.this, NovelContents.class);
                intent.putExtra("novelDocId", novelDocId);
                intent.putExtra("tabNum", 1);
                startActivity(intent);
                finish();
            }
        });
    }
    public void updateNovelNote(String docId, String title, String content) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("title", title);
        updates.put("content", content);
        db.collection("users").document(authUser.getUid()).collection("notes").document(docId).update(updates);
    }

    public void createNovelNote(String novelDocId, String title, String content) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        Note note = new Note(title, content, novelDocId);
        db.collection("users").document(authUser.getUid()).collection("notes").add(note);
    }
}