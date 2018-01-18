package com.tony.brown.util.callback;

import android.support.annotation.Nullable;

/**
 * Created by Tony on 2018/1/18.
 */

public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);
}
