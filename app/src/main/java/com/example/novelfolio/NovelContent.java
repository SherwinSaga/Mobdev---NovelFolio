package com.example.novelfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import yuku.ambilwarna.AmbilWarnaDialog;

import java.util.HashMap;

public class NovelContent extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView chapterTitle;
    private TextView chapterContent;
    private Button prevButton;
    private Button nextButton;
    private Button contentButton;
    private  int defaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_content);
        chapterTitle = findViewById(R.id.chaptertitle);
        chapterContent = findViewById(R.id.chapterContent);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        defaultColor = chapterTitle.getCurrentTextColor();

        contentButton = findViewById(R.id.btnContents);

        String novelDocId = getIntent().getStringExtra("novelDocId");
        int currChapterNum = getIntent().getIntExtra("currentChapterNum", 1);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("currChapterNum", currChapterNum);
        db.collection("users").document(user.getUid()).collection("favorites").document(novelDocId).update(updates);

        contentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NovelContent.this, NovelContents.class);
                intent.putExtra("novelDocId", novelDocId);
                intent.putExtra("currChapterNum", currChapterNum);
                startActivity(intent);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NovelContent.this, NovelContent.class);
                intent.putExtra("novelDocId", novelDocId);
                intent.putExtra("currentChapterNum",currChapterNum - 1);
                startActivity(intent);
                finish();

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NovelContent.this, NovelContent.class);
                intent.putExtra("novelDocId", novelDocId);
                intent.putExtra("currentChapterNum",currChapterNum + 1);
                startActivity(intent);
                finish();
            }
        });
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.txtInc:
                increaseFontSize();
                return true;
            case R.id.txtDec:
                decreaseFontSize();
                return true;
            case R.id.txtcolor:
                openColorPicker();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void increaseFontSize() {
        float currentSize = chapterTitle.getTextSize() / getResources().getDisplayMetrics().scaledDensity;
        chapterTitle.setTextSize(currentSize + 1);
        chapterContent.setTextSize(currentSize + 1);
    }

    private void decreaseFontSize() {
        float currentSize = chapterTitle.getTextSize() / getResources().getDisplayMetrics().scaledDensity;
        chapterTitle.setTextSize(currentSize - 1);
        chapterContent.setTextSize(currentSize - 1);
    }

    private void openColorPicker(){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                chapterContent.setTextColor(defaultColor);
                chapterTitle.setTextColor(defaultColor);
            }
        });
        ambilWarnaDialog.show();
    }
}