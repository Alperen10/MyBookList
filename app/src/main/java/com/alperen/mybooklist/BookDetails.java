package com.alperen.mybooklist;

import android.graphics.Bitmap;

public class BookDetails {
    private String bookName,bookAuthor,bookSummary;
    private Bitmap bookImage;

    public BookDetails(String bookName, String bookAuthor, String bookSummary, Bitmap bookImage) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookSummary = bookSummary;
        this.bookImage = bookImage;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookSummary() {
        return bookSummary;
    }

    public Bitmap getBookImage() {
        return bookImage;
    }
}
