package com.example.novelfolio;

import java.util.ArrayList;

public class User {
    private String username;
    private ArrayList<Novel> userfavoritelist;

    public User(String username){
        this.username = username;
        userfavoritelist = new ArrayList<>();
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void addToFavoriteList(Novel n){
        userfavoritelist.add(n);
    }

    public ArrayList<Novel> getFavoriteslist() {
        return userfavoritelist;
    }

    public boolean removefromFavorite(Novel n){
        return userfavoritelist.remove(n);
    }
}
