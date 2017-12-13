package com.tony.shareec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.tony.brown.activities.ProxyActivity;
import com.tony.brown.delegates.BrownDelegate;
import com.tony.brown.ec.launcher.LauncherDelegate;
import com.tony.brown.ec.sign.SignUpDelegate;

public class ExampleActivity extends ProxyActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public BrownDelegate setRootDelegate() {
        return new SignUpDelegate();
    }
}
