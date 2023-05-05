package com.example.novelfolio;

import java.util.ArrayList;

public class Favorites {


    private ArrayList<Novel> favoriteslist;

    public Favorites(){
        favoriteslist = new ArrayList<>();
    }

    public void addToFavoriteList(Novel n){
        favoriteslist.add(n);
    }

    public ArrayList<Novel> getFavoriteslist() {
        return favoriteslist;
    }

    public boolean removefromFavorite(Novel n){
        return favoriteslist.remove(n);
    }

}
