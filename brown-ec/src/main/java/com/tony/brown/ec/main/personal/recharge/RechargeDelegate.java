package com.tony.brown.ec.main.personal.recharge;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.tony.brown.app.AccountManager;
import com.tony.brown.delegates.BrownDelegate;
import com.tony.brown.ec.R;
import com.tony.brown.ec.R2;
import com.tony.brown.net.RestClient;
import com.tony.brown.net.callback.ISuccess;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.tony.brown.app.AccountManager.setMoney;
import static com.tony.brown.util.keyboard.KeyboardS.closeKeyboard;
import static com.tony.brown.util.keyboard.KeyboardS.isSoftInputShow;
import static com.tony.brown.util.storage.BrownPreference.getMoney;
import static com.tony.brown.util.storage.BrownPreference.getUserId;

/**
 * Created by Tony on 2018/3/5.
 */

public class RechargeDelegate extends BrownDelegate {

    private long mUserId = 0;
    private int mUserMoney = 0;
    private int mRechargeMoney = 0;

    @BindView(R2.id.tv_recharge_current)
    AppCompatTextView mTvCurrentNum = null;
    @BindView(R2.id.tv_recharge_100)
    AppCompatTextView mTvRecharge100 = null;
    @BindView(R2.id.tv_recharge_500)
    AppCompatTextView mTvRecharge500 = null;
    @BindView(R2.id.tv_recharge_1000)
    AppCompatTextView mTvRecharge1000 = null;
    @BindView(R2.id.et_recharge_diy)
    AppCompatEditText mEtRechargeDiy = null;
    @BindView(R2.id.tv_recharge_num)
    AppCompatTextView mTvRechargeNum = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_recharge;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mUserId = getUserId(AccountManager.SignTag.USER_ID.name());
        mUserMoney = getMoney(AccountManager.SignTag.USER_MONEY.name());
        mTvCurrentNum.setText(String.valueOf(mUserMoney) + " 元");
    }

    @OnClick(R2.id.tv_recharge_100)
    void onClickRe100() {
        mTvRecharge100.setBackgroundResource(R.drawable.shape_corner_red);
        mTvRecharge100.setTextColor(Color.WHITE);
        mTvRecharge500.setBackgroundResource(R.drawable.shape_corner);
        mTvRecharge500.setTextColor(ContextCompat.getColor(getContext(), R.color.we_chat_black));
        mTvRecharge1000.setBackgroundResource(R.drawable.shape_corner);
        mTvRecharge1000.setTextColor(ContextCompat.getColor(getContext(), R.color.we_chat_black));

        mTvRechargeNum.setText(String.valueOf(100));
        mRechargeMoney = 100;
    }

    @OnClick(R2.id.tv_recharge_500)
    void onClickRe500() {
        mTvRecharge500.setBackgroundResource(R.drawable.shape_corner_red);
        mTvRecharge500.setTextColor(Color.WHITE);
        mTvRecharge100.setBackgroundResource(R.drawable.shape_corner);
        mTvRecharge100.setTextColor(ContextCompat.getColor(getContext(), R.color.we_chat_black));
        mTvRecharge1000.setBackgroundResource(R.drawable.shape_corner);
        mTvRecharge1000.setTextColor(ContextCompat.getColor(getContext(), R.color.we_chat_black));

        mTvRechargeNum.setText(String.valueOf(500));
        mRechargeMoney = 500;
    }

    @OnClick(R2.id.tv_recharge_1000)
    void onClickRe1000() {
        mTvRecharge1000.setBackgroundResource(R.drawable.shape_corner_red);
        mTvRecharge1000.setTextColor(Color.WHITE);
        mTvRecharge100.setBackgroundResource(R.drawable.shape_corner);
        mTvRecharge100.setTextColor(ContextCompat.getColor(getContext(), R.color.we_chat_black));
        mTvRecharge500.setBackgroundResource(R.drawable.shape_corner);
        mTvRecharge500.setTextColor(ContextCompat.getColor(getContext(), R.color.we_chat_black));

        mTvRechargeNum.setText(String.valueOf(1000));
        mRechargeMoney = 1000;
    }

    @OnTextChanged(R2.id.et_recharge_diy)
    void onEditReDiy(CharSequence text) {
        mTvRecharge100.setBackgroundResource(R.drawable.shape_corner);
        mTvRecharge100.setTextColor(ContextCompat.getColor(getContext(), R.color.we_chat_black));
        mTvRecharge500.setBackgroundResource(R.drawable.shape_corner);
        mTvRecharge500.setTextColor(ContextCompat.getColor(getContext(), R.color.we_chat_black));
        mTvRecharge1000.setBackgroundResource(R.drawable.shape_corner);
        mTvRecharge1000.setTextColor(ContextCompat.getColor(getContext(), R.color.we_chat_black));

        if (Objects.equals(String.valueOf(text), "")) {
            mTvRechargeNum.setText(String.valueOf(0));
            mRechargeMoney = 0;
        } else {
            mTvRechargeNum.setText(String.valueOf(text));
            mRechargeMoney = Integer.valueOf(String.valueOf(text));
        }
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R2.id.btn_recharge)
    void onClickRecharge() {
        if (isSoftInputShow(getActivity())) {
            closeKeyboard(mEtRechargeDiy, getContext());
        }
        mUserMoney = mUserMoney + mRechargeMoney;

        if (mRechargeMoney > 0) {
            RestClient.builder()
                    .url("recharge.php")
                    .loader(getContext())
                    .params("uId", mUserId)
                    .params("uMoney", mUserMoney)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            if (Objects.equals(response, "OK")) {
                                mTvCurrentNum.setText(String.valueOf(mUserMoney) + " 元");
                                setMoney(mUserMoney);
                                Toast.makeText(getContext(), "充值成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "充值失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .build()
                    .get();
        } else {
            Toast.makeText(getContext(), "请选择/输入充值金额", Toast.LENGTH_SHORT).show();
        }

    }

}
