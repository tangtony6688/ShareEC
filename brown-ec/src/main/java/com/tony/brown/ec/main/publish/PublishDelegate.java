package com.tony.brown.ec.main.publish;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tony.brown.app.AccountManager;
import com.tony.brown.delegates.bottom.BottomItemDelegate;
import com.tony.brown.ec.R;
import com.tony.brown.ec.R2;
import com.tony.brown.net.RestClient;
import com.tony.brown.net.callback.ISuccess;
import com.tony.brown.util.log.BrownLogger;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tony.brown.util.storage.BrownPreference.getUserId;

/**
 * Created by Tony on 2017/12/15.
 */

public class PublishDelegate extends BottomItemDelegate {

    private String mGoodName = "";
    private String mGoodDesc = "";
    private String mGoodRent = "";
    private String mGoodGuarantee = "";
    private int mGoodCount = 0;
    private String[] mGoodClass = new String[]{};
    private int mGoodClassId = 0;
    private long mUserId = 0;

    @BindView(R2.id.et_good_name)
    AppCompatEditText mEtGoodName = null;
    @BindView(R2.id.et_good_desc)
    AppCompatEditText mEtGoodDesc = null;
    @BindView(R2.id.et_good_rent_num)
    AppCompatEditText mEtGoodRent = null;
    @BindView(R2.id.et_good_guarantee_num)
    AppCompatEditText mEtGoodGuarantee = null;
    @BindView(R2.id.tv_count_num)
    AppCompatTextView mTvGoodCount = null;
    @BindView(R2.id.tv_good_class)
    AppCompatTextView mTvGoodClass = null;

    @OnClick(R2.id.tv_publish_confirm)
    void onClickConfirm() {
        mGoodName = String.valueOf(mEtGoodName.getText());
        mGoodDesc = String.valueOf(mEtGoodDesc.getText());
        mGoodRent = String.valueOf(String.valueOf(mEtGoodRent.getText()));
        mGoodGuarantee = String.valueOf(String.valueOf(mEtGoodGuarantee.getText()));
        mGoodCount = Integer.valueOf(String.valueOf(mTvGoodCount.getText()));

        if (!Objects.equals(mGoodName, "") && !Objects.equals(mGoodDesc, "") && mGoodClassId != 0) {
            RestClient.builder()
                    .url("publish_confirm.php")
                    .loader(getContext())
                    .params("uid", mUserId)
                    .params("name", mGoodName)
                    .params("desc", mGoodDesc)
                    .params("rent", mGoodRent)
                    .params("guarantee", mGoodGuarantee)
                    .params("count", mGoodCount)
                    .params("cate", mGoodClassId)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            BrownLogger.d("PublishResp", response);
                            if (Objects.equals(response, "OK")) {
                                Toast.makeText(getContext(),"商品发布成功", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(),"商品发布失败", Toast.LENGTH_LONG).show();
                            }

                        }
                    })
                    .build()
                    .post();
        } else {
            Toast.makeText(getContext(),"商品发布失败", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R2.id.tv_publish_clear)
    void onClickClean() {
        mGoodName = "";
        mGoodDesc = "";
        mGoodRent = "";
        mGoodGuarantee = "";
        mGoodCount = 1;
        mGoodClassId = 0;

        mEtGoodName.setText(mGoodName);
        mEtGoodDesc.setText(mGoodDesc);
        mEtGoodRent.setText(mGoodRent);
        mEtGoodGuarantee.setText(mGoodGuarantee);
        mTvGoodCount.setText(String.valueOf(mGoodCount));
        mTvGoodClass.setText("请选择所属类目");
        mTvGoodClass.setTextColor(Color.GRAY);
    }

    @OnClick(R2.id.icon_count_minus)
    void onClickMinus() {
        if (mGoodCount > 1) {
            mGoodCount = mGoodCount - 1;
            mTvGoodCount.setText(String.valueOf(mGoodCount));
        }
    }

    @OnClick(R2.id.icon_count_plus)
    void onClickPlus() {
        mGoodCount = mGoodCount + 1;
        mTvGoodCount.setText(String.valueOf(mGoodCount));
    }

    @OnClick(R2.id.tv_good_class)
    void onClickCategory() {
        getCategoryDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTvGoodClass.setText(mGoodClass[which]);
                mTvGoodClass.setTextColor(getResources().getColor(R.color.app_main));
                mGoodClassId = which + 1;
                dialog.cancel();
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private void getCategoryDialog(DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setSingleChoiceItems(mGoodClass, 0, listener);
        builder.show();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_publish;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mUserId = getUserId(AccountManager.SignTag.USER_ID.name());
        mGoodCount = Integer.valueOf(mTvGoodCount.getText().toString());
        RestClient.builder()
                .url("publish_category.php")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        BrownLogger.json("CATEGORY", response);
                        ArrayList<String> tempArrayList = new ArrayList<String>();
                        final JSONArray dataArray = JSON.parseObject(response).getJSONArray("data");
                        final int size = dataArray.size();
                        for (int i = 0; i < size; i++) {
                            final JSONObject data = dataArray.getJSONObject(i);
                            final String name = data.getString("name");
                            tempArrayList.add(name);
                        }
                        mGoodClass = tempArrayList.toArray(new String[tempArrayList.size()]);
                    }
                })
                .build()
                .get();
    }
}
