package com.paul.paulk.testdatabase.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paul.paulk.testdatabase.provider.contracts.DatabaseObject;
import com.paul.paulk.testdatabase.provider.contracts.TitlesContract;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.paul.paulk.testdatabase.provider.contracts.TitlesContract.EMPNO;
import static com.paul.paulk.testdatabase.provider.contracts.TitlesContract.FROMDATE;
import static com.paul.paulk.testdatabase.provider.contracts.TitlesContract.TITLE;
import static com.paul.paulk.testdatabase.provider.contracts.TitlesContract.TODATE;
import static com.paul.paulk.testdatabase.provider.contracts.TitlesContract._ID;

/**
 * Created by paulk on 12/15/2016.
 */

@Getter
@EqualsAndHashCode
public class Title implements DatabaseObject {
    @Expose @SerializedName ("_id") String id;
    @Expose @SerializedName ("emp_no") String empno;
    @Expose @SerializedName ("title") String title;
    @Expose @SerializedName ("from_date") String fromDate;
    @Expose @SerializedName ("to_date") String toDate;

    public Title (final Cursor cursor) {
        this(cursor, cursor.getColumnIndexOrThrow (_ID), cursor.getColumnIndexOrThrow (EMPNO),
                cursor.getColumnIndexOrThrow (TITLE), cursor.getColumnIndexOrThrow (FROMDATE),
                cursor.getColumnIndexOrThrow (TODATE));
    }

    public Title (final Cursor cursor, final int idxId, final int idxEmpNo, final int idxTitle, final int idxFromDate, final int idxToDate) {
        id = cursor.getString (idxId);
        empno = cursor.getString (idxEmpNo);
        title = cursor.getString (idxTitle);
        fromDate = cursor.getString (idxFromDate);
        toDate = cursor.getString (idxToDate);
    }

    @Override public ContentValues values() {
        ContentValues values = new ContentValues ();

        values.put (_ID, id);
        values.put (EMPNO, empno);
        values.put (TITLE, title);
        values.put (FROMDATE, fromDate);
        values.put (TODATE, toDate);

        return values;
    }

    public Uri uri() {
        return TitlesContract.CONTENT_URI.buildUpon ().appendPath (id).build ();
    }
}
