package com.tony.brown.ec.main.personal.address;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tony.brown.delegates.BrownDelegate;
import com.tony.brown.ec.R;
import com.tony.brown.ec.R2;
import com.tony.brown.net.RestClient;
import com.tony.brown.net.callback.ISuccess;
import com.tony.brown.ui.recycler.MultipleItemEntity;
import com.tony.brown.util.log.BrownLogger;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Tony on 2018/1/18.
 */

public class AddressDelegate extends BrownDelegate implements ISuccess {

    @BindView(R2.id.rv_address)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        RestClient.builder()
                .url("address.php")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }


    @Override
    public void onSuccess(String response) {
        BrownLogger.d("AddressDelegate", response);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final List<MultipleItemEntity> data =
                new AddressDataConverter().setJsonData(response).convert();
        final AddressAdapter addressAdapter = new AddressAdapter(data);
        mRecyclerView.setAdapter(addressAdapter);
    }
}
