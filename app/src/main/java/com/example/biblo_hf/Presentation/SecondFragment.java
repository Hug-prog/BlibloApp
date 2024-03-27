package com.example.biblo_hf.Presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.biblo_hf.Db.SQLiteManager;
import com.example.biblo_hf.R;
import com.example.biblo_hf.Repository.UserRepository;
import com.example.biblo_hf.databinding.FragmentSecondBinding;

import com.example.biblo_hf.Model.Profile;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    public UserRepository userRepository;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set db
        userRepository = new UserRepository(SQLiteManager.instanceOfDatabase(getContext()));


        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(binding.plainTextInput.getText().toString());
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    public void createUser(String name){
        Profile user = new Profile();
        user.setName(name);
        userRepository.createUser(user);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}