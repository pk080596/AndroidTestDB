package com.paul.paulk.testdatabase.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.paul.paulk.testdatabase.BuildConfig;
import com.paul.paulk.testdatabase.R;
import com.paul.paulk.testdatabase.api.EmployeeAPI;
import com.paul.paulk.testdatabase.api.ServerAPI;
import com.paul.paulk.testdatabase.provider.contracts.EmployeesContract;
import com.paul.paulk.testdatabase.provider.contracts.SalariesContract;
import com.paul.paulk.testdatabase.provider.contracts.SchemaContract;
import com.paul.paulk.testdatabase.provider.contracts.TitlesContract;
import com.paul.paulk.testdatabase.ui.adapter.ExpandableListAdapter;
import com.paul.paulk.testdatabase.ui.model.ExpandableMenuItem;
import com.paul.paulk.testdatabase.ui.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.paul.paulk.testdatabase.provider.DatabaseHelper.DATABASE_NAME;
import static com.paul.paulk.testdatabase.provider.contracts.BaseContract.FORCE_DOWNLOAD;
import static com.paul.paulk.testdatabase.provider.contracts.BaseContract.PATH.ALL;
import static com.paul.paulk.testdatabase.provider.contracts.BaseContract.YES;
import static com.paul.paulk.testdatabase.provider.contracts.EmployeesDetailContract.WIPE;

public class MainActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private final static int LOADER_SCHEMA = 100;
    private final static int LOADER_EMPLOYEES = 101;
    private final static int LOADER_SALARIES = 102;
    private final static int LOADER_TITLES = 103;

    private final static int EXTERNAL_STORAGE = 1;
    private EmployeeAPI api;

    private ExpandableListAdapter menuAdapter;
    private List<ExpandableMenuItem> menuItems;

    @BindView(R.id.employees)
    TextView employees;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.nav_list)
    ExpandableListView navList;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_navigation_drawer);
        ButterKnife.bind (this);

        initMenuItems ();
        setupActionBar ();
        createServerAPI ();
        checkPermission ();
    }

    private void createServerAPI() {
        ServerAPI.create ();
        api = ServerAPI.get ().api;
    }

    private void setupActionBar() {
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(android.R.drawable.ic_menu_sort_by_size);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupNavigationDrawer(Cursor cursor) {
        prepareMenuItems (cursor);
        menuAdapter = new ExpandableListAdapter (this, menuItems, navList);
        navList.setAdapter (menuAdapter);
        navList.setOnChildClickListener (this);
        navList.setOnGroupClickListener (this);
    }

    private void prepareMenuItems(Cursor cursor) {
        List<MenuItem> tables = new ArrayList<> ();
        List<MenuItem> views = new ArrayList<> ();
        while (cursor.moveToNext ()) {
            String type = cursor.getString (cursor.getColumnIndexOrThrow (SchemaContract.TYPE));
            if (type.equals(SchemaContract.TABLE))
                tables.add (new MenuItem (cursor.getString (cursor.getColumnIndexOrThrow (SchemaContract.NAME))));
            else if (type.equals(SchemaContract.VIEW))
                views.add (new MenuItem (cursor.getString (cursor.getColumnIndexOrThrow (SchemaContract.NAME))));
        }
        menuItems = new ArrayList<> ();
        menuItems.add(new ExpandableMenuItem (tables, getString(R.string.tables)));
        menuItems.add(new ExpandableMenuItem (views, getString(R.string.views)));
    }

    private void initMenuItems() {
        getSupportLoaderManager ().initLoader (LOADER_SCHEMA, null, this);
    }

    @OnClick(R.id.download_database)
    public void downloadDatabase () {
        Uri uri = EmployeesContract.CONTENT_URI.buildUpon ().appendPath (ALL).build ();
        getContentResolver ().query (uri, null, null, null, null);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId ()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen (GravityCompat.START))
                    drawerLayout.closeDrawer (GravityCompat.START);
                else
                    drawerLayout.openDrawer (GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected (item);
    }

    @Override
    public boolean onChildClick (ExpandableListView expandableListView, View view, int i, int i1, long l) {
        return false;
    }

    @Override
    public boolean onGroupClick (ExpandableListView expandableListView, View view, int i, long l) {
        return false;
    }

    @Override
    public Loader<Cursor> onCreateLoader (int id, Bundle args) {
        Uri uri;
        switch (id) {
            case LOADER_SCHEMA:
                return new CursorLoader (this, SchemaContract.CONTENT_URI, SchemaContract.projection, null, null, null);
            case LOADER_EMPLOYEES:
                uri = EmployeesContract.CONTENT_URI.buildUpon ().appendQueryParameter (FORCE_DOWNLOAD, YES).build();
                return new CursorLoader (this, uri, EmployeesContract.PROJECTION, null, null, null);
            case LOADER_SALARIES:
                uri = SalariesContract.CONTENT_URI.buildUpon ().appendQueryParameter (FORCE_DOWNLOAD, YES).build();
                return new CursorLoader (this, uri, SalariesContract.PROJECTION, null, null, null);
            case LOADER_TITLES:
                uri = TitlesContract.CONTENT_URI.buildUpon ().appendQueryParameter (FORCE_DOWNLOAD, YES).build();
                return new CursorLoader (this, uri, TitlesContract.PROJECTION, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished (Loader<Cursor> loader, Cursor data) {
        if (null ==  data || !data.moveToFirst ()) return;

        switch (loader.getId ()) {
            case LOADER_SCHEMA:
                setupNavigationDrawer (data);
                break;
            case LOADER_EMPLOYEES:
                break;
            case LOADER_SALARIES:
                break;
            case LOADER_TITLES:
                break;
        }
    }

    @Override
    public void onLoaderReset (Loader<Cursor> loader) {

    }
}
