package com.mj.circlemenu;

import android.app.Application;

import com.android.library.Library;


/**
 * Created by wiki on 2018/1/23.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Library.init(this, BuildConfig.DEBUG);
    }
}
