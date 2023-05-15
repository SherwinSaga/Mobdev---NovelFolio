package com.example.novelfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class NovelContent extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView chapterTitle;
    private TextView chapterContent;
    private Button prevButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_content);
        chapterTitle = findViewById(R.id.chaptertitle);
        chapterContent = findViewById(R.id.chapterContent);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);

        String novelDocId = getIntent().getStringExtra("novelDocId");
        int currChapterNum = getIntent().getIntExtra("currentChapterNum", 1);
        if (currChapterNum <= 1) {
            prevButton.setEnabled(false);
            prevButton.setAlpha(0.5f);
        }
        CollectionReference chapters = db.collection("novels").document(novelDocId).collection("chapters");
        chapters.whereEqualTo("chapterNumber", currChapterNum).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        chapterTitle.setText("Chapter " + document.getLong("chapterNumber") + ": " + document.getString("name"));
                        chapterContent.setText(document.getString("content"));
                        Log.d("MainAct", document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.w("MainAct", "Error getting documents.", task.getException());
                }
            }
        });

        chapters.whereEqualTo("chapterNumber", currChapterNum + 1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Boolean f = true;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        f = false;
                    }
                    if (f) {
                        nextButton.setEnabled(false);
                        nextButton.setAlpha(0.5f);
                    }
                }
            }
        });
    }
    public void nextNovel(View v) {
        Intent intent = new Intent(NovelContent.this, NovelContent.class);
        intent.putExtra("novelDocId", "KgpW8IuHIbnn5hm17cyg");
        intent.putExtra("currentChapterNum",2 );
        startActivity(intent);
    }
    public void prevNovel(View v) {
        Intent intent = new Intent(NovelContent.this, NovelContent.class);
        intent.putExtra("novelDocId", "KgpW8IuHIbnn5hm17cyg");
        intent.putExtra("currentChapterNum",1 );
        startActivity(intent);
    }
}