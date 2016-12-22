package com.paul.paulk.testdatabase.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.paul.paulk.testdatabase.api.EmployeeAPI;
import com.paul.paulk.testdatabase.api.ServerAPI;
import com.paul.paulk.testdatabase.model.Employee;
import com.paul.paulk.testdatabase.model.Salary;
import com.paul.paulk.testdatabase.model.Title;
import com.paul.paulk.testdatabase.provider.contracts.EmployeesContract;
import com.paul.paulk.testdatabase.provider.contracts.SalariesContract;
import com.paul.paulk.testdatabase.provider.contracts.TitlesContract;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

import static android.provider.BaseColumns._ID;
import static com.paul.paulk.testdatabase.provider.EmployeesProvider.insertOrUpdate;

/**
 * Created by paulk on 12/14/2016.
 */

public class DownloadEmployeesTask implements Runnable {
    private static final String SQLITE_SEQUENCE = "sqlite_sequence";
    private static final String SQLITE_SEQUENCE_NAME = "name";

    private final Context context;
    private final EmployeeAPI api;
    private final SQLiteOpenHelper dbHelper;
    private final Boolean wipe;

    private final int state;
    private final static int EMPLOYEE = 0b100,
                             SALARY = 0b010,
                             TITLE = 0b001,
                             ALL = 0b111,
                             WIPE = 0b000;


    private DownloadEmployeesTask (final Context c, final SQLiteOpenHelper sqLiteOpenHelper, final Boolean employees,
                                  final Boolean salaries, final Boolean titles, final Boolean wipeData) {
        context = c;
        api = ServerAPI.get ().api;
        dbHelper = sqLiteOpenHelper;
        wipe = wipeData;
        state = (employees ? EMPLOYEE : 0) |
                (salaries ? SALARY : 0) |
                (titles ? TITLE : 0);
    }

    static void Employees (final Context c, final SQLiteOpenHelper sqLiteOpenHelper, final Boolean wipeData) {
        new Thread (new DownloadEmployeesTask (c, sqLiteOpenHelper, true, false, false, wipeData)).start ();
    }

    static void Salaries (final Context c, final SQLiteOpenHelper sqLiteOpenHelper,  final Boolean wipeData) {
        new Thread (new DownloadEmployeesTask (c, sqLiteOpenHelper, false, true, false, wipeData)).start ();
    }

    static void Titles (final Context c, final SQLiteOpenHelper sqLiteOpenHelper, final Boolean wipeData) {
        new Thread (new DownloadEmployeesTask (c, sqLiteOpenHelper, false, false, true, wipeData)).start ();
    }

    static void All (final Context c, final SQLiteOpenHelper sqLiteOpenHelper, final Boolean wipeData) {
        new Thread (new DownloadEmployeesTask (c, sqLiteOpenHelper, true, true, true, wipeData)).start ();
    }

    static void Wipe (final Context c, final SQLiteOpenHelper sqLiteOpenHelper) {
        new Thread (new DownloadEmployeesTask (c, sqLiteOpenHelper, false, false, false, true)).start();
    }

    @Override
    public void run () {
        download ();
    }

    private void download() {
        switch (state) {
            case EMPLOYEE:
                storeEmployees (downloadEmployees ());
                break;
            case SALARY:
                storeSalaries (downloadSalaries ());
                break;
            case TITLE:
                storeTitles (downloadTitles ());
                break;
            case ALL:
                storeEmployees (downloadEmployees ());
                storeSalaries (downloadSalaries ());
                storeTitles (downloadTitles ());
                break;
            case WIPE:
                wipe(dbHelper.getWritableDatabase ());
                break;
        }
    }

    private List<Employee> downloadEmployees () {
        try {
            final Response<List<Employee>> employees = api.listEmployees ().execute ();
            return employees.isSuccessful () ? employees.body () : null;
        } catch (IOException e) {
            Toast.makeText (context, "Unable to download database", Toast.LENGTH_LONG).show ();
            return null;
        }
    }

    private List<Salary> downloadSalaries () {
        try {
            final Response<List<Salary>> salaries = api.listSalaries ().execute ();
            return salaries.isSuccessful () ? salaries.body () : null;
        } catch (IOException e) {
            Toast.makeText (context, "Unable to download database", Toast.LENGTH_LONG).show ();
            return null;
        }
    }

    private List<Title> downloadTitles () {
        try {
            final Response<List<Title>> titles = api.listTitles ().execute ();
            return titles.isSuccessful () ? titles.body () : null;
        } catch (IOException e) {
            Toast.makeText (context, "Unable to download database", Toast.LENGTH_LONG).show ();
            return null;
        }
    }

    // TODO: can probably combine the store methods
    private void storeEmployees (final List<Employee> employees) {
        if (null == employees || employees.size () == 0)
            return;

        final SQLiteDatabase db = dbHelper.getWritableDatabase ();
        db.beginTransaction ();

        try {
            if (wipe) wipe(db);

            for (Employee employee : employees) {
                insertOrUpdate (db, EmployeesContract.TABLE_NAME, EmployeesContract._ID,
                        employee.values (), EmployeesContract._ID + "=?", new String[]{employee.getId ()});
            }

            db.setTransactionSuccessful ();
        } finally {
            db.endTransaction ();
        }

    }

    private void storeSalaries (final List<Salary> salaries) {
        if (null == salaries || salaries.size () == 0)
            return;

        final SQLiteDatabase db = dbHelper.getWritableDatabase ();
        db.beginTransaction ();

        try {
            if (wipe) wipe(db);

            for (Salary salary : salaries) {
                insertOrUpdate (db, SalariesContract.TABLE_NAME, _ID,
                        salary.values (), _ID + "=?", new String[]{salary.getId ()});
            }

            db.setTransactionSuccessful ();
        } finally {
            db.endTransaction ();
        }

    }

    private void storeTitles (final List<Title> titles) {
        if (null == titles || titles.size () == 0)
            return;

        final SQLiteDatabase db = dbHelper.getWritableDatabase ();
        db.beginTransaction ();

        try {
            if (wipe) wipe(db);

            for (Title title : titles) {
                insertOrUpdate (db, TitlesContract.TABLE_NAME, _ID,
                        title.values (), _ID + "=?", new String[]{title.getId ()});
            }

            db.setTransactionSuccessful ();
        } finally {
            db.endTransaction ();
        }

    }

    private void wipe (SQLiteDatabase db) {
        switch (state) {
            case SALARY:
                db.delete (SalariesContract.TABLE_NAME, null, null);
                db.delete (SQLITE_SEQUENCE, SQLITE_SEQUENCE_NAME + "=?", new String[]{SalariesContract.TABLE_NAME});
                break;
            case TITLE:
                db.delete (TitlesContract.TABLE_NAME, null, null);
                db.delete (SQLITE_SEQUENCE, SQLITE_SEQUENCE_NAME + "=?", new String[]{TitlesContract.TABLE_NAME});
                break;
            case EMPLOYEE: // fall through
            case ALL:
            case WIPE:
                db.delete (EmployeesContract.TABLE_NAME, null, null);
                db.delete (SQLITE_SEQUENCE, null, null);
                break;
        }
    }
}
