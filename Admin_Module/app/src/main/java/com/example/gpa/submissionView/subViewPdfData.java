package com.example.gpa.submissionView;

public class subViewPdfData {
    public subViewPdfData() {
    }

    private String StudID, StudName, SubjectName, date, pdfTitle, pdfUrl, time ,attendMarks,performMarks;

    public String getStudID() {
        return StudID;
    }

    public void setStudID(String studID) {
        StudID = studID;
    }

    public String getStudName() {
        return StudName;
    }

    public void setStudName(String studName) {
        StudName = studName;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public void setPdfTitle(String pdfTitle) {
        this.pdfTitle = pdfTitle;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAttendMarks() {
        return attendMarks;
    }

    public void setAttendMarks(String attendMarks) {
        this.attendMarks = attendMarks;
    }

    public String getPerformMarks() {
        return performMarks;
    }

    public void setPerformMarks(String performMarks) {
        this.performMarks = performMarks;
    }

    public subViewPdfData(String studID, String studName, String subjectName, String date, String pdfTitle, String pdfUrl, String time, String attendMarks, String performMarks) {
        StudID = studID;
        StudName = studName;
        SubjectName = subjectName;
        this.date = date;
        this.pdfTitle = pdfTitle;
        this.pdfUrl = pdfUrl;
        this.time = time;
        this.attendMarks = attendMarks;
        this.performMarks = performMarks;
    }
}



