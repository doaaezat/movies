package com.example.dododo.finalproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dododo.finalproject.data.Contract.mostpopEntry;
import com.example.dododo.finalproject.data.Contract.highrateEntry;

/**
 * Created by DoDo DO on 15/09/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    static final String DATABASE_NAME = "Movies.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_mostpop_TABLE = "CREATE TABLE " + mostpopEntry.TABLE_NAME + " (" +

                mostpopEntry.ColumnID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                mostpopEntry.ColumnMovieID + " INTEGER ," +
                mostpopEntry.COLUMN_titel + " TEXT NOT NULL, " +
                mostpopEntry.COLUMN_overview + " TEXT NOT NULL, " +
                mostpopEntry.COLUMN_rated + " REAL NOT NULL, " +
                mostpopEntry.COLUMN_releasdate + " REAL NOT NULL, " +
                mostpopEntry.COLUMN_image + " TEXT NOT NULL " + " )";
        sqLiteDatabase.execSQL(SQL_CREATE_mostpop_TABLE);

        final String SQL_CREATE_highrate_TABLE = "CREATE TABLE " + highrateEntry.TABLE_NAME + " (" +

                highrateEntry.ColumnID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                highrateEntry.ColumnMovieID + " INTEGER ," +
                highrateEntry.COLUMN_titel + " TEXT NOT NULL, " +
                highrateEntry.COLUMN_overview + " TEXT NOT NULL, " +
                highrateEntry.COLUMN_rated + " REAL NOT NULL, " +
                highrateEntry.COLUMN_releasdate + " REAL NOT NULL, " +
                highrateEntry.COLUMN_image + " TEXT NOT NULL" + ")";
        sqLiteDatabase.execSQL(SQL_CREATE_highrate_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + mostpopEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + highrateEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
