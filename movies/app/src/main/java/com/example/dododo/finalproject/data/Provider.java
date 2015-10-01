package com.example.dododo.finalproject.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by DoDo DO on 17/09/2015.
 */
public class Provider extends ContentProvider {
    private static  UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.highrateEntry.TABLE_NAME + "/#", highrate_with_image);
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.mostpopEntry.TABLE_NAME + "/#", mostpop_with_image);
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.highrateEntry.TABLE_NAME, highrate);
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.mostpopEntry.TABLE_NAME, mostpop);
        return sUriMatcher;
    }

    private DbHelper mOpenHelper;
    SQLiteDatabase sqLiteDatabase;
    static final int highrate = 100;
    static final int mostpop = 101;
    static final int mostpop_with_image = 102;
    static final int highrate_with_image = 103;


    @Override
    public boolean onCreate() {
        mOpenHelper = new DbHelper(getContext());
        sqLiteDatabase = mOpenHelper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String s1) {
        Cursor cursor = null;

        switch (sUriMatcher.match(uri)) {
            case highrate_with_image:
                int id = Integer.parseInt(uri.getLastPathSegment());
                cursor = sqLiteDatabase.query(Contract.highrateEntry.TABLE_NAME, projection, Contract.highrateEntry.ColumnID,
                        new String[]{String.valueOf(id)}, null, null, s1);
                break;
            case mostpop_with_image:
                int id1 = Integer.parseInt(uri.getLastPathSegment());
                cursor = sqLiteDatabase.query(Contract.mostpopEntry.TABLE_NAME, new String[]{Contract.mostpopEntry.COLUMN_titel,
                                Contract.mostpopEntry.ColumnMovieID },selection,
                        new String[]{String.valueOf(id1)}, null, null, s1);
                break;
            case highrate:
                //int id2 = Integer.parseInt(uri.getLastPathSegment());
                cursor = sqLiteDatabase.query(Contract.highrateEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, s1);
                break;
            case mostpop:
               // int id3 = Integer.parseInt(uri.getLastPathSegment());
                cursor = sqLiteDatabase.query(Contract.mostpopEntry.TABLE_NAME, projection,selection,
                        selectionArgs, null, null, s1);
                break;

        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {

            case highrate_with_image:
                return Contract.highrateEntry.CONTENT_ITEM_TYPE;
            case mostpop_with_image:
                return Contract.mostpopEntry.CONTENT_ITEM_TYPE;
            case highrate:
                return Contract.highrateEntry.CONTENT_TYPE;
            case mostpop:
                return Contract.mostpopEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case highrate: {

                long _id = db.insert(Contract.highrateEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = Contract.highrateEntry.buildhighrateUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case mostpop: {
                long _id = db.insert(Contract.mostpopEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = Contract.mostpopEntry.buildmostpopUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case highrate:
                rowsDeleted = db.delete(
                        Contract.highrateEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case mostpop:
                rowsDeleted = db.delete(
                        Contract.mostpopEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {


        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case highrate:

                rowsUpdated = db.update(Contract.highrateEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case mostpop:
                rowsUpdated = db.update(Contract.mostpopEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
