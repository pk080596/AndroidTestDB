package com.paul.paulk.testdatabase.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paul.paulk.testdatabase.BuildConfig;
import com.paul.paulk.testdatabase.provider.contracts.DatabaseObject;
import com.paul.paulk.testdatabase.provider.contracts.EmployeesContract;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.paul.paulk.testdatabase.provider.contracts.EmployeesContract.BIRTHDATE;
import static com.paul.paulk.testdatabase.provider.contracts.EmployeesContract.FIRSTNAME;
import static com.paul.paulk.testdatabase.provider.contracts.EmployeesContract.GENDER;
import static com.paul.paulk.testdatabase.provider.contracts.EmployeesContract.HIREDATE;
import static com.paul.paulk.testdatabase.provider.contracts.EmployeesContract.LASTNAME;
import static com.paul.paulk.testdatabase.provider.contracts.EmployeesContract._ID;

/**
 * Created by paulk on 12/14/2016.
 */

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Employee implements DatabaseObject {
    @Expose @SerializedName ("emp_no") String id;
    @Expose @SerializedName ("birth_date") String birthDate;
    @Expose @SerializedName ("first_name") String firstName;
    @Expose @SerializedName ("last_name") String lastName;
    @Expose @SerializedName ("gender") String gender;
    @Expose @SerializedName ("hire_date") String hireDate;

    public Employee(final Cursor cursor) {
        this (cursor, cursor.getColumnIndexOrThrow (_ID), cursor.getColumnIndexOrThrow (BIRTHDATE),
                cursor.getColumnIndexOrThrow (FIRSTNAME), cursor.getColumnIndexOrThrow (LASTNAME),
                cursor.getColumnIndexOrThrow (GENDER), cursor.getColumnIndexOrThrow (HIREDATE));
    }

    public Employee(final Cursor cursor, final int idxId, final int idxBD, final int idxFN,
                    final int idxLN, final int idxGEN, final int idxHD) {
        id = cursor.getString (idxId);
        birthDate = cursor.getString (idxBD);
        firstName = cursor.getString (idxFN);
        lastName = cursor.getString (idxLN);
        gender = cursor.getString (idxGEN);
        hireDate = cursor.getString (idxHD);
    }

    @Override
    public ContentValues values() {
        ContentValues values = new ContentValues ();

        values.put(_ID, id);
        values.put (BIRTHDATE, birthDate);
        values.put(FIRSTNAME, firstName);
        values.put (LASTNAME, lastName);
        values.put (GENDER, gender);
        values.put (HIREDATE, hireDate);

        return values;
    }

    public Uri uri() {
        return EmployeesContract.CONTENT_URI.buildUpon ().appendPath (id).build ();
    }
}
