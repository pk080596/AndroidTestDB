package com.paul.paulk.testdatabase.api;

import com.paul.paulk.testdatabase.model.Employee;
import com.paul.paulk.testdatabase.model.Salary;
import com.paul.paulk.testdatabase.model.Title;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by paulk on 12/14/2016.
 */

public interface EmployeeAPI {
    @GET("/employees")
    Call<List<Employee>> listEmployees();

    @GET("/salaries")
    Call<List<Salary>> listSalaries();

    @GET("/titles")
    Call<List<Title>> listTitles();
}
