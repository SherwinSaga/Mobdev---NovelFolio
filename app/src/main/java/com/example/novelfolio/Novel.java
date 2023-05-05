package com.example.novelfolio;

public class Novel {
    private String title;
    private int chapters;
    private String author;

    public Novel(String title, int chapters, String author){
        this.title = title;
        this.chapters = chapters;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChapters() {
        return chapters;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
