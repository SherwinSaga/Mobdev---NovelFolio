package com.example.novelfolio.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.novelfolio.R;


public class FragmentAccountDetails extends Fragment {

    TextView tfeditEmail;
    TextView tfeditPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_edit_account_details, container, false);

        tfeditEmail = view.findViewById(R.id.tfEMAIL);
        tfeditPass = view.findViewById(R.id.tfEditPassword);


        tfeditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment FragmentEmail = new FragmentChangeEmail();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, FragmentEmail);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        tfeditPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment FragmentPass = new FragmentChangePassword();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, FragmentPass);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }
}