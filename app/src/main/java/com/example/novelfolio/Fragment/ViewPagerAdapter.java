package com.example.novelfolio.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.novelfolio.Fragment.FavoritesTab;
import com.example.novelfolio.Fragment.NotesTab;

public class ViewPagerAdapter extends FragmentStateAdapter{

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0: return new FavoritesTab();
            case 1: return new NotesTab();
            default: return new FavoritesTab();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
