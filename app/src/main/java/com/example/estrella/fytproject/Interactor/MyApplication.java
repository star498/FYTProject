package com.example.estrella.fytproject.Interactor;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by Estrella on 19/04/2018.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());

    }
}
