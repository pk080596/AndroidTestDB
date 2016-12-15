package com.paul.paulk.testdatabase.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.paul.paulk.testdatabase.api.EmployeeAPI;
import com.paul.paulk.testdatabase.api.ServerAPI;
import com.paul.paulk.testdatabase.model.Employee;
import com.paul.paulk.testdatabase.provider.contracts.EmployeesContract;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

import static com.paul.paulk.testdatabase.provider.EmployeesProvider.insertOrUpdate;

/**
 * Created by paulk on 12/14/2016.
 */

public class DownloadEmployeesTask implements Runnable {

    private final Context context;
    private final EmployeeAPI api;
    private final SQLiteOpenHelper dbHelper;

    public DownloadEmployeesTask (Context c, SQLiteOpenHelper sqLiteOpenHelper) {
        context = c;
        api = ServerAPI.get ().api;
        dbHelper = sqLiteOpenHelper;
    }

    static void Employees (final Context c, SQLiteOpenHelper sqLiteOpenHelper) {
        new Thread (new DownloadEmployeesTask (c, sqLiteOpenHelper)).start ();
    }

    @Override
    public void run () {
        List<Employee> employees = downloadDatabase ();
        if (employees != null && employees.size () > 0)
            storeDownloadedDatabase (employees);
    }

    private List<Employee> downloadDatabase () {
        try {
            final Response<List<Employee>> employees = api.listEmployees ().execute ();
            return employees.isSuccessful () ? employees.body () : null;
        } catch (IOException e) {
            Toast.makeText (context, "Unable to download database", Toast.LENGTH_LONG).show ();
            return null;
        }
    }

    private void storeDownloadedDatabase (List<Employee> employees) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase ();
        db.beginTransaction ();

        try {
            for (Employee employee : employees) {
                insertOrUpdate (db, EmployeesContract.TABLE_NAME, EmployeesContract._ID,
                        employee.values (), EmployeesContract._ID + "=?", new String[]{employee.getId ()});
            }

            db.setTransactionSuccessful ();
        } finally {
            db.endTransaction ();
        }

    }
}
