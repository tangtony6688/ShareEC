package com.tony.brown.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.tony.brown.app.Brown;

/**
 * Created by Tony on 2017/12/11.
 */

public class DimenUtil {

    public static int getScreenWidth() {
        final Resources resources = Brown.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = Brown.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

}
