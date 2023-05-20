package com.example.novelfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.novelfolio.Fragment.NovelNotesFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddNoteTemplate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_template);
        Button btnSave = findViewById(R.id.btnSaveNote);
        EditText title = findViewById(R.id.addNoteTitle);
        EditText content = findViewById(R.id.addNoteContent);
        String novelDocId = getIntent().getStringExtra("novelDocId");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNovelNote(novelDocId, title.getText().toString(), content.getText().toString());

                Intent intent = new Intent(AddNoteTemplate.this, NovelContents.class);
                intent.putExtra("novelDocId", novelDocId);
                intent.putExtra("tabNum", 1);
                startActivity(intent);
            }
        });
    }

    public void createNovelNote(String novelDocId, String title, String content) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        Note note = new Note(title, content, novelDocId);
        db.collection("users").document(authUser.getUid()).collection("notes").add(note);
    }
}