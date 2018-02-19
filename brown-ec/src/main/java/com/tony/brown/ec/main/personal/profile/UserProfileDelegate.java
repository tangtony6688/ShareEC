package com.tony.brown.ec.main.personal.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tony.brown.app.AccountManager;
import com.tony.brown.delegates.BrownDelegate;
import com.tony.brown.ec.R;
import com.tony.brown.ec.R2;
import com.tony.brown.ec.main.personal.list.ListAdapter;
import com.tony.brown.ec.main.personal.list.ListBean;
import com.tony.brown.ec.main.personal.list.ListItemType;
import com.tony.brown.ec.main.personal.settings.NameDelegate;
import com.tony.brown.net.RestClient;
import com.tony.brown.net.callback.ISuccess;
import com.tony.brown.util.log.BrownLogger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.tony.brown.util.storage.BrownPreference.getGender;
import static com.tony.brown.util.storage.BrownPreference.getUserId;
import static com.tony.brown.util.storage.BrownPreference.getUserName;

/**
 * Created by Tony on 2018/1/17.
 */

public class UserProfileDelegate extends BrownDelegate {

    @BindView(R2.id.rv_user_profile)
    RecyclerView mRecyclerView = null;

    final List<ListBean> data = new ArrayList<>();
    private long mUserId = 0;
    private String mImageUrl = "";
    private String mUserName = "";
    private String mGender = "";



    @Override
    public Object setLayout() {
        return R.layout.delegate_user_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        mUserId = getUserId(AccountManager.SignTag.USER_ID.name());

        final ListBean image = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_AVATAR)
                .setId(1)
                .setImageUrl("http://i9.qhimg.com/t017d891ca365ef60b5.jpg")
                .build();

        final ListBean name = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("姓名")
                .setDelegate(new NameDelegate())
                .setValue(getUserName(AccountManager.SignTag.USER_NAME.name()))
                .build();

        final ListBean gender = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setText("性别")
                .setValue(getGender(AccountManager.SignTag.USER_GENDER.name()))
                .build();

        final ListBean birth = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(4)
                .setText("生日")
                .setValue("未设置生日")
                .build();


        data.add(image);
        data.add(name);
        data.add(gender);
        data.add(birth);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new UserProfileClickListener(this));
    }

    private void refresh() {

    }
}
