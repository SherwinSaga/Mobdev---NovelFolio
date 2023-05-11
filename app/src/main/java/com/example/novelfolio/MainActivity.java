package com.example.novelfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        frameLayout = findViewById(R.id.frameLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                    case 1:
                        tabLayout.getTabAt(position).select();
                }
                super.onPageSelected(position);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                frameLayout.setVisibility(View.VISIBLE);
                viewPager2.setVisibility(View.GONE);
                switch(item.getItemId()){
                    case R.id.bot_home:
                        //viewPager2.setVisibility(View.VISIBLE);
                        tabLayout.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentHome()).commit();
                        return true;

                    case R.id.bot_library:
                        viewPager2.setVisibility(View.GONE);
                        tabLayout.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentLibrary()).commit();
                        return true;

                    case R.id.bot_forum:
                        viewPager2.setVisibility(View.GONE);
                        tabLayout.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentForum()).commit();
                        return true;

                    case R.id.bot_more:
                        viewPager2.setVisibility(View.GONE);
                        tabLayout.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentMore()).commit();
                        return true;
                }
                return false;
            }
        });
    }

    
}