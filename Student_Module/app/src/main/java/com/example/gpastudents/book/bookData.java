package com.example.gpastudents.book;

public class bookData {

    private String bookTitle,bookUrl;

    public bookData() {
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public bookData(String bookTitle, String bookUrl) {

        this.bookTitle = bookTitle;
        this.bookUrl = bookUrl;
    }
}
