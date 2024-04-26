package com.example.biblo_hf.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.biblo_hf.Model.Profile;
import com.example.biblo_hf.Db.SQLiteManager;

import java.util.ArrayList;

public class UserRepository {

    private SQLiteManager sqLiteManager;

    public static final String TABLE_NAME = "users";
    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String Emoji = "emojiCode";

    public UserRepository(SQLiteManager sqLiteManager) {
        this.sqLiteManager = sqLiteManager;
    }

    public void createUser(Profile user){
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, user.getName());
        contentValues.put(Emoji,user.getEmojiCode());

        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        Log.i("insert","user created");
    }

    public void updateUser(Profile user,int id){
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, user.getName());

        sqLiteDatabase.update(TABLE_NAME,null, USER_ID+" =? ",new String[]{String.valueOf(id)});
    }

    public void deleteUser(int userId){
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(userId)});
    }

    public ArrayList<Profile> getUsers(){
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery(" SELECT * FROM "+TABLE_NAME,null);
        ArrayList<Profile> arrayList = new ArrayList<>();

        while (result.moveToNext()){
            Profile profile = new Profile();
            profile.setId(result.getInt(0));
            profile.setName(result.getString(1));
            profile.setEmojiCode(result.getString(2));

            arrayList.add(profile);
        }

        return arrayList;
    }

    public Profile getUser(int id){
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery(" SELECT * FROM "+TABLE_NAME+" WHERE "+ USER_ID + " = "+id,null);
        Profile profile = new Profile();
        while (result.moveToNext()) {
            profile.setId(result.getInt(0));
            profile.setName(result.getString(1));
            profile.setEmojiCode(result.getString(2));
        }
        return profile;
    }

}
