package com.example.novelfolio.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.novelfolio.InFavorite;
import com.example.novelfolio.Novel;
import com.example.novelfolio.NovelCardAdapter;
import com.example.novelfolio.R;
import com.example.novelfolio.Synopsis;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FragmentLibrary extends Fragment implements NovelCardAdapter.NovelCardClickInterface {
    RecyclerView recyclerView;
    ArrayList<Novel> novels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        recyclerView = view.findViewById(R.id.novelsList);
        SearchView searchbar = view.findViewById(R.id.searchbar);
        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search when user submits the query
                getNovels(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform search as the text changes (optional)
                getNovels(newText);
                return true;
            }
        });

        getNovels("");
        return view;
    }

    @Override
    public void onNovelClick(String novelDocId, int chapterNum) {
        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("favorites").document(novelDocId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Intent intent = new Intent(getActivity(), InFavorite.class);
                        intent.putExtra("novelDocId", novelDocId);
                        intent.putExtra("currentChapterNum", chapterNum);
                        startActivity(intent);
                    } else {
                        //Toast.makeText(getContext(), "HELLLOoo", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), Synopsis.class);
                        intent.putExtra("novelDocId", novelDocId);
                        intent.putExtra("currentChapterNum", chapterNum);
                        startActivity(intent);
                    }
                }
            }
        });
    }


    public void getNovels(String query) {
        Query dbNovels = FirebaseFirestore.getInstance().collection("novels");
        if (!query.isEmpty()) {
            String queryLower = query.toLowerCase();
            dbNovels = dbNovels.orderBy("titleSearch").startAt(queryLower).endAt(queryLower + "\uf8ff").limit(10);
        }
        dbNovels.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    novels = new ArrayList<>();
                    List<Novel> data = task.getResult().toObjects(Novel.class);
                    novels.addAll(data);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
                    NovelCardAdapter adapter = new NovelCardAdapter(getContext(), novels, FragmentLibrary.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.w("MainAct", "Error getting documents.", task.getException());
                }
            }
        });
    }
}