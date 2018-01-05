package com.tony.brown.ui.recycler;

import com.google.auto.value.AutoValue;

/**
 * Created by Tony on 2017/12/28.
 */

@AutoValue
public abstract class RgbValue {

    public abstract int red();

    public abstract int green();

    public abstract int blue();

    public static RgbValue create(int red, int green, int blue) {
        return new AutoValue_RgbValue(red, green, blue);
    }
}
