package com.tony.shareec.example;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.tony.brown.app.Brown;
import com.tony.brown.ec.icon.FontEcModule;
import com.tony.brown.net.interceptors.DebugInterceptor;

/**
 * Created by Tony on 2017/12/9.
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Brown.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withLoaderDelayed(1000)
                .withApiHost("http://127.0.0.1/")
                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .configure();
    }
}
