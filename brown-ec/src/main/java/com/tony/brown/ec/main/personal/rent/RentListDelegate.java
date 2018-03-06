package com.tony.brown.ec.main.personal.rent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tony.brown.app.AccountManager;
import com.tony.brown.delegates.BrownDelegate;
import com.tony.brown.ec.R;
import com.tony.brown.ec.R2;

import butterknife.BindView;

import static com.tony.brown.util.storage.BrownPreference.getUserId;

/**
 * Created by Tony on 2018/3/6.
 */

public class RentListDelegate extends BrownDelegate {

    @BindView(R2.id.rv_rent_list)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_rent_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        long mUserId = getUserId(AccountManager.SignTag.USER_ID.name());
    }
}
