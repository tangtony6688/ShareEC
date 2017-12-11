package com.tony.brown.app;

import android.content.Context;
import java.util.logging.Handler;

/**
 * Created by Tony on 2017/12/9.
 */

public final class Brown {

    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getBrownConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

    public static void test() {
    }
}
