package com.paul.paulk.testdatabase.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.compat.BuildConfig;

import com.paul.paulk.testdatabase.provider.contracts.BaseContract;
import com.paul.paulk.testdatabase.provider.contracts.EmployeesContract;
import com.paul.paulk.testdatabase.provider.contracts.SalariesContract;
import com.paul.paulk.testdatabase.provider.contracts.TitlesContract;

import static com.paul.paulk.testdatabase.provider.contracts.BaseContract.MATCHER_ID.EMPLOYEES;
import static com.paul.paulk.testdatabase.provider.contracts.BaseContract.MATCHER_ID.SALARIES;
import static com.paul.paulk.testdatabase.provider.contracts.BaseContract.MATCHER_ID.TITLES;

/**
 * Created by paulk on 12/13/2016.
 */

public class EmployeesProvider extends ContentProvider {

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final Uri BASE_URL = Uri.parse (ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY);

    private static final UriMatcher uriMatcher;

    private DatabaseHelper dbHelper;
    private static final int DBVERSION = 1;

    @Override
    public boolean onCreate () {
        dbHelper = new DatabaseHelper (getContext (), DBVERSION);
        return null != dbHelper.getWritableDatabase ();
    }

    @Override
    public void shutdown () {
        dbHelper.close ();
        super.shutdown ();
    }

    @Nullable
    @Override
    public Cursor query (final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        final Boolean download = uri.getBooleanQueryParameter (BaseContract.FORCE_DOWNLOAD, false);
        final Boolean reset = uri.getBooleanQueryParameter (BaseContract.RESET, false);

        switch (uriMatcher.match (uri)) {
            case EMPLOYEES:
                return queryEmployees (download, reset, projection, sortOrder);
            case SALARIES:
                return querySalaries (download, reset, projection, sortOrder);
            case TITLES:
                return queryTitles (download, reset, projection, sortOrder);
            default:
                return null;
        }
    }

    private Cursor queryEmployees (final boolean download, final boolean wipe, final String[] projection, final String sortOrder) {
        if (download)
            DownloadEmployeesTask.Employees (getContext (), dbHelper, wipe);

        return dbHelper.getReadableDatabase ().query (EmployeesContract.TABLE_NAME, projection, null, null, null, null, sortOrder);
    }

    private Cursor querySalaries (final boolean download, final boolean wipe, final String[] projection, final String sortOrder) {
        if (download)
            DownloadEmployeesTask.Salaries (getContext (), dbHelper, wipe);

        return dbHelper.getReadableDatabase ().query (SalariesContract.TABLE_NAME, projection, null, null, null, null, sortOrder);
    }

    private Cursor queryTitles (final boolean download, final boolean wipe, final String[] projection, final String sortOrder) {
        if (download)
            DownloadEmployeesTask.Titles (getContext (), dbHelper, wipe);

        return dbHelper.getReadableDatabase ().query (TitlesContract.TABLE_NAME, projection, null, null, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType (Uri uri) {
        switch (uriMatcher.match (uri)) {
            case EMPLOYEES:
            case SALARIES:
            case TITLES:
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert (Uri uri, ContentValues contentValues) {
        // Don't want insert via provider
        return null;
    }

    @Override
    public int delete (Uri uri, String s, String[] strings) {
        // Don't want delete via provider
        return 0;
    }

    @Override
    public int update (Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    static long insertOrUpdate (final SQLiteDatabase db, final String table, final String idxColumn, final ContentValues values,
                                final String selection, final String[] selectionArgs) {
        long row = getRow (db, table, idxColumn, selection, selectionArgs);

        final int updated = db.update (table, values, selection, selectionArgs);

        if (updated == 0) {
            row = db.insert (table, null, values);
        }

        return row;
    }

    static long getRow (final SQLiteDatabase db, final String table, final String idxColumn, final String selection, final String[] selectionArgs) {
        Cursor cursor = null;

        try {
            cursor = db.query (table, new String[]{idxColumn}, selection, selectionArgs, null, null, null);

            if (null == cursor) return 0L;

            if (!cursor.moveToFirst ()) return 0L;

            return cursor.getLong (0);
        } finally {
            cursor.close ();
        }
    }

    static {
        uriMatcher = new UriMatcher (UriMatcher.NO_MATCH);

        // content://android.support.compat/employees
        uriMatcher.addURI (AUTHORITY, BaseContract.PATH.EMPLOYEE, EMPLOYEES);
        // content://android.support.compat/employees/detail
        // content://android.support.compat/employees/wipe
        // content://android.support.compat/salaries
        uriMatcher.addURI (AUTHORITY, BaseContract.PATH.SALARY, SALARIES);
        // content://android.support.compat/titles
        uriMatcher.addURI (AUTHORITY, BaseContract.PATH.TITLE, TITLES);
    }
}
