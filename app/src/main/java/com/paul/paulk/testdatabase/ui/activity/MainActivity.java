package com.paul.paulk.testdatabase.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paul.paulk.testdatabase.BuildConfig;
import com.paul.paulk.testdatabase.R;
import com.paul.paulk.testdatabase.api.EmployeeAPI;
import com.paul.paulk.testdatabase.api.ServerAPI;
import com.paul.paulk.testdatabase.model.Employee;
import com.paul.paulk.testdatabase.provider.DownloadEmployeesTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<List<Employee>> {

    private final static int EXTERNAL_STORATE = 1;
    private EmployeeAPI api;

    @BindView(R.id.load_database)
    Button loadDatabase;
    @BindView(R.id.employees)
    TextView employees;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        ButterKnife.bind (this);

        ServerAPI.create ();
        api = ServerAPI.get ().api;

        if (ContextCompat.checkSelfPermission (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && BuildConfig.VERSION_CODE > Build.VERSION_CODES.LOLLIPOP_MR1)
            ActivityCompat.requestPermissions (this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET},
                    EXTERNAL_STORATE);
    }

    @OnClick(R.id.load_database)
    public void downloadDatabase () {
        //new Thread(new DownloadEmployeesTask (this)).start ();
        Call<List<Employee>> call = api.listEmployees ();
        call.enqueue (this);
    }

    @Override
    public void onResponse (Call<List<Employee>> call, Response<List<Employee>> response) {
        String firstNames = "";
        for (Employee employee : response.body ()) {
            firstNames += firstNames + employee.getFirstName () + " ";
        }
        employees.setText (firstNames);
    }

    @Override
    public void onFailure (Call<List<Employee>> call, Throwable t) {
        Toast.makeText (this, t.getLocalizedMessage (), Toast.LENGTH_LONG).show ();
    }

    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
    }
}
