package com.example.json.mytouzhisystem;

import android.app.Application;

import com.example.json.mytouzhisystem.Utils.DBUserInvestmentUtils;


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DBUserInvestmentUtils.Init(getApplicationContext());
    }
}
