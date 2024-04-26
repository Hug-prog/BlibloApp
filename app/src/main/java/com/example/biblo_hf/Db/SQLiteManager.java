package com.example.biblo_hf.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "Biblo";
    private static final int DATABASE_VERSION = 1;


    // Foreign key
    public static final String ID_USER = "user_id";
    public static final String ID_AUTHOR = "author_id";


    //table user
    public static final String TABLE_USER = "users";
    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String Emoji = "emojiCode";

    //table author
    public static final String TABLE_AUTHOR = "authors";
    public static final String AUTHOR_ID = "id";
    public static final String AUTHOR_NAME = "name";


    //table book
    public static final String TABLE_BOOK = "books";
    public static final String BOOK_ID = "id";
    public static final String BOOK_NAME = "name";
    public static final String NB_PAGES = "nb_pages";
    public static final String DESC = "desc";

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context){
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);
        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql,sql2,sql3;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_USER)
                .append("(")
                .append(USER_ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(USER_NAME)
                .append(" TEXT,")
                .append(Emoji)
                .append(" TEXT )");

        sqLiteDatabase.execSQL(sql.toString());

        sql2 = new StringBuilder()
                .append("CREATE TABLE "
                        +TABLE_AUTHOR+
                        "("+AUTHOR_ID+
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        AUTHOR_NAME+
                        " TEXT,"+
                        ID_USER+
                        " INTEGER, FOREIGN KEY ("+
                        ID_USER+
                        ") REFERENCES "+
                        TABLE_USER+
                        "("+
                        USER_ID+
                        ") ON DELETE CASCADE ) "
                );
        sqLiteDatabase.execSQL(sql2.toString());

        sql3 = new StringBuilder()
                .append("CREATE TABLE " +TABLE_BOOK+ "("+BOOK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+BOOK_NAME+" TEXT, "+NB_PAGES+" INTEGER, "+DESC+" TEXT, "+ID_USER+" INTEGER,"+ID_AUTHOR+" INTEGER , FOREIGN KEY ("+ID_USER+") REFERENCES "+TABLE_USER+"("+USER_ID+") ON DELETE CASCADE, FOREIGN KEY ("+ID_AUTHOR+") REFERENCES "+TABLE_AUTHOR+"("+AUTHOR_ID+") ON DELETE CASCADE )"
                );
        Log.i("sql",sql3.toString());
        sqLiteDatabase.execSQL(sql3.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

}
