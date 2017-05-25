package com.teamtreehouse.contentproviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by LuongHH on 5/24/2017.
 */

public class MyContentProvider extends ContentProvider {

    static final Uri CONTENT_URI = Uri.parse("content://com.teamtreehouse.contentproviders.provider/" + DatabaseHelper.TABLE_VICS);
    private Context mContext;
    private SQLiteDatabase mDatabase;

    @Override
    public boolean onCreate() {
        mContext = getContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
        mDatabase = databaseHelper.getWritableDatabase();
        return mDatabase != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DatabaseHelper.TABLE_VICS);
        return builder.query(mDatabase, columns, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowId = mDatabase.insert(DatabaseHelper.TABLE_VICS, null, values);
        if (rowId > -1) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            mContext.getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLiteException("Insert failed for Uri: " +uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
