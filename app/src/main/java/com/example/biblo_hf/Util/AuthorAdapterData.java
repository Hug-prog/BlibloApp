package com.example.biblo_hf.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.biblo_hf.Model.Author;
import com.example.biblo_hf.Model.Profile;
import com.example.biblo_hf.R;

import java.util.ArrayList;

public class AuthorAdapterData extends ArrayAdapter<Author> {

    public AuthorAdapterData(@NonNull Context context, int resource, ArrayList<Author> authors) {
        super(context, resource,authors);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Author author = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_user_layout,parent,false);


        TextView textView = convertView.findViewById(R.id.customText);

        textView.setText(author.getName());

        return  convertView;
    }

}
