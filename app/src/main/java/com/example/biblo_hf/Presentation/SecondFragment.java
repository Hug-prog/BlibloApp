package com.example.biblo_hf.Presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.biblo_hf.Db.SQLiteManager;
import com.example.biblo_hf.R;
import com.example.biblo_hf.Repository.UserRepository;
import com.example.biblo_hf.Util.SpinnerAuthorAdapterData;
import com.example.biblo_hf.databinding.FragmentSecondBinding;

import com.example.biblo_hf.Model.Profile;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    protected Profile profile = new Profile();

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


        //select emoji
        Spinner spinner = binding.selectEmoji;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.emoticons, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String emojiArr[] = getResources().getStringArray(R.array.emoticons);
                binding.emoji.setText(emojiArr[position]);
                profile.setEmojiCode(emojiArr[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // create user
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // set profile name
                profile.setName(binding.plainTextInput.getText().toString());

                createUser(profile);

                // return to First Fragment
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    public void createUser(Profile profile){
        userRepository.createUser(profile);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}