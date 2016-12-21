package com.paul.paulk.testdatabase.provider.contracts;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.Locale;

import lombok.NonNull;

import static android.provider.BaseColumns._ID;
import static com.paul.paulk.testdatabase.provider.EmployeesProvider.BASE_URL;

/**
 * Created by paulk on 12/14/2016.
 */

public final class EmployeesContract extends BaseContract {
    public static final String TABLE_NAME = "employees";

    public static final Uri CONTENT_URI = BASE_URL.buildUpon ().appendPath (PATH.EMPLOYEE).build ();

    // employee id
    public static final String _ID = "emp_no";
    // employee birth date
    public static final String BIRTHDATE = "birth_date";
    // employee first name
    public static final String FIRSTNAME = "first_name";
    // employee last name
    public static final String LASTNAME = "last_name";
    // employee gender
    public static final String GENDER = "gender";
    // employee hire date
    public static final String HIREDATE = "hire_date";

    public static final String[] PROJECTION = {
            _ID,
            BIRTHDATE,
            FIRSTNAME,
            LASTNAME,
            GENDER,
            HIREDATE
    };

    private static final int GENDER_LEN = 1;

    public static void onCreate(@NonNull final SQLiteDatabase db) {
        // TODO: better restrictions
        String sql = String.format(Locale.ENGLISH, "CREATE TABLE %s (" + // table name
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +       // empno
                        "%s DATETIME NOT NULL, " +                       // birthdate
                        "%s TEXT NOT NULL, " +                           // firstname
                        "%s TEXT NOT NULL, " +                           // lastname
                        "%s TEXT NOT NULL, " +                           // gender
                        "%s DATETIME NOT NULL)",                         // hiredate
                        TABLE_NAME,
                        _ID,
                        BIRTHDATE,
                        FIRSTNAME,
                        LASTNAME,
                        GENDER,
                        HIREDATE);

        db.execSQL (sql);

        // TODO: create index
    }

}
