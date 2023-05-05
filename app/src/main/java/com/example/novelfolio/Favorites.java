package com.example.novelfolio;

import java.util.ArrayList;

public class Favorites {

    private ArrayList<Novel> favoriteslist;
    private int lastpageread;

    public Favorites(){
        favoriteslist = new ArrayList<>();
        lastpageread = 0;
    }

    public void addToFavoriteList(Novel n){
        favoriteslist.add(n);
    }

    public void setLastpageread(int n){
        lastpageread = n;
    }

    public ArrayList<Novel> getFavoriteslist() {
        return favoriteslist;
    }

    public int getLastpageread(){
        return lastpageread;
    }

}
