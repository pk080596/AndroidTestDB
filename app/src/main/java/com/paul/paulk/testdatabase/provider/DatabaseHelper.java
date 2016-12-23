package com.paul.paulk.testdatabase.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.paul.paulk.testdatabase.provider.contracts.EmployeesContract;
import com.paul.paulk.testdatabase.provider.contracts.EmployeesDetailContract;
import com.paul.paulk.testdatabase.provider.contracts.SalariesContract;
import com.paul.paulk.testdatabase.provider.contracts.TitlesContract;

/**
 * Created by paulk on 12/13/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String SCHEMA = "sqlite_master";
    public static final String DATABASE_NAME = "employees.sqlite";

    public DatabaseHelper (final Context context, final int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase) {

        // Need this to use foreign keys on sqlite3
        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON");

        EmployeesContract.onCreate (sqLiteDatabase);
        SalariesContract.onCreate (sqLiteDatabase);
        TitlesContract.onCreate (sqLiteDatabase);
        EmployeesDetailContract.onCreate (sqLiteDatabase);
    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen (SQLiteDatabase sqLiteDatabase) {
        // Need this to use foreign keys on sqlite3
        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON");
    }
}
