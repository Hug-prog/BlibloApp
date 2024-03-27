package com.example.biblo_hf.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.biblo_hf.Db.SQLiteManager;
import com.example.biblo_hf.Model.Author;
import com.example.biblo_hf.Model.Book;
import com.example.biblo_hf.Model.Profile;

import java.util.ArrayList;

public class BookRepository {
    private SQLiteManager sqLiteManager;
    public static final String TABLE_BOOK = "books";
    public static final String BOOK_ID = "id";
    public static final String BOOK_NAME = "name";
    public static final String ID_USER = "user_id";
    public static final String ID_AUTHOR = "author_id";

    public BookRepository(SQLiteManager sqLiteManager){
        this.sqLiteManager = sqLiteManager;
    }

    public void createBook(Book book, Profile profile,Author author){
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_NAME, book.getName());
        contentValues.put(ID_USER,profile.getId());
        contentValues.put(ID_AUTHOR,author.getId());

        sqLiteDatabase.insert(TABLE_BOOK,null,contentValues);
        Log.i("insert","authors created");
    }

    public ArrayList<Book> getBooks(int userId){
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery(" SELECT * FROM "+TABLE_BOOK+" WHERE "+ ID_USER + " = "+userId,null);
        ArrayList<Book> arrayList = new ArrayList<>();

        while (result.moveToNext()){
            Book book = new Book();
            book.setId(result.getInt(0));
            book.setName(result.getString(1));

            arrayList.add(book);
        }

        return arrayList;
    }

    public Book getBook(int bookId){
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery(" SELECT * FROM "+TABLE_BOOK+" WHERE "+ BOOK_ID + " = "+bookId,null);
        Book book = new Book();
        while (result.moveToNext()) {
            book.setId(result.getInt(0));
            book.setName(result.getString(1));
        }
        return book;
    }


}
