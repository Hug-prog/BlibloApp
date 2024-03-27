package com.example.biblo_hf.Presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.biblo_hf.R;
import com.example.biblo_hf.Repository.UserRepository;

import com.example.biblo_hf.Model.Profile;
import com.example.biblo_hf.databinding.ActivityBibloListBinding;
import com.example.biblo_hf.Db.SQLiteManager;

public class BibloList extends AppCompatActivity {

    public Profile user;

    private ActivityBibloListBinding binding;

    public UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBibloListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.toString()){
                case "home":
                    replaceFragment(new HomeFragment());
                    break;
                case "author":
                    replaceFragment(new AuthorFragment());
                    break;
                case "profile":
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });

        userRepository = new UserRepository(SQLiteManager.instanceOfDatabase(this));


        // get current user
        getUser();
        //Log.i("user",user.getName());
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    protected void getUser(){
        // get info player
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String value = extras.getString("id");
            if(value != null){
                int userId = Integer.parseInt(value);
                user = userRepository.getUser(userId);
            }
        }
    }
}
