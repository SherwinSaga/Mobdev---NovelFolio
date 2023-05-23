package com.example.novelfolio.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.novelfolio.AddNoteTemplate;
import com.example.novelfolio.Note;
import com.example.novelfolio.NoteAdapter;
import com.example.novelfolio.R;
import com.example.novelfolio.ViewNotes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class NovelNotesFragment extends Fragment implements NoteAdapter.NoteClickInterface {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    ArrayList<Note> notes;
    String novelDocId;

    @Override
    public void onNoteClick(String docId) {
        Intent intent = new Intent(getContext(), ViewNotes.class);
        intent.putExtra("docId", docId);
        intent.putExtra("novelDocId", novelDocId);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_novel_notes, container, false);
        FloatingActionButton button = view.findViewById(R.id.createNovelNote);
        recyclerView = view.findViewById(R.id.listNovelNotes);
        Bundle bundle = getArguments();

        novelDocId = bundle.getString("novelDocId");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNoteTemplate.class);
                intent.putExtra("novelDocId", novelDocId);
                startActivity(intent);
            }
        });

        getNovelNotes(novelDocId);
        return view;
    }


    public void getNovelNotes(String novelDocId) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        CollectionReference dbNotes = db.collection("users").document(currentUser.getUid()).collection("notes");
        dbNotes.whereEqualTo("novelId", novelDocId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    notes = new ArrayList<>();
                    for (QueryDocumentSnapshot document : value) {
                        Note note = new Note(document.getString("title"), document.getString("content"), document.getString("novelId"), document.getId());
                        notes.add(note);
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    NoteAdapter adapter = new NoteAdapter(getContext(), notes, NovelNotesFragment.this);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }
}