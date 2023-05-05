package com.example.novelfolio;

public class Novel {
    private String title;
    private int chapters;
    private String author;
    private int lastpageread;

    public Novel(String title, int chapters, String author, int lastpageread){
        this.title = title;
        this.chapters = chapters;
        this.author = author;
        this.lastpageread = lastpageread;
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

    public int getLastpageread() {
        return lastpageread;
    }

    public void setLastpageread(int lastpageread) {
        this.lastpageread = lastpageread;
    }
}
