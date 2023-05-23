package com.example.novelfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.novelfolio.Fragment.FragmentForum;
import com.example.novelfolio.Fragment.FragmentHome;
import com.example.novelfolio.Fragment.FragmentLibrary;
import com.example.novelfolio.Fragment.FragmentMore;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

public class MainScreen extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);



        bottomNavigationView = findViewById(R.id.bottom_navigation);
        frameLayout = findViewById(R.id.frameLayout);



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                frameLayout.setVisibility(View.VISIBLE);

                switch(item.getItemId()){
                    case R.id.bot_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentHome()).commit();
                        return true;

                    case R.id.bot_library:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentLibrary()).commit();
                        return true;

                    case R.id.bot_forum:


                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentForum()).commit();
                        return true;

                    case R.id.bot_more:


                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentMore()).commit();
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.bot_home);
    }
}