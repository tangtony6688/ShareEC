package com.tony.brown.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.tony.brown.app.AccountManager;
import com.tony.brown.delegates.BrownDelegate;
import com.tony.brown.ec.R;
import com.tony.brown.ec.R2;
import com.tony.brown.net.RestClient;
import com.tony.brown.net.callback.ISuccess;
import com.tony.brown.util.log.BrownLogger;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tony.brown.app.AccountManager.setUserName;
import static com.tony.brown.util.storage.BrownPreference.getUserId;
import static com.tony.brown.util.storage.BrownPreference.getUserName;

/**
 * Created by Tony on 2018/1/17.
 */

public class NameDelegate extends BrownDelegate {

    @BindView(R2.id.et_name)
    AppCompatEditText mName = null;

    private long mUserId = 0;


    @Override
    public Object setLayout() {
        return R.layout.delegate_name;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mUserId = getUserId(AccountManager.SignTag.USER_ID.name());
        mName.setText(getUserName(AccountManager.SignTag.USER_NAME.name()));
    }

    @OnClick(R2.id.btn_name_submit)
    void onClickSubmit() {
        RestClient.builder()
                .url("profile_name_change.php")
                .params("user_id", mUserId)
                .params("new_name", mName.getText().toString())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if (Objects.equals(response, "OK")) {
                            setUserName(mName.getText().toString());
                            getSupportDelegate().pop();
                        }
                    }
                })
                .build()
                .get();
    }
}
