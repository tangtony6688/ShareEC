package com.tony.brown.ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.tony.brown.delegates.bottom.BottomItemDelegate;
import com.tony.brown.ec.R;
import com.tony.brown.ec.R2;
import com.tony.brown.ec.main.EcBottomDelegate;
import com.tony.brown.net.RestClient;
import com.tony.brown.net.callback.ISuccess;
import com.tony.brown.ui.recycler.MultipleFields;
import com.tony.brown.ui.recycler.MultipleItemEntity;
import com.tony.brown.util.log.BrownLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Tony on 2017/12/15.
 */

public class ShopCartDelegate extends BottomItemDelegate implements ISuccess, ICartItemListener {

    private ShopCartAdapter mAdapter = null;
    private double mTotalPrice = 0.00;

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelectAll = null;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTvTotalPrice = null;
    @BindView(R2.id.stub_no_item)
    ViewStubCompat mStubNoItem = null;

    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectAll() {
        final int tag = (int) mIconSelectAll.getTag();
        if (tag == 0) {
            mIconSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
            mIconSelectAll.setTag(1);
            mAdapter.setIsSelectedAll(true);
            //更新RecyclerView的显示状态
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        }
    }

    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    void onClickRemoveSelectedItem() {
        if (mAdapter.getItemCount() != 0) {
            //取出购物车数据，并初始化数据序号
            List<MultipleItemEntity> data = mAdapter.getData();
            int i = 0;
            for (MultipleItemEntity item : data) {
                item.setField(ShopCartItemFields.POSITION, i);
                i++;
            }

            //循环取出要删除的entity
            final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
            for (MultipleItemEntity entity : data) {
                final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                if (isSelected) {
                    deleteEntities.add(entity);
                }
            }

            //循环删除
            for (MultipleItemEntity entity : deleteEntities) {
                final int removePosition = entity.getField(ShopCartItemFields.POSITION);
                final double removePrice = entity.getField(ShopCartItemFields.PRICE);
                final int removeCount = entity.getField(ShopCartItemFields.COUNT);

                //更新其他待删除数据的序号
                for (MultipleItemEntity entity1 : deleteEntities) {
                    int curPos = entity1.getField(ShopCartItemFields.POSITION);
                    entity1.setField(ShopCartItemFields.POSITION, curPos - 1);
                }
                if (removePosition <= mAdapter.getItemCount()) {
                    mAdapter.remove(removePosition);
                    mAdapter.notifyItemRangeChanged(removePosition, mAdapter.getItemCount());
                    mTotalPrice -= removePrice * removeCount;
                    mTvTotalPrice.setText(String.valueOf(mTotalPrice));
                }
            }
            checkItemCount();
        }
    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear() {
        if (mAdapter.getItemCount() != 0) {
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
            mTotalPrice = 0.00;
            mTvTotalPrice.setText(String.valueOf(mTotalPrice));
            checkItemCount();
        }
    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay(){
//        createOrder();
    }

    //创建订单，注意，和支付是没有关系的
    private void createOrder(){
        final String orderUrl = "你的生成订单的API";
        final WeakHashMap<String, Object> orderParams = new WeakHashMap<>();
        //加入参数
//        orderParams.put("userid","");
        RestClient.builder()
                .url(orderUrl)
                .loader(getContext())
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //进行具体的支付
                    }
                })
                .build()
                .post();

    }

    @SuppressWarnings("RestrictedApi")
    private void checkItemCount() {
        final int count = mAdapter.getItemCount();
        if (count == 0) {
            final View stubView = mStubNoItem.inflate();
//            final AppCompatTextView tvToBuy = stubView.findViewById(R.id.tv_stub_to_buy);
//            tvToBuy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //返回首页
//                    final int indexTab = 0;
//                    final EcBottomDelegate ecBottomDelegate = getParentDelegate();
//                    final BottomItemDelegate indexDelegate = ecBottomDelegate.getItemDelegates().get(indexTab);
//                    ecBottomDelegate
//                            .getSupportDelegate()
//                            .showHideFragment(indexDelegate, ShopCartDelegate.this);
//                    ecBottomDelegate.changeColor(indexTab);
//                }
//            });
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mIconSelectAll.setTag(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("shop_cart.php")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> data =
                new ShopCartDataConverter()
                        .setJsonData(response)
                        .convert();
        mAdapter = new ShopCartAdapter(data);
        mAdapter.setCartItemListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mTotalPrice = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(mTotalPrice));
        checkItemCount();
    }

    @Override
    public void onItemClick(double itemTotalPrice) {
        final double price = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(price));
    }
}
