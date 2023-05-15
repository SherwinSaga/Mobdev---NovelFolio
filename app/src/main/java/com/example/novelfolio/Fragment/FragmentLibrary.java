package com.example.novelfolio.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.novelfolio.MainActivity;
import com.example.novelfolio.NovelContent;
import com.example.novelfolio.R;


public class FragmentLibrary extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNovel();
            }
        });

        return view;
    }


    public void openNovel() {
        Intent intent = new Intent(getActivity(), NovelContent.class);
        intent.putExtra("novelDocId", "KgpW8IuHIbnn5hm17cyg");
        intent.putExtra("currentChapterNum", 1);
        startActivity(intent);
    }
}