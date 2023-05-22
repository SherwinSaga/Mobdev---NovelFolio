package com.example.novelfolio.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novelfolio.Ei_port;
import com.example.novelfolio.MainActivity;
import com.example.novelfolio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentMore extends Fragment {

    private Switch switcher;
    private Switch notif;
    private boolean nightMode;

    private TextView details;
    private TextView Backup;
    static boolean isON;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String NOTIF_SWITCH_STATE = "notifSwitchState";



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        View view = inflater.inflate(R.layout.fragment_more, container, false);


        details = view.findViewById(R.id.edit_details);
        Backup = view.findViewById(R.id.tfBackUP);
        switcher = view.findViewById(R.id.darkModeSwitch);
        notif = view.findViewById(R.id.switchNotif);
        TextView logout = view.findViewById(R.id.menuLogout);

        nightMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;

        switcher.setChecked(nightMode);

        // Restore the state of the notifications switch from SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        isON = prefs.getBoolean(NOTIF_SWITCH_STATE, false);
        notif.setChecked(isON);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        TextView name = view.findViewById(R.id.moreUserName);
        FirebaseFirestore.getInstance().collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                name.setText(task.getResult().getString("name"));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Successfully Log out.", Toast.LENGTH_SHORT).show();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = requireActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightMode = !nightMode;
                if (nightMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });


        Backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(getActivity(), Ei_port.class);
                startActivity(intent4);
            }
        });

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment FragmentAccDetails = new FragmentAccountDetails();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, FragmentAccDetails);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    /*
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "My notification");
                    builder.setContentTitle("NovelFolio");
                    builder.setContentText("ENABLED NOTIFICATIONS");
                    builder.setSmallIcon(R.drawable.ic_launcher_background);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    managerCompat.notify(1, builder.build());
                    */
                    Toast.makeText(requireContext(), "Notifications Enabled", Toast.LENGTH_SHORT).show();
                    isON = true;
                } else {
                    Toast.makeText(requireContext(), "Notifications disabled", Toast.LENGTH_SHORT).show();
                    isON = false;
                }
                // Save the state of the notifications switch to SharedPreferences
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(NOTIF_SWITCH_STATE, isON);
                editor.apply();
                sharedViewModel.setIsON(isON);
            }
        });

        return view;
    }


}
