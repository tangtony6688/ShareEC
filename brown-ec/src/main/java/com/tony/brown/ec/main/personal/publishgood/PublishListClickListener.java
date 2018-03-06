package com.tony.brown.ec.main.personal.publishgood;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.tony.brown.ec.R;
import com.tony.brown.ec.detail.GoodsDetailDelegate;
import com.tony.brown.net.RestClient;
import com.tony.brown.net.callback.ISuccess;
import com.tony.brown.ui.recycler.MultipleFields;
import com.tony.brown.ui.recycler.MultipleItemEntity;
import com.tony.brown.util.log.BrownLogger;

import java.util.Objects;

/**
 * Created by Tony on 2018/3/5.
 */

public class PublishListClickListener extends SimpleClickListener {

    private final PublishListDelegate DELEGATE;

    PublishListClickListener(PublishListDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final int goodsId = entity.getField(MultipleFields.ID);
        final GoodsDetailDelegate delegate = GoodsDetailDelegate.create(goodsId);
        DELEGATE.getSupportDelegate().start(delegate);
    }

    @Override
    public void onItemLongClick(final BaseQuickAdapter adapter, View view, final int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final int goodsId = entity.getField(MultipleFields.ID);
        AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
        builder.setTitle("删除商品")
                .setIcon(R.drawable.delete)
                .setMessage("是否删除该商品？")
                .setPositiveButton("心意已决", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RestClient.builder()
                                .url("delete_product.php")
                                .params("pid", goodsId)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        BrownLogger.d("DELResp", response);
                                        if (Objects.equals(response, "OK")) {
                                            adapter.remove(position);
                                        }
                                    }
                                })
                                .build()
                                .get();
                    }
                })
                .setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
