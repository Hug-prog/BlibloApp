package com.example.biblo_hf.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.biblo_hf.Db.SQLiteManager;
import com.example.biblo_hf.R;
import com.example.biblo_hf.Repository.UserRepository;
import com.example.biblo_hf.Util.AdapterData;
import com.example.biblo_hf.databinding.FragmentFirstBinding;

import com.example.biblo_hf.Model.Profile;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    public UserRepository userRepository;

    public ArrayList<Profile> userList;
    public ArrayAdapter<Profile> arrayAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // init repo
        userRepository = new UserRepository(SQLiteManager.instanceOfDatabase(getContext()));

        //get all users
        getAllUser();
        addUserInListView();

        binding.userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Profile selectProfile = (Profile) binding.userList.getItemAtPosition(position);

                // select BibloList activity
                Intent intent = new Intent(view.getContext(), BibloList.class);
                intent.putExtra("id",String.valueOf(selectProfile.getId()));
                view.getContext().startActivity(intent);
            }
        });

        binding.btnAddProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

    }

    // get all user
    public void getAllUser(){
        userList = userRepository.getUsers();
    }

    // add user in ListView
    public void addUserInListView(){
        AdapterData adapterData = new AdapterData(getContext(),R.layout.adapter_user_layout,userList);
        binding.userList.setAdapter(adapterData);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}