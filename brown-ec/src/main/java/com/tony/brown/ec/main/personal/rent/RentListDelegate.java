package com.tony.brown.ec.main.personal.rent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tony.brown.app.AccountManager;
import com.tony.brown.delegates.BrownDelegate;
import com.tony.brown.ec.R;
import com.tony.brown.ec.R2;
import com.tony.brown.ec.main.personal.publishgood.PublishListAdapter;
import com.tony.brown.ec.main.personal.publishgood.PublishListDataConverter;
import com.tony.brown.net.RestClient;
import com.tony.brown.net.callback.ISuccess;
import com.tony.brown.ui.recycler.MultipleItemEntity;

import java.util.List;

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
        RestClient.builder()
                .url("rent_list.php")
                .loader(getContext())
                .params("uId", mUserId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        final List<MultipleItemEntity> data =
                                new PublishListDataConverter().setJsonData(response).convert();
                        final PublishListAdapter adapter = new PublishListAdapter(data);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.addOnItemTouchListener(new RentListClickListener(RentListDelegate.this));

                    }
                })
                .build()
                .get();
    }
}
