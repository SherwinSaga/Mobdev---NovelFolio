package com.example.novelfolio.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.novelfolio.Chapter;
import com.example.novelfolio.ChapterAdapter;
import com.example.novelfolio.Note;
import com.example.novelfolio.NoteAdapter;
import com.example.novelfolio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class NovelNotesFragment extends Fragment {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    ArrayList<Note> notes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_novel_notes, container, false);
        Button button = view.findViewById(R.id.createNovelNote);
        recyclerView = view.findViewById(R.id.listNovelNotes);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNovelNote();
            }
        });

        getNovelNotes();
        return view;
    }

    public void createNovelNote() {
        //FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        //Fragment fragment = new AnotherFragment();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        Note note = new Note("Background", "lorem ipsum", "KgpW8IuHIbnn5hm17cyg");
        db.collection("users").document(authUser.getUid()).collection("notes").add(note);

    }

    public void getNovelNotes() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        CollectionReference dbNotes = db.collection("users").document(currentUser.getUid()).collection("notes");
        dbNotes.whereEqualTo("novelId", "KgpW8IuHIbnn5hm17cyg").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    notes = new ArrayList<>();
                    List<Note> data = task.getResult().toObjects(Note.class);
                    notes.addAll(data);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    NoteAdapter adapter = new NoteAdapter(getContext(), notes);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.w("MainAct", "Error getting documents.", task.getException());
                }
            }
        });
    }
}