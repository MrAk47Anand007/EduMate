package com.example.gpastudents.curriculum;

public class curriculumdata {

    private String carriculumTitle,carriculumUrl;

    public curriculumdata() {
    }

    public curriculumdata(String carriculumTitle, String carriculumUrl) {
        this.carriculumTitle = carriculumTitle;
        this.carriculumUrl = carriculumUrl;
    }

    public String getCarriculumTitle() {
        return carriculumTitle;
    }

    public void setCarriculumTitle(String carriculumTitle) {
        this.carriculumTitle = carriculumTitle;
    }

    public String getCarriculumUrl() {
        return carriculumUrl;
    }

    public void setCarriculumUrl(String carriculumUrl) {
        this.carriculumUrl = carriculumUrl;
    }
}
