package com.example.novelfolio;

public class Novel {
    private String title;
    private int chapters;
    private String authorName;
    private String imgUrl;
    private String docId;
    private int currentChapterNumber;

    public Novel(String title, int chapters, String authorName, String imgUrl, String docId){
        this.title = title;
        this.chapters = chapters;
        this.authorName = authorName;
        this.imgUrl = imgUrl;
        this.docId = docId;
        this.currentChapterNumber = 1;
    }
    public Novel(){this.currentChapterNumber = 1;};
    public Novel(String title, int chapters, String authorName, String imgUrl, String docId, int currentChapterNumber){
        this.title = title;
        this.chapters = chapters;
        this.authorName = authorName;
        this.imgUrl = imgUrl;
        this.docId = docId;
        this.currentChapterNumber = currentChapterNumber;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public int getCurrentChapterNumber() {
        return currentChapterNumber;
    }

    public void setCurrentChapterNumber(int currentChapterNumber) {
        this.currentChapterNumber = currentChapterNumber;
    }
}
