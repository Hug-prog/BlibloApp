package com.example.biblo_hf.Presentation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.biblo_hf.Db.SQLiteManager;
import com.example.biblo_hf.Model.Author;
import com.example.biblo_hf.Model.Book;
import com.example.biblo_hf.Model.Profile;
import com.example.biblo_hf.R;
import com.example.biblo_hf.Repository.AuthorRepository;
import com.example.biblo_hf.Repository.BookRepository;
import com.example.biblo_hf.Util.AdapterData;
import com.example.biblo_hf.Util.AuthorAdapterData;
import com.example.biblo_hf.Util.BookAdapterData;
import com.example.biblo_hf.Util.SpinnerAuthorAdapterData;
import com.example.biblo_hf.databinding.FragmentHomeBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public  BookRepository bookRepository;

    public AuthorRepository authorRepository;

    public ArrayList<Author> authorArrayList;
    public ArrayList<Book> bookArrayList;

    public Book book = new Book();

    public Author author = new Author();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BibloList activity = (BibloList) getActivity();

        TextView textView =  binding.titleBibloList;
        textView.setText("Hello "+String.valueOf(activity.user.getName())+" !!");
        textView.setTextSize(25);

        // initial db
        bookRepository = new BookRepository(SQLiteManager.instanceOfDatabase(getContext()));
        authorRepository = new AuthorRepository(SQLiteManager.instanceOfDatabase(getContext()));

        // get all authors
        getAllAuthors(activity.user.getId());

        // get all books
        getAllBooks(activity.user.getId());

        binding.btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook();
            }
        });

    }

    public void getAllAuthors(int id){
        authorArrayList =  authorRepository.getAuthors(id);
    }

    public void getAllBooks(int id){
        bookArrayList =  bookRepository.getBooks(id);
        addBooksInListView();
    }

    public void addBooksInListView(){
        BookAdapterData bookAdapterData = new BookAdapterData(getContext(),R.layout.adapter_book_layout,bookArrayList);
        binding.bookList.setAdapter(bookAdapterData);
    }


    public void addAuthorInSpinner(Spinner spinner){
        SpinnerAuthorAdapterData adapterData = new SpinnerAuthorAdapterData(getContext(),R.layout.spinner_adapter_layout,authorArrayList);
        adapterData.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapterData);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                author = (Author) authorArrayList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addBook(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_book,null);
        bottomSheetDialog.setContentView(view1);
        bottomSheetDialog.show();

        EditText editText =view1.findViewById(R.id.input_add_book);
        Button button = view1.findViewById(R.id.btn_add_b);
        Spinner spinner = view1.findViewById(R.id.select_list_authors);

        addAuthorInSpinner(spinner);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BibloList activity = (BibloList) getActivity();

                book.setName(editText.getText().toString());

                createBook();

                getAllBooks(activity.user.getId());

                bottomSheetDialog.dismiss();
            }
        });
    }


    public void createBook(){
        BibloList activity = (BibloList) getActivity();
        bookRepository.createBook(book,activity.user,author);
    }



}