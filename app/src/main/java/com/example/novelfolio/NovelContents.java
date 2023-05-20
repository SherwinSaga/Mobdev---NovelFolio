package com.example.novelfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.example.novelfolio.Fragment.ViewPagerNovelAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

public class NovelContents extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerNovelAdapter viewPagerNovelAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_contents);

        tabLayout = findViewById(R.id.novelContentTabLayout);
        viewPager2 = findViewById(R.id.viewPager);

        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("novelDocId", getIntent().getStringExtra("novelDocId"));
        int tabNum = getIntent().getIntExtra("tabNum", 0);
        viewPagerNovelAdapter = new ViewPagerNovelAdapter(this, arguments);
        viewPager2.setAdapter(viewPagerNovelAdapter);
        viewPager2.setCurrentItem(tabNum);
        tabLayout.getTabAt(tabNum).select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);

            }
        });
    }
}