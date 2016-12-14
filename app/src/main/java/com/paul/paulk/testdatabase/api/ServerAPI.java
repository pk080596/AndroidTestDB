package com.paul.paulk.testdatabase.api;

import android.content.Context;

import com.paul.paulk.testdatabase.Config;
import com.paul.paulk.testdatabase.model.Employee;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paulk on 12/14/2016.
 */

public class ServerAPI {

    private static ServerAPI instance = null;
    public final EmployeeAPI api;

    public static void create() {
        if (null != instance)
            throw new IllegalStateException ("An instance already exists");

        instance = new ServerAPI ();
    }

    public static ServerAPI get() {
        if (null != instance)
            return new ServerAPI ();

        return instance;
    }

    private ServerAPI() {
        Retrofit retrofit = new Retrofit.Builder ()
                .baseUrl (Config.BASE_URL)
                .addConverterFactory (GsonConverterFactory.create ())
                .build ();

        api = retrofit.create (EmployeeAPI.class);
    }
}
