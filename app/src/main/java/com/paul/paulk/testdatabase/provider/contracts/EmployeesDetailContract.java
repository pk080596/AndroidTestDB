package com.paul.paulk.testdatabase.provider.contracts;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.Locale;

/**
 * Created by paulk on 12/20/2016.
 */

public class EmployeesDetailContract extends BaseContract {
    public static final String DETAIL = "detail";
    public static final String WIPE = "wipe";

    public static final String TABLE_NAME = EmployeesContract.TABLE_NAME + "_" + DETAIL;

    public static final String VIEW_EMPLOYEES_DETAIL = String.format (Locale.ENGLISH,
            "%s_with_%s", EmployeesContract.TABLE_NAME, DETAIL);

    public static void onCreate(@NonNull SQLiteDatabase db) {
        createEmployeesDetailView (db);
    }

    private static void createEmployeesDetailView(@NonNull SQLiteDatabase db) {
        String sql = String.format (Locale.ENGLISH, "CREATE VIEW %s AS " +
                        "SELECT e.*, s.%s, t.%s " +
                        "FROM %s AS e " +
                        "INNER JOIN %s AS t " +
                        "ON t.%s = e.%s " +
                        "INNER JOIN %s AS s " +
                        "ON s.%s = e.%s",
                        VIEW_EMPLOYEES_DETAIL,
                        SalariesContract.SALARY, TitlesContract.TITLE,
                        EmployeesContract.TABLE_NAME,
                        TitlesContract.TABLE_NAME,
                        TitlesContract.EMPNO, EmployeesContract._ID,
                        SalariesContract.TABLE_NAME,
                        SalariesContract.EMPNO, EmployeesContract._ID
                        );

        db.execSQL (sql);
    }

}
