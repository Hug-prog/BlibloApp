package com.example.biblo_hf.Presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.strictmode.GetTargetFragmentRequestCodeUsageViolation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.biblo_hf.Db.SQLiteManager;
import com.example.biblo_hf.Model.Author;
import com.example.biblo_hf.Model.Book;
import com.example.biblo_hf.R;
import com.example.biblo_hf.Repository.AuthorRepository;
import com.example.biblo_hf.Util.AdapterData;
import com.example.biblo_hf.Util.AuthorAdapterData;
import com.example.biblo_hf.databinding.FragmentAuthorBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;


public class AuthorFragment extends Fragment {

    private FragmentAuthorBinding binding;

    public AuthorRepository authorRepository;

    public ArrayList<Author> authorArrayList;

    public Author author = new Author();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BibloList activity = (BibloList) getActivity();

        // init repo
        authorRepository = new AuthorRepository(SQLiteManager.instanceOfDatabase(getContext()));

        getAllAuthors();

        // if btn create author is selected
        binding.btnAddAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAuthor();

            }
        });

        binding.authorsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // get selected book
                Author selectAuthor = (Author) binding.authorsList.getItemAtPosition(position);

                // set data to AuthorIdFragment
                AuthorIdFragment authorIdFragment = new AuthorIdFragment();
                Bundle args = new Bundle();
                args.putInt("id",selectAuthor.getId());
                authorIdFragment.setArguments(args);

                activity.replaceFragment(authorIdFragment);
            }
        });

    }

    public void addAuthor(){

        // create a bottom Dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_author,null);
        bottomSheetDialog.setContentView(view1);
        bottomSheetDialog.show();

        EditText editText =view1.findViewById(R.id.input_add_author);
        Button button = view1.findViewById(R.id.btn_add);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                author.setName(editText.getText().toString());
                createAuthor();
                getAllAuthors();
                bottomSheetDialog.dismiss();
            }
        });
    }

    public void getAllAuthors(){
        BibloList activity = (BibloList) getActivity();
        authorArrayList =  authorRepository.getAuthors(activity.user.getId());
        addAuthorsInListView();
    }

    public void addAuthorsInListView(){
        AuthorAdapterData authorAdapterData = new AuthorAdapterData(getContext(),R.layout.adapter_user_layout,authorArrayList);
        binding.authorsList.setAdapter(authorAdapterData);
    }

    public void createAuthor(){
        BibloList activity = (BibloList) getActivity();
        authorRepository.createAuthor(author,activity.user);
    }


}