package com.tony.brown.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.tony.brown.delegates.BrownDelegate;
import com.tony.brown.ec.R;
import com.tony.brown.ec.R2;
import com.tony.brown.net.RestClient;
import com.tony.brown.net.callback.ISuccess;

import butterknife.BindView;

/**
 * Created by Tony on 2018/3/2.
 */

public class AboutDelegate extends BrownDelegate {

    @BindView(R2.id.tv_info)
    AppCompatTextView mTextView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        RestClient.builder()
                .url("about.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String info = JSON.parseObject(response).getString("data");
                        mTextView.setText(info);
                    }
                })
                .build()
                .get();
    }
}
