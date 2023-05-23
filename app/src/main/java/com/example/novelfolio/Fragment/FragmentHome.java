package com.example.novelfolio.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.novelfolio.InFavorite;
import com.example.novelfolio.Novel;
import com.example.novelfolio.NovelCardAdapter;
import com.example.novelfolio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;


public class FragmentHome extends Fragment implements NovelCardAdapter.NovelCardClickInterface{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    ArrayList<Novel> novels;
    NovelCardAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.favoritenovels);

        getNovels();
        return view;
    }

    public void onNovelClick(String novelDocId, int chapterNum) {
        Intent intent = new Intent(getActivity(), InFavorite.class);
        intent.putExtra("novelDocId", novelDocId);
        intent.putExtra("currentChapterNum", chapterNum);
        startActivity(intent);
    }


    public void getNovels() {
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HashMap<String, Object> favNovels = new HashMap<>();
        ArrayList<String> novelDocIds = new ArrayList<>();
        CollectionReference dbFavNovels = db.collection("users").document(uId).collection("favorites");
        dbFavNovels.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    ArrayList<String> novelDocIds = new ArrayList<>();
                    for (QueryDocumentSnapshot document : value) {
                        String novelDocId = document.getId();
                        favNovels.put("novelDocId", novelDocId);
                        favNovels.put(novelDocId + " currChapterNum", document.getLong("currChapterNum").intValue());
                        novelDocIds.add(novelDocId);
                    }

                    if (!novelDocIds.isEmpty()) {
                        CollectionReference dbNovels = db.collection("novels");
                        dbNovels.whereIn(FieldPath.documentId(), novelDocIds).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    novels = new ArrayList<>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Novel novel = new Novel(document.getString("title"), document.getString("authorName"), document.getString("imgUrl"), document.getId(), Integer.parseInt(favNovels.get(document.getId() + " currChapterNum").toString()));
                                        novels.add(novel);
                                    }
                                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
                                    adapter = new NovelCardAdapter(getContext(), novels, FragmentHome.this);
                                    recyclerView.setAdapter(adapter);
                                }
                            }
                        });

                    }
                }
            }
        });

    }
}