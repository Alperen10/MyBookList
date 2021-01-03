package com.alperen.mybooklist;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    private ImageView imgBookImage;
    private TextView txtBookName,txtBookAuthor,txtBookSummary;
    private String bookName,bookAuthor,bookSummary;
    private Bitmap bookImage;


    private void init(){
        imgBookImage = (ImageView)findViewById(R.id.details_activity_imageViewBookImage);
        txtBookName = (TextView)findViewById(R.id.details_activity_textViewBookName);
        txtBookAuthor = (TextView)findViewById(R.id.details_activity_textViewBookAuthor);
        txtBookSummary = (TextView)findViewById(R.id.details_activity_textViewBookSummary);

        bookName = MainActivity.bookDetails.getBookName();
        bookAuthor = MainActivity.bookDetails.getBookAuthor();
        bookSummary = MainActivity.bookDetails.getBookSummary();
        bookImage = MainActivity.bookDetails.getBookImage();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();

        if (!TextUtils.isEmpty(bookName) && !TextUtils.isEmpty(bookAuthor) && !TextUtils.isEmpty(bookSummary)){
            txtBookName.setText(bookName);
            txtBookAuthor.setText(bookAuthor);
            txtBookSummary.setText(bookSummary);

            imgBookImage.setImageBitmap(bookImage);

        }


    }
}