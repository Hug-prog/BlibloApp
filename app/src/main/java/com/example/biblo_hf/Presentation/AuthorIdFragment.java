package com.example.biblo_hf.Presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biblo_hf.Db.SQLiteManager;
import com.example.biblo_hf.Model.Author;
import com.example.biblo_hf.Model.Book;
import com.example.biblo_hf.R;
import com.example.biblo_hf.Repository.AuthorRepository;
import com.example.biblo_hf.Repository.BookRepository;
import com.example.biblo_hf.Util.BookAdapterData;
import com.example.biblo_hf.databinding.FragmentAuthorIdBinding;
import com.example.biblo_hf.databinding.FragmentBookIdBinding;

import java.util.ArrayList;

public class AuthorIdFragment extends Fragment {

    private FragmentAuthorIdBinding binding;
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    private Author author;

    private ArrayList<Book> bookArrayList;

    private int authorId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthorIdBinding.inflate(inflater, container, false);

        if(getArguments() != null){
            authorId = getArguments().getInt("id");
        }

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        BibloList activity = (BibloList) getActivity();

        // initial db
        authorRepository = new AuthorRepository(SQLiteManager.instanceOfDatabase(getContext()));
        bookRepository = new BookRepository(SQLiteManager.instanceOfDatabase(getContext()));

        getAuthor(authorId);

        binding.btnComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comeBack(activity);
            }
        });

        // set title page
        binding.authorTitle.setText(author.getName());

        //delete book
        binding.btnDeleteAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAuthor(author.getId());
                comeBack(activity);
            }
        });

        bookArrayList =  getAllBookByAuthorId(authorId,activity.user.getId());

        BookAdapterData bookAdapterData = new BookAdapterData(getContext(),R.layout.adapter_book_layout,bookArrayList);
        binding.bookAuthorList.setAdapter(bookAdapterData);

    }

    public void comeBack(BibloList activity){
        activity.replaceFragment(new AuthorFragment());
    }

    public void getAuthor(int authorId){
        author = authorRepository.getAuthor(authorId);
    }

    public ArrayList<Book> getAllBookByAuthorId(int authorId, int userId){
         return  bookRepository.getBooksByAuthorId(authorId,userId);
    }

    public void deleteAuthor(int authorId){

        for(int i=0;i<bookArrayList.size();i++){
          bookRepository.deleteBook(bookArrayList.get(i).getId());
        }
        authorRepository.deleteAuthor(authorId);

    }
}