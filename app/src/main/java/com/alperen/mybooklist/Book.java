package com.alperen.mybooklist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Book{
    private String bookName,bookAuthor,bookSummary;
    private Bitmap bookImage;

    public Book(){

    }

    public Book(String bookName, String bookAuthor, String bookSummary, Bitmap bookImage) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookSummary = bookSummary;
        this.bookImage = bookImage;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookSummary() {
        return bookSummary;
    }

    public void setBookSummary(String bookSummary) {
        this.bookSummary = bookSummary;
    }

    public Bitmap getBookImage() {
        return bookImage;
    }

    public void setBookImage(Bitmap bookImage) {
        this.bookImage = bookImage;
    }


    static public ArrayList<Book> getData(Context context){
        ArrayList<Book> bookList = new ArrayList<>();
        ArrayList<String> bookNameList = new ArrayList<>();
        ArrayList<String> bookAuthorList = new ArrayList<>();
        ArrayList<String> bookSummaryList = new ArrayList<>();
        ArrayList<Bitmap> bookImageList = new ArrayList<>();


        try {
            SQLiteDatabase database = context.openOrCreateDatabase("Kitaplar",Context.MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT * FROM kitaplar",null);


            int bookNameIndex = cursor.getColumnIndex("kitapAdi");
            int bookAuthorIndex = cursor.getColumnIndex("kitapYazari");
            int bookSummaryIndex = cursor.getColumnIndex("kitapOzeti");
            int bookImageIndex = cursor.getColumnIndex("kitapResim");


            while (cursor.moveToNext()){
                bookNameList.add(cursor.getString(bookNameIndex));
                bookAuthorList.add(cursor.getString(bookAuthorIndex));
                bookSummaryList.add(cursor.getString(bookSummaryIndex));

                byte[] gelenResimByte = cursor.getBlob(bookImageIndex);
                Bitmap gelenResim = BitmapFactory.decodeByteArray(gelenResimByte,0,gelenResimByte.length);


                bookImageList.add(gelenResim);
            }

            cursor.close();

            for (int i=0;i<bookNameList.size();i++){
                Book book = new Book();
                book.setBookName(bookNameList.get(i));
                book.setBookAuthor(bookAuthorList.get(i));
                book.setBookSummary(bookSummaryList.get(i));
                book.setBookImage(bookImageList.get(i));

                bookList.add(book);

            }

        }


        catch (Exception e){
            e.printStackTrace();

        }

        return bookList;


    }
}
