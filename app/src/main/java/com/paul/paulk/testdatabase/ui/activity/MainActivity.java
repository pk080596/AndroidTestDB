package com.paul.paulk.testdatabase.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.paul.paulk.testdatabase.BuildConfig;
import com.paul.paulk.testdatabase.R;
import com.paul.paulk.testdatabase.api.EmployeeAPI;
import com.paul.paulk.testdatabase.api.ServerAPI;
import com.paul.paulk.testdatabase.provider.contracts.EmployeesContract;
import com.paul.paulk.testdatabase.provider.contracts.EmployeesDetailContract;
import com.paul.paulk.testdatabase.provider.contracts.SalariesContract;
import com.paul.paulk.testdatabase.provider.contracts.TitlesContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.paul.paulk.testdatabase.provider.DatabaseHelper.DATABASE_NAME;
import static com.paul.paulk.testdatabase.provider.contracts.BaseContract.FORCE_DOWNLOAD;
import static com.paul.paulk.testdatabase.provider.contracts.BaseContract.YES;
import static com.paul.paulk.testdatabase.provider.contracts.EmployeesDetailContract.WIPE;

public class MainActivity extends AppCompatActivity {

    private final static int EXTERNAL_STORAGE = 1;
    private EmployeeAPI api;

    @BindView(R.id.employees)
    TextView employees;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        ButterKnife.bind (this);

        ServerAPI.create ();
        api = ServerAPI.get ().api;
        checkPermission ();
    }

    @OnClick(R.id.download_database)
    public void downloadDatabase () {
        Uri uri = EmployeesContract.CONTENT_URI.buildUpon ().appendQueryParameter (FORCE_DOWNLOAD, YES).build();
        Uri uri2 = SalariesContract.CONTENT_URI.buildUpon ().appendQueryParameter (FORCE_DOWNLOAD, YES).build();
        Uri uri3 = TitlesContract.CONTENT_URI.buildUpon ().appendQueryParameter (FORCE_DOWNLOAD, YES).build();
        getContentResolver().query(uri, EmployeesContract.PROJECTION, null, null, null);
        getContentResolver().query(uri2, SalariesContract.PROJECTION, null, null, null);
        getContentResolver().query(uri3, TitlesContract.PROJECTION, null, null, null);
    }

    @OnClick(R.id.wipe_database)
    public void wipeDatabase () {
        Uri uri = EmployeesContract.CONTENT_URI.buildUpon ().appendPath (WIPE).build ();
        getContentResolver ().query (uri, null, null, null, null);
    }

    @OnClick(R.id.delete_database)
    public void deleteDatabase () {
        this.deleteDatabase (DATABASE_NAME);
    }

    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && BuildConfig.VERSION_CODE > Build.VERSION_CODES.LOLLIPOP_MR1)
            ActivityCompat.requestPermissions (this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET}, EXTERNAL_STORAGE);
    }
}
