package com.example.gpastudents.oldPaper;

public class oldPaperData {
private String paperTitle,paperUrl;

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getPaperUrl() {
        return paperUrl;
    }

    public void setPaperUrl(String paperUrl) {
        this.paperUrl = paperUrl;
    }

    public oldPaperData() {
    }

    public oldPaperData(String paperTitle, String paperUrl) {
        this.paperTitle = paperTitle;
        this.paperUrl = paperUrl;
    }
}
