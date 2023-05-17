package com.example.novelfolio.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerNovelAdapter extends FragmentStateAdapter {

    public ViewPagerNovelAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0: return new ContentFragment();
            case 1:
                Bundle bundle = new Bundle();
                bundle.putString("test", "test");
                NovelNotesFragment x = new NovelNotesFragment();
                x.setArguments(bundle);
                return x;

            default:
                return new ContentFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
