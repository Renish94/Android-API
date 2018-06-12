package com.android.application;

import android.app.Application;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.api.API;
import com.android.api.common.ConnectionQuality;
import com.android.api.interceptors.HttpLoggingInterceptor.Level;
import com.android.api.interfaces.ConnectionQualityChangeListener;

public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication appInstance = null;

    public static MyApplication getInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;

        API.initialize(getApplicationContext());

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        API.setBitmapDecodeOptions(options);

        API.enableLogging();
        API.enableLogging(Level.NONE);

        API.removeConnectionQualityChangeListener();
        API.setConnectionQualityChangeListener(new ConnectionQualityChangeListener() {
            @Override
            public void onChange(ConnectionQuality currentConnectionQuality, int currentBandwidth) {
                Log.d(TAG, "onChange: currentConnectionQuality : " + currentConnectionQuality + " currentBandwidth : " + currentBandwidth);
            }
        });
    }
}