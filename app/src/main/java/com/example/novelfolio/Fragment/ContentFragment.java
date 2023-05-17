package com.example.novelfolio.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.novelfolio.Chapter;
import com.example.novelfolio.ChapterAdapter;
import com.example.novelfolio.NovelContent;
import com.example.novelfolio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ContentFragment extends Fragment implements ChapterAdapter.ChapterClickInterface {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    ArrayList<Chapter> chapters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        recyclerView = view.findViewById(R.id.chaptersList);

        getChapters();
        return view;
    }

    @Override
    public void onChapterClick(String novelDocId, int chapterNum) {
        Intent intent = new Intent(getActivity(), NovelContent.class);
        intent.putExtra("novelDocId", novelDocId);
        intent.putExtra("currentChapterNum", chapterNum);
        startActivity(intent);
    }

    public void getChapters() {
        CollectionReference dbChapters = db.collection("novels").document("KgpW8IuHIbnn5hm17cyg").collection("chapters");
        dbChapters.orderBy("chapterNumber", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    chapters = new ArrayList<>();
                    List<Chapter> data = task.getResult().toObjects(Chapter.class);
                    chapters.addAll(data);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    ChapterAdapter adapter = new ChapterAdapter(getContext(), chapters, ContentFragment.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.w("MainAct", "Error getting documents.", task.getException());
                }
            }
        });
    }
}