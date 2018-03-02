package com.tony.brown.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tony.brown.app.AccountManager;
import com.tony.brown.delegates.bottom.BottomItemDelegate;
import com.tony.brown.ec.R;
import com.tony.brown.ec.R2;
import com.tony.brown.ec.main.personal.address.AddressDelegate;
import com.tony.brown.ec.main.personal.list.ListAdapter;
import com.tony.brown.ec.main.personal.list.ListBean;
import com.tony.brown.ec.main.personal.list.ListItemType;
import com.tony.brown.ec.main.personal.order.OrderListDelegate;
import com.tony.brown.ec.main.personal.profile.UserProfileDelegate;
import com.tony.brown.ec.main.personal.settings.SettingsDelegate;
import com.tony.brown.net.RestClient;
import com.tony.brown.net.callback.ISuccess;
import com.tony.brown.util.log.BrownLogger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tony.brown.util.storage.BrownPreference.getUserId;
import static com.tony.brown.util.storage.BrownPreference.getUserName;

/**
 * Created by Tony on 2017/12/15.
 */

public class PersonalDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRvSettings = null;
    @BindView(R2.id.tv_user_name)
    AppCompatTextView mTvUserName = null;

    public static final String ORDER_TYPE = "ORDER_TYPE";
    private Bundle mArgs = null;

    private long mUserId = 0;

    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    @OnClick(R2.id.tv_all_order)
    void onClickAllOrder() {
        mArgs.putString(ORDER_TYPE, "all");
        startOrderListByType();
    }


    @OnClick(R2.id.img_user_avatar)
    void onClickAvatar() {
        getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
    }
    private void startOrderListByType() {
        final OrderListDelegate delegate = new OrderListDelegate();
        delegate.setArguments(mArgs);
        getParentDelegate().getSupportDelegate().start(delegate);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
        mUserId = getUserId(AccountManager.SignTag.USER_ID.name());
        onUserNameClick();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        final ListBean address = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setDelegate(new AddressDelegate())
                .setText("收货地址")
                .build();

        final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setDelegate(new SettingsDelegate())
                .setText("系统设置")
                .build();

        final ListBean signOut = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_TEXT)
                .setId(3)
                .setText("注销")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(address);
        data.add(system);
        data.add(signOut);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvSettings.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRvSettings.setAdapter(adapter);
        mRvSettings.addOnItemTouchListener(new PersonalClickListener(this));
    }

    @OnClick(R2.id.tv_user_name)
    void onUserNameClick() {
        RestClient.builder()
                .url("profile_name.php")
                .params("user_id", mUserId)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mTvUserName.setText(response);
                    }
                })
                .build()
                .get();
    }
}
