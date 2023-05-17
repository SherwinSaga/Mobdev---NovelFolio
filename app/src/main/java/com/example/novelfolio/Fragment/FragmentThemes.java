package com.example.novelfolio.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novelfolio.R;


public class FragmentThemes extends Fragment {

    private TextView tf1;
    private TextView tf2;
    private TextView tf3;
    private TextView tf4;
    private TextView tf5;
    private TextView tf6;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_themes, container, false);

        tf1 = view.findViewById(R.id.tvDefault);
        tf2 = view.findViewById(R.id.tMidnightDusk);
        tf3 = view.findViewById(R.id.tYinYang);
        tf4 = view.findViewById(R.id.tGreenApple);
        tf5 = view.findViewById(R.id.tLavender);
        tf6 = view.findViewById(R.id.tTako);


        tf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "default", Toast.LENGTH_SHORT).show();
            }
        });

        tf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "MidnightDusk", Toast.LENGTH_SHORT).show();
            }
        });

        tf3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "YinYang", Toast.LENGTH_SHORT).show();
            }
        });


        tf4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "GreenApple", Toast.LENGTH_SHORT).show();
            }
        });


        tf5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Lavender", Toast.LENGTH_SHORT).show();
            }
        });


        tf6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Tako", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

}