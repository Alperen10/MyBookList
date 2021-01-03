package com.alperen.mybooklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    private ArrayList<Book> bookList;
    private Context context;
    private OnItemClickListener listener;

    public BookAdapter(ArrayList<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.book_item,parent,false);

        return new BookHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Book book = bookList.get(position);
        holder.setData(book);


    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookHolder extends RecyclerView.ViewHolder{
        TextView txtBookName,txtBookAuthor,txtBookSummary;
        ImageView imgBookImage;
        public BookHolder(@NonNull View itemView) {
            super(itemView);

            txtBookName = (TextView)itemView.findViewById(R.id.book_item_textViewBookName);
            txtBookAuthor = (TextView)itemView.findViewById(R.id.book_item_textViewBookAuthor);
            txtBookSummary = (TextView)itemView.findViewById(R.id.book_item_textViewBookSummary);
            imgBookImage = (ImageView)itemView.findViewById(R.id.book_item_imageViewBookImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(bookList.get(position));
                    }
                }
            });

        }

        public void setData(Book book){
            this.txtBookName.setText(book.getBookName());
            this.txtBookAuthor.setText(book.getBookAuthor());
            this.txtBookSummary.setText(book.getBookSummary());
            this.imgBookImage.setImageBitmap(book.getBookImage());

        }
    }

    public interface OnItemClickListener{
        void onItemClick(Book book);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;

    }

}
