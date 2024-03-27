package com.example.biblo_hf.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.biblo_hf.Model.Profile;
import com.example.biblo_hf.R;

import java.util.ArrayList;

public class AdapterData extends ArrayAdapter<Profile> {

    public AdapterData(@NonNull Context context, int resource,ArrayList<Profile> profiles) {
        super(context, resource,profiles);
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        Profile profile = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_user_layout,parent,false);


        TextView textView = convertView.findViewById(R.id.customText);

        textView.setText(profile.getName());

        return  convertView;
    }

}
