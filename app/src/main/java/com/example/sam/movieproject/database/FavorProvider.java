package com.example.sam.movieproject.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by sam on 10/18/17.
 */

public class FavorProvider extends ContentProvider {

    private SQLiteOpenHelper mSqliteOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int MOVIE = 0;
    private static final int MOVIE_WITH_ID = 200;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavorContract.AUTHORITY;

        matcher.addURI(authority, FavorContract.MovieEntry.TABLE_FAVOR, MOVIE);
        matcher.addURI(authority, FavorContract.MovieEntry.TABLE_FAVOR + "/#", MOVIE_WITH_ID);

        return matcher;

    }


    @Override
    public boolean onCreate(){
        mSqliteOpenHelper = new FavoriteMovieHelper(getContext());
        return true;
    }
    @Override
    synchronized public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                                     String[] selectionArgs, String sortOrder){
        final SQLiteDatabase db = mSqliteOpenHelper.getWritableDatabase();
        Cursor mCursor;

        switch (sUriMatcher.match(uri)){
            case MOVIE:{
                mCursor = db.query(FavorContract.MovieEntry.TABLE_FAVOR,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return mCursor;
            }

            default:
                throw new IllegalArgumentException("uri not recognized!");

        }

    }
@Override
    public String getType(Uri uri) {
    final int match = sUriMatcher.match(uri);

    switch (match){
        case MOVIE:{
            return FavorContract.MovieEntry.CONTENT_DIR_TYPE;
        }
        case MOVIE_WITH_ID:{
            return FavorContract.MovieEntry.CONTENT_ITEM_TYPE;
        }
        default:{
            throw new IllegalArgumentException("unknown URI man!");
        }
    }
}

@Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
return null;
}

@Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs){
    return -1;
}
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return -1;
    }
}
