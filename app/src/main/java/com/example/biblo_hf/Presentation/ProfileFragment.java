package com.example.biblo_hf.Presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.biblo_hf.Db.SQLiteManager;
import com.example.biblo_hf.Model.Author;
import com.example.biblo_hf.Model.Book;
import com.example.biblo_hf.Repository.AuthorRepository;
import com.example.biblo_hf.Repository.BookRepository;
import com.example.biblo_hf.Repository.UserRepository;
import com.example.biblo_hf.databinding.FragmentProfileBinding;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    public AuthorRepository authorRepository;
    public BookRepository bookRepository;
    public UserRepository userRepository;

    public ArrayList<Author> authorArrayList;
    public ArrayList<Book> booksArrayList = new ArrayList<Book>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BibloList activity = (BibloList) getActivity();

        // init repo
        authorRepository = new AuthorRepository(SQLiteManager.instanceOfDatabase(getContext()));
        bookRepository = new BookRepository(SQLiteManager.instanceOfDatabase(getContext()));
        userRepository = new UserRepository(SQLiteManager.instanceOfDatabase(getContext()));


        //set information
        binding.titleUserName.setText(activity.user.getName());
        binding.profileEmoji.setText(activity.user.getEmojiCode());

        binding.btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAuthorAndBooksId(activity.user.getId());
                deleteUser(activity.user.getId());

                // return to userList
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    public void getAuthors(int userId){
        authorArrayList = authorRepository.getAuthors(userId);
    }

    public void getbooksbyAuthor(int authorId, int userId){
        booksArrayList.addAll(bookRepository.getBooksByAuthorId(authorId,userId));
    }

    public void deleteAuthorAndBooksId(int userId){
        getAuthors(userId);

        for(int i=0;i<authorArrayList.size();i++){
            getbooksbyAuthor(authorArrayList.get(i).getId(),userId);
        }

        for(int i=0;i<booksArrayList.size();i++){
            bookRepository.deleteBook(booksArrayList.get(i).getId());
        }

        for(int i=0;i<authorArrayList.size();i++){
            authorRepository.deleteAuthor(authorArrayList.get(i).getId());
        }

    }

    public void deleteUser(int userId){
        userRepository.deleteUser(userId);
    }


}