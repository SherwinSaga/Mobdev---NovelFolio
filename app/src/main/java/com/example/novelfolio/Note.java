package com.example.novelfolio;

import java.io.Serializable;

public class Note implements Serializable {
    private String title;
    private String content;
    private String novelId;

    public Note(String title, String content, String novelId) {
        this.title = title;
        this.content = content;
        this.novelId = novelId;
    }
    public Note(){};
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNovelId() {
        return novelId;
    }

    public void setNovelId(String novelId) {
        this.novelId = novelId;
    }
}
