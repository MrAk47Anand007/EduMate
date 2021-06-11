package com.example.gpastudents.submission;

public class submissionData {
    String title,title1,lastdate,date,time,key;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public submissionData() {
    }

    public submissionData(String title, String title1, String lastdate, String date, String time, String key) {
        this.title = title;
        this.title1 = title1;
        this.lastdate = lastdate;
        this.date = date;
        this.time = time;
        this.key = key;
    }
}
