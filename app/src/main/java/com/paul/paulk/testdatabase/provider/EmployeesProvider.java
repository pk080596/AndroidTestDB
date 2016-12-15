package com.paul.paulk.testdatabase.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.compat.BuildConfig;

/**
 * Created by paulk on 12/13/2016.
 */

public class EmployeesProvider extends ContentProvider {

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final Uri BASE_URL = Uri.parse (ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY);

    private static final UriMatcher uriMatcher;

    private DatabaseHelper dbHelper;

    @Override
    public boolean onCreate () {
        // TODO: initiallize dbhelper, return true if writable
        return false;
    }

    @Nullable
    @Override
    public Cursor query (Uri uri, String[] strings, String s, String[] strings1, String s1) {
        switch (uriMatcher.match(uri)) {
            // TODO: query based on uri
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public String getType (Uri uri) {
        switch (uriMatcher.match (uri)) {
            // TODO: get type based on uri
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert (Uri uri, ContentValues contentValues) {
        // Don't want insert
        return null;
    }

    @Override
    public int delete (Uri uri, String s, String[] strings) {
        // Don't want delete
        return 0;
    }

    @Override
    public int update (Uri uri, ContentValues contentValues, String s, String[] strings) {
        // TODO: Using custom update
        return 0;
    }

    static {
        uriMatcher = new UriMatcher (UriMatcher.NO_MATCH);
        // TODO: match uris
    }
}
