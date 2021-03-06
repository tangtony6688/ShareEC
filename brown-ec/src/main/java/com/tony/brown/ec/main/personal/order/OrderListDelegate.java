package com.tony.brown.ec.main.personal.order;

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
import com.tony.brown.ec.main.personal.PersonalDelegate;
import com.tony.brown.net.RestClient;
import com.tony.brown.net.callback.ISuccess;
import com.tony.brown.ui.recycler.MultipleItemEntity;

import java.util.List;

import butterknife.BindView;

import static com.tony.brown.util.storage.BrownPreference.getUserId;

/**
 * Created by Tony on 2018/1/17.
 */

public class OrderListDelegate extends BrownDelegate {

    private String mType = null;
    private long mUserId = 0;

    @BindView(R2.id.rv_order_list)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = getUserId(AccountManager.SignTag.USER_ID.name());
        final Bundle args = getArguments();
        if (args != null) {
            mType = args.getString(PersonalDelegate.ORDER_TYPE);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .loader(getContext())
                .url("order_list.php")
                .params("uId", mUserId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        final List<MultipleItemEntity> data =
                                new OrderListDataConverter().setJsonData(response).convert();
                        final OrderListAdapter adapter = new OrderListAdapter(data);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.addOnItemTouchListener(new OrderListClickListener(OrderListDelegate.this));
                    }
                })
                .build()
                .get();
    }
}
