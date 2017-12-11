package com.tony.brown.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tony on 2017/12/9.
 */

/* 配置文件的存储与获取 */

public class Configurator {

    private static final HashMap<Object, Object> BROWN_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    private Configurator() {
        BROWN_CONFIGS.put(ConfigKeys.CONFIG_READY.name(), false);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getBrownConfigs() {
        return BROWN_CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure() {
        initIcons();

        BROWN_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    public final Configurator withApiHost(String host) {
        BROWN_CONFIGS.put(ConfigKeys.API_HOST.name(), host);
        return this;
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) BROWN_CONFIGS.get(ConfigKeys.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready, call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = BROWN_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) BROWN_CONFIGS.get(key);
    }

}
