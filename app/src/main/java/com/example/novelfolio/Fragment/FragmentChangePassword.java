package com.example.novelfolio.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.novelfolio.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentChangePassword extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        TextInputLayout password = view.findViewById(R.id.inputChangePassword);

        Button btnChangePassword = view.findViewById(R.id.btnChangePasswordSave);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.updatePassword(password.getEditText().getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Email updated successfully
                                Toast.makeText(getContext(), "Updated Successfully.", Toast.LENGTH_LONG).show();
                            } else {
                                // Email update failed
                                Toast.makeText(getContext(), "Password update failed.", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }

        );


        return view;
    }

}