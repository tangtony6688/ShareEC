package com.tony.brown.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.tony.brown.delegates.BrownDelegate;
import com.tony.brown.ec.R;

/**
 * Created by Tony on 2018/1/17.
 */

public class NameDelegate extends BrownDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_name;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
