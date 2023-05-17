package com.example.novelfolio;

public class Chapter {
    public String name;
    public int chapterNumber;
    public String content;

    public Chapter(String name, int chapterNumber, String content) {
        this.name = name;
        this.chapterNumber = chapterNumber;
        this.content = content;
    }

    public Chapter() {}

    public String getName() {
        return name;
    }


    public int getChapterNumber() {
        return chapterNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
