package com.example.biblo_hf.Presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.biblo_hf.Db.SQLiteManager;
import com.example.biblo_hf.Model.Author;
import com.example.biblo_hf.Model.Book;
import com.example.biblo_hf.R;
import com.example.biblo_hf.Repository.AuthorRepository;
import com.example.biblo_hf.Repository.BookRepository;
import com.example.biblo_hf.databinding.FragmentBookIdBinding;
import com.example.biblo_hf.databinding.FragmentHomeBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BookIdFragment extends Fragment {

    private FragmentBookIdBinding binding;
    private   BookRepository bookRepository;

    private AuthorRepository authorRepository;

    private Book book;

    private Author author;

    private int bookId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookIdBinding.inflate(inflater, container, false);

        if(getArguments() != null){
            bookId = getArguments().getInt("id");
        }

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        BibloList activity = (BibloList) getActivity();

        // initial db
        bookRepository = new BookRepository(SQLiteManager.instanceOfDatabase(getContext()));
        authorRepository = new AuthorRepository(SQLiteManager.instanceOfDatabase(getContext()));

        getBook(bookId);

        binding.btnComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comeBack(activity);
            }
        });

        setInformation();

        //delete book
        binding.btnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBook(book.getId());
                comeBack(activity);
            }
        });

        getAuthorByBookId();
        binding.authorId.setText(author.getName());

        // btn update book
        binding.btnUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSheetDialog();
            }
        });

    }

    public void showSheetDialog(){

        // create a bottom Dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_book,null);
        bottomSheetDialog.setContentView(view1);
        bottomSheetDialog.show();

        EditText editText =view1.findViewById(R.id.input_add_book);
        editText.setVisibility(View.INVISIBLE);

        EditText editTextNbPage =view1.findViewById(R.id.input_add_pages);

        EditText editTextDesc =view1.findViewById(R.id.input_desc);

        Button button = view1.findViewById(R.id.btn_add_b);
        button.setText("modifier");

        Spinner spinner = view1.findViewById(R.id.select_list_authors);
        spinner.setVisibility(View.INVISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                book.setDesc(editTextDesc.getText().toString());
                book.setNbPages(Integer.parseInt(editTextNbPage.getText().toString()));

                setInformation();
                updateBook();

                bottomSheetDialog.dismiss();
            }
        });

    }

    // set title,desc,nbpage in Vue
    public void setInformation(){
        binding.bookTitle.setText(book.getName());
        binding.desc.setText(book.getDesc());
        binding.nbPages.setText(String.valueOf(book.getNbPages()));
    }

    public void updateBook(){
        bookRepository.updateBook(book);
    }

    public void comeBack(BibloList activity){
        activity.replaceFragment(new HomeFragment());
    }

    public void getBook(int bookId){
        book = bookRepository.getBook(bookId);
    }

    public void deleteBook(int bookId){
        bookRepository.deleteBook(bookId);
    }

    public void getAuthorByBookId(){
       author =  authorRepository.getAuthorByBookId(book.getId());
    }
}