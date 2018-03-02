package com.tony.shareec.example;

import android.app.Application;
import android.support.annotation.Nullable;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.tony.brown.app.Brown;
import com.tony.brown.ec.database.DatabaseManager;
import com.tony.brown.ec.icon.FontEcModule;
import com.tony.brown.net.ApiHost;
import com.tony.brown.net.interceptors.DebugInterceptor;
import com.tony.brown.util.callback.CallbackManager;
import com.tony.brown.util.callback.CallbackType;
import com.tony.brown.util.callback.IGlobalCallback;

import cn.jpush.android.api.JPushInterface;

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
                .withApiHost(ApiHost.API_HOST)
                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .withWeChatAppId("")
                .withWeChatAppSecret("")
                .configure();
        initStetho();
        DatabaseManager.getInstance().init(this);

        // 开启极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        CallbackManager.getInstance()
                .addCallback(CallbackType.TAG_OPEN_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (JPushInterface.isPushStopped(Brown.getApplicationContext())) {
                            //开启极光推送
                            JPushInterface.setDebugMode(true);
                            JPushInterface.init(Brown.getApplicationContext());
                        }
                    }
                })
                .addCallback(CallbackType.TAG_STOP_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (!JPushInterface.isPushStopped(Brown.getApplicationContext())) {
                            JPushInterface.stopPush(Brown.getApplicationContext());
                        }
                    }
                });
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
