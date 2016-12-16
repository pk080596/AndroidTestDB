package com.paul.paulk.testdatabase.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paul.paulk.testdatabase.provider.DatabaseHelper;
import com.paul.paulk.testdatabase.provider.contracts.DatabaseObject;
import com.paul.paulk.testdatabase.provider.contracts.SalariesContract;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.paul.paulk.testdatabase.provider.EmployeesProvider.BASE_URL;
import static com.paul.paulk.testdatabase.provider.contracts.SalariesContract.EMPNO;
import static com.paul.paulk.testdatabase.provider.contracts.SalariesContract.FROMDATE;
import static com.paul.paulk.testdatabase.provider.contracts.SalariesContract.SALARY;
import static com.paul.paulk.testdatabase.provider.contracts.SalariesContract.TODATE;
import static com.paul.paulk.testdatabase.provider.contracts.SalariesContract._ID;

/**
 * Created by paulk on 12/15/2016.
 */

@Getter
@EqualsAndHashCode
public class Salary implements DatabaseObject {
    @Expose @SerializedName ("_id") String id;
    @Expose @SerializedName ("emp_no") String empno;
    @Expose @SerializedName ("salary") String salary;
    @Expose @SerializedName ("from_date") String fromDate;
    @Expose @SerializedName ("to_date") String toDate;

    public Salary (final Cursor cursor) {
        this(cursor, cursor.getColumnIndexOrThrow (_ID), cursor.getColumnIndexOrThrow (EMPNO),
                cursor.getColumnIndexOrThrow (SALARY), cursor.getColumnIndexOrThrow (FROMDATE),
                cursor.getColumnIndexOrThrow (TODATE));
    }

    public Salary (final Cursor cursor, final int idxId, final int idxEmpNo, final int idxSalary, final int idxFromDate, final int idxToDate) {
        id = cursor.getString (idxId);
        empno = cursor.getString (idxEmpNo);
        salary = cursor.getString (idxSalary);
        fromDate = cursor.getString (idxFromDate);
        toDate = cursor.getString (idxToDate);
    }

    @Override public ContentValues values() {
        ContentValues values = new ContentValues ();

        values.put (_ID, id);
        values.put (EMPNO, empno);
        values.put (SALARY, salary);
        values.put (FROMDATE, fromDate);
        values.put (TODATE, toDate);

        return values;
    }

    public Uri uri() {
        return SalariesContract.CONTENT_URI.buildUpon ().appendPath (id).build ();
    }
}
