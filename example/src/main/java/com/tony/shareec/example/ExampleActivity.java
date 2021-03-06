package com.tony.shareec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.tony.brown.activities.ProxyActivity;
import com.tony.brown.app.Brown;
import com.tony.brown.delegates.BrownDelegate;
import com.tony.brown.ec.launcher.LauncherDelegate;
import com.tony.brown.ec.main.EcBottomDelegate;
import com.tony.brown.ec.sign.ISignListener;
import com.tony.brown.ec.sign.SignInDelegate;
import com.tony.brown.ui.launcher.ILauncherListener;
import com.tony.brown.ui.launcher.OnLauncherFinishTag;

import cn.jpush.android.api.JPushInterface;
import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity implements ISignListener, ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Brown.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    public BrownDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
//        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
        getSupportDelegate().replaceFragment(new EcBottomDelegate(), false);
    }

    @Override
    public void onSignUpSuccess() {
//        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
        getSupportDelegate().replaceFragment(new EcBottomDelegate(), false);
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
//                Toast.makeText(this, "启动结束，用户登录了", Toast.LENGTH_LONG).show();
                getSupportDelegate().replaceFragment(new EcBottomDelegate(), false);
                break;
            case NOT_SIGNED:
//                Toast.makeText(this, "启动结束，用户没登录", Toast.LENGTH_LONG).show();
                getSupportDelegate().replaceFragment(new SignInDelegate(), false);
                break;
            default:
                break;
        }
    }
}
