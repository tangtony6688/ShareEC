package com.tony.brown.ui.recycler;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * Created by Tony on 2017/12/27.
 */

public class BaseDecoration extends DividerItemDecoration {

    private BaseDecoration(@ColorInt int color, int size) {
        setDividerLookup(new DividerLookupImpl(color, size));
    }

    public static BaseDecoration create(@ColorInt int color, int size) {
        return new BaseDecoration(color, size);
    }
}
