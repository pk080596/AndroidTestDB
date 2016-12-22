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
    public static final String RECENT = "recent";

    public static final String VIEW_RECENT_SALARY = RECENT + "_" + SalariesContract.TABLE_NAME;
    public static final String VIEW_RECENT_TITLE = RECENT + "_" + TitlesContract.TABLE_NAME;

    public static final String VIEW_EMPLOYEES_DETAIL = String.format (Locale.ENGLISH,
            "%s_with_%s", EmployeesContract.TABLE_NAME, DETAIL);

    // most recent title from date
    public static final String TITLE_FROM_DATE = "title_from_date";
    // most recent salary from date
    public static final String SALARY_FROM_DATE = "salary_from_date";

    public static void onCreate(@NonNull SQLiteDatabase db) {
        createRecentSalaryView (db);
        createRecentTitleView (db);
        createEmployeesDetailView (db);
    }

    private static void createRecentSalaryView(@NonNull SQLiteDatabase db) {
        String sql = String.format (Locale.ENGLISH, "CREATE VIEW %s AS " +
                        "SELECT e.%s, s.%s, MAX(s.%s) AS '%s' " +
                        "FROM %s AS 'e' " +
                        "INNER JOIN %s AS 's' " +
                        "ON s.%s = e.%s " +
                        "GROUP BY e.%s",
                VIEW_RECENT_SALARY,
                EmployeesContract._ID, SalariesContract.SALARY, SalariesContract.FROMDATE, SALARY_FROM_DATE,
                EmployeesContract.TABLE_NAME,
                SalariesContract.TABLE_NAME,
                SalariesContract.EMPNO, EmployeesContract._ID,
                EmployeesContract._ID);

        db.execSQL (sql);
    }

    private static void createRecentTitleView(@NonNull SQLiteDatabase db) {
        String sql = String.format (Locale.ENGLISH, "CREATE VIEW %s AS " +
                        "SELECT e.%s, t.%s, MAX(t.%s) AS '%s' " +
                        "FROM %s AS 'e' " +
                        "INNER JOIN %s AS 't' " +
                        "ON t.%s = e.%s " +
                        "GROUP BY e.%s",
                VIEW_RECENT_TITLE,
                EmployeesContract._ID, TitlesContract.TITLE, TitlesContract.FROMDATE, TITLE_FROM_DATE,
                EmployeesContract.TABLE_NAME,
                TitlesContract.TABLE_NAME,
                TitlesContract.EMPNO, EmployeesContract._ID,
                EmployeesContract._ID);

        db.execSQL (sql);
    }

    private static void createEmployeesDetailView(@NonNull SQLiteDatabase db) {
        String sql = String.format (Locale.ENGLISH, "CREATE VIEW %s AS " +
                        "SELECT e.*, t.%s, t.%s, " +
                        "s.%s, s.%s " +
                        "FROM %s AS e " +
                        "INNER JOIN %s AS t " +
                        "ON t.%s = e.%s " +
                        "INNER JOIN %s AS s " +
                        "ON s.%s = e.%s",
                        VIEW_EMPLOYEES_DETAIL,
                        TitlesContract.TITLE, TITLE_FROM_DATE,
                        SalariesContract.SALARY, SALARY_FROM_DATE,
                        EmployeesContract.TABLE_NAME,
                        VIEW_RECENT_TITLE,
                        TitlesContract.EMPNO, EmployeesContract._ID,
                        VIEW_RECENT_SALARY,
                        SalariesContract.EMPNO, EmployeesContract._ID);

        db.execSQL (sql);
    }

}
