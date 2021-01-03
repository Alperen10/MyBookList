package com.alperen.mybooklist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddBookActivity extends AppCompatActivity {

    private EditText editTextBookName,editTextBookAuthor,editTextBookSummary;
    private ImageView imgBookImage;
    private String bookName,bookAuthor,bookSummary;
    private int imgPermissionCode = 0,imagePermissionTake = 1;
    private Bitmap selectedImage,editedImage,defaultImage;
    private Button btnSave;

    private void init(){
        editTextBookName = (EditText)findViewById(R.id.add_book_activity_editTextBookName);
        editTextBookSummary = (EditText)findViewById(R.id.add_book_activity_editTextBookSummary);
        editTextBookAuthor = (EditText)findViewById(R.id.add_book_activity_editTextBookAuthor);
        imgBookImage = (ImageView) findViewById(R.id.add_book_activity_imageViewBookImage);
        btnSave = (Button)findViewById(R.id.add_book_activity_btnSave);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        init();

    }

    public void bookSave(View v){
        bookName = editTextBookName.getText().toString();
        bookAuthor = editTextBookAuthor.getText().toString();
        bookSummary = editTextBookSummary.getText().toString();

        if (!TextUtils.isEmpty(bookName)){
            if (!TextUtils.isEmpty(bookAuthor)){
                if (!TextUtils.isEmpty(bookSummary)){
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    editedImage = imageSmall(selectedImage);
                    editedImage.compress(Bitmap.CompressFormat.PNG,75   ,outputStream);
                    byte[] savedImage = outputStream.toByteArray();


                    try {
                        SQLiteDatabase database = this.openOrCreateDatabase("Kitaplar",MODE_PRIVATE,null);
                        database.execSQL("CREATE TABLE IF NOT EXISTS kitaplar (id INTEGER PRIMARY KEY,kitapAdi VARCHAR,kitapYazari VARCHAR,kitapOzeti VARCHAR,kitapResim BLOB)");

                        String sqlQuery = "INSERT INTO kitaplar (kitapAdi,kitapYazari,kitapOzeti,kitapResim) VALUES (?,?,?,?)";
                        SQLiteStatement statement = database.compileStatement(sqlQuery);
                        statement.bindString(1,bookName);
                        statement.bindString(2,bookAuthor);
                        statement.bindString(3,bookSummary);
                        statement.bindBlob(4,savedImage);
                        statement.execute();

                        clearObject();
                        Toast.makeText(getApplicationContext(),"Kayıt başarıyla eklendi",Toast.LENGTH_LONG).show();

                    }
                    catch (Exception e){
                        e.printStackTrace();

                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"Kitap özeti boş olamaz",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"Kitap yazarı boş olamaz",Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(getApplicationContext(),"Kitap ismi boş olamaz",Toast.LENGTH_SHORT).show();
        }

    }

    private Bitmap imageSmall(Bitmap image){
        return Bitmap.createScaledBitmap(image,120,150,true);

    }

    private void clearObject(){
        editTextBookName.setText("");
        editTextBookAuthor.setText("");
        editTextBookSummary.setText("");
        defaultImage = BitmapFactory.decodeResource(this.getResources(),R.drawable.book);
        imgBookImage.setImageBitmap(defaultImage);
        btnSave.setEnabled(false);

    }

    public void selectImage(View v){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},imgPermissionCode);

        }
        else{
            Intent takeImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(takeImage,imagePermissionTake);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == imgPermissionCode){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent takeImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(takeImage,imagePermissionTake);
            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == imagePermissionTake){
            if (resultCode == RESULT_OK && data != null){
                Uri imageUri = data.getData();

                try {
                    if (Build.VERSION.SDK_INT >= 28){
                        ImageDecoder.Source imageSource = ImageDecoder.createSource(this.getContentResolver(),imageUri);
                        selectedImage = ImageDecoder.decodeBitmap(imageSource);
                        imgBookImage.setImageBitmap(selectedImage);

                    }
                    else{
                        selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                        imgBookImage.setImageBitmap(selectedImage);
                    }
                    btnSave.setEnabled(true);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(this,MainActivity.class);
        finish();
        startActivity(backIntent);
    }
}