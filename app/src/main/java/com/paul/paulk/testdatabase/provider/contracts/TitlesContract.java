package com.paul.paulk.testdatabase.provider.contracts;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.Locale;

import static android.provider.BaseColumns._ID;
import static com.paul.paulk.testdatabase.provider.EmployeesProvider.BASE_URL;

/**
 * Created by paulk on 12/15/2016.
 */

public class TitlesContract extends BaseContract {
    public static final String TABLE_NAME = "titles";

    public static final Uri CONTENT_URI = BASE_URL.buildUpon ().appendPath (PATH.TITLE).build ();

    // employee id
    public static final String EMPNO = "emp_no";
    // employee title
    public static final String TITLE = "title";
    // employee title assigned date
    public static final String FROMDATE = "from_date";
    // employee title expire date
    public static final String TODATE = "to_date";

    public static final String[] PROJECTION = {
            _ID,
            EMPNO,
            TITLE
    };

    // TODO: remove auto-index and make primary key (emp_no, title)
    public static void onCreate(@NonNull final SQLiteDatabase db) {
        String sql = String.format(Locale.ENGLISH, "CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s INTEGER NOT NULL REFERENCES %s(%s) ON DELETE CASCADE, " +
                        "%s TEXT NOT NULL, " +
                        "%s DATETIME NOT NULL, " +
                        "%s DATETIME NOT NULL)",
                        TABLE_NAME,
                        _ID,
                        EMPNO, EmployeesContract.TABLE_NAME, EmployeesContract._ID,
                        TITLE,
                        FROMDATE,
                        TODATE);

        db.execSQL (sql);

        // TODO: create index
    }
}
