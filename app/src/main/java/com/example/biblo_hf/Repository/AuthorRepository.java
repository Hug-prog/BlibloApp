package com.example.biblo_hf.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.biblo_hf.Model.Author;
import com.example.biblo_hf.Db.SQLiteManager;
import com.example.biblo_hf.Model.Book;
import com.example.biblo_hf.Model.Profile;

import java.util.ArrayList;

public class AuthorRepository {

    private SQLiteManager sqLiteManager;
    public static final String TABLE_AUTHOR = "authors";
    public static final String TABLE_BOOK = "books";
    public static final String AUTHOR_ID = "id";
    public static final String BOOK_ID = "id";
    public static final String AUTHOR_NAME = "name";
    public static final String ID_USER = "user_id";
    public static final String ID_AUTHOR = "author_id";


    public AuthorRepository(SQLiteManager sqLiteManager){
        this.sqLiteManager = sqLiteManager;
    }

    public void createAuthor(Author author, Profile profile){
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AUTHOR_NAME, author.getName());
        contentValues.put(ID_USER,profile.getId());

        sqLiteDatabase.insert(TABLE_AUTHOR,null,contentValues);
        Log.i("insert","authors created");
    }
/*
    public void updateAuthor(Author author, int id){
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(AUTHOR_NAME, author.getName());

        sqLiteDatabase.update(TABLE_AUTHOR,null, AUTHOR_ID+" =? ",new String[]{String.valueOf(id)});
    }

 */

    public void deleteAuthor(int authorsId){
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_AUTHOR,"id=?",new String[]{String.valueOf(authorsId)});
    }


    public ArrayList<Author> getAuthors(int id){
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery(" SELECT * FROM "+TABLE_AUTHOR+" WHERE "+ ID_USER + " = "+id,null);
        ArrayList<Author> arrayList = new ArrayList<>();

        while (result.moveToNext()){
            Author author = new Author();
            author.setId(result.getInt(0));
            author.setName(result.getString(1));

            arrayList.add(author);
        }

        return arrayList;
    }

    public Author getAuthor(int id){
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery(" SELECT * FROM "+TABLE_AUTHOR+" WHERE "+ AUTHOR_ID + " = "+id,null);
        Author author = new Author();
        while (result.moveToNext()) {
            author.setId(result.getInt(0));
            author.setName(result.getString(1));
        }
        return author;
    }

    public Author getAuthorByBookId(int bookId){
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery(" SELECT * FROM "+TABLE_AUTHOR+" INNER JOIN "+TABLE_BOOK+" ON "+TABLE_BOOK+"."+ID_AUTHOR+" = "+TABLE_AUTHOR+"."+AUTHOR_ID+" WHERE "+TABLE_BOOK+"."+BOOK_ID + " = "+bookId,null);
        Log.i("query author",result.toString());
        Author author = new Author();
        while (result.moveToNext()) {
            author.setId(result.getInt(0));
            author.setName(result.getString(1));
        }
        return author;
    }



}
