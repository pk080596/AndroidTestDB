package com.paul.paulk.testdatabase.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.paul.paulk.testdatabase.provider.contracts.EmployeesContract;

/**
 * Created by paulk on 12/13/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "employees.sqlite";

    public DatabaseHelper (final Context context, final int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase) {
        EmployeesContract.onCreate (sqLiteDatabase);
    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
