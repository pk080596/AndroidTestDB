package com.paul.paulk.testdatabase.provider;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.paul.paulk.testdatabase.api.EmployeeAPI;
import com.paul.paulk.testdatabase.api.ServerAPI;
import com.paul.paulk.testdatabase.model.Employee;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

/**
 * Created by paulk on 12/14/2016.
 */

public class DownloadEmployeesTask implements Runnable {

    private Context context;
    private EmployeeAPI api;


    public DownloadEmployeesTask(Context c) {
        context = c;
        api = ServerAPI.get ().api;
    }

    @Override
    public void run () {
        List<Employee> employees = downloadDatabase();
    }

    public List<Employee> downloadDatabase () {
        try {
            final Response<List<Employee>> employees = api.listEmployees ().execute ();
            return employees.isSuccessful () ? employees.body () : null;
        } catch (IOException e) {
            Toast.makeText (context, "Unable to download database", Toast.LENGTH_LONG).show ();
            return null;
        }
    }
}
