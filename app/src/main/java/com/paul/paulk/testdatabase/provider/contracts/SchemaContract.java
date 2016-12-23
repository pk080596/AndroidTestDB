package com.paul.paulk.testdatabase.provider.contracts;

import android.net.Uri;

import static com.paul.paulk.testdatabase.provider.EmployeesProvider.BASE_URL;

/**
 * Created by paulk on 12/22/2016.
 */

public class SchemaContract extends BaseContract {
    public static final String TABLE_NAME = "schema";

    public static final Uri CONTENT_URI = BASE_URL.buildUpon ().appendPath (TABLE_NAME).build ();

    // name
    public static final String NAME = "name";
    // type
    public static final String TYPE = "type";
    // table
    public static final String TABLE = "table";
    // view
    public static final String VIEW = "view";

    public static final String[] projection = {
            NAME,
            TYPE
    };
}
