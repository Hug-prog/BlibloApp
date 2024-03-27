package com.example.biblo_hf.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.biblo_hf.Model.Book;
import com.example.biblo_hf.R;

import java.util.ArrayList;

public class BookAdapterData extends ArrayAdapter<Book> {

    public BookAdapterData(@NonNull Context context, int resource, ArrayList<Book> books) {
            super(context, resource,books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            Book book = getItem(position);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_book_layout,parent,false);


            TextView textView = convertView.findViewById(R.id.book_custom_text);

            textView.setText(book.getName());

            return  convertView;
    }

}
