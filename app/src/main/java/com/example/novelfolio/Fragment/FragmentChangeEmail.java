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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FragmentChangeEmail extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_email, container, false);
        TextInputLayout newEmail = view.findViewById(R.id.inputChangeEmail);

        Button btnChangeEmail = view.findViewById(R.id.btnChangeEmailSave);
        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNewEmail = newEmail.getEditText().getText().toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.updateEmail(strNewEmail)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Email updated successfully
                                Toast.makeText(getContext(), "Updated Successfully.", Toast.LENGTH_LONG).show();
                            } else {
                                // Email update failed
                                Toast.makeText(getContext(), "Email update failed.", Toast.LENGTH_LONG).show();
                            }
                        });

                DocumentReference userDoc = FirebaseFirestore.getInstance().collection("users").document(user.getUid());
                Map<String, Object> updates = new HashMap<>();
                updates.put("email", strNewEmail);
                userDoc.update(updates);
            }
        });


        return view;
    }
}