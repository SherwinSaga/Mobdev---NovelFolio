package com.example.novelfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Synopsis extends AppCompatActivity implements ChapterAdapter.ChapterClickInterface {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mb = FirebaseAuth.getInstance();
    ImageView coverImg;
    TextView title;
    TextView author;
    TextView synopsis;
    RecyclerView recyclerView;
    ArrayList<Chapter> chapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synopsis);

        String novelDocId = getIntent().getStringExtra("novelDocId");
        coverImg = findViewById(R.id.novelDetailsCoverImg);
        title = findViewById(R.id.novelDetailsTitle);
        author = findViewById(R.id.novelDetailsAuthor);
        synopsis = findViewById(R.id.novelDetailsSynopsis);
        recyclerView = findViewById(R.id.novelDetailsChaptersList);
        ImageButton btnAddFav = findViewById(R.id.novelDetailsAddFav);

        displayDetails(novelDocId);

        btnAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    addToFav(novelDocId);
            }
        });
    }

    @Override
    public void onChapterClick(String novelDocId, int chapterNum) {
        Intent intent = new Intent(Synopsis.this, NovelContent.class);
        intent.putExtra("novelDocId", novelDocId);
        intent.putExtra("currentChapterNum", chapterNum);
        startActivity(intent);
    }

    public void displayDetails(String novelDocId) {
        DocumentReference dbNovels = db.collection("novels").document(novelDocId);
        dbNovels.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Glide.with(Synopsis.this).load(document.getString("imgUrl")).into(coverImg);
                        title.setText(document.getString("title"));
                        synopsis.setText(document.getString("synopsis"));
                        author.setText(document.getString("authorName"));
                    }
                }
            }
        });

        dbNovels.collection("chapters").orderBy("chapterNumber", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    chapters = new ArrayList<>();
                    List<Chapter> data = task.getResult().toObjects(Chapter.class);
                    chapters.addAll(data);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Synopsis.this));
                    ChapterAdapter adapter = new ChapterAdapter(Synopsis.this, chapters,novelDocId, Synopsis.this);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    public void addToFav(String novelDocId) {
        FirebaseUser user = mb.getCurrentUser();
        HashMap<String, Object> fav = new HashMap<>();
        fav.put("currChapterNum", 1);
        db.collection("users").document(user.getUid()).collection("favorites").document(novelDocId).set(fav).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Synopsis.this, "Added to favorites successfully", Toast.LENGTH_SHORT).show();
                        // Document successfully created
                    }
                });
    }
}