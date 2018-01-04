package com.tony.brown.ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.tony.brown.delegates.BrownDelegate;
import com.tony.brown.ec.detail.GoodsDetailDelegate;
import com.tony.brown.ui.recycler.MultipleFields;
import com.tony.brown.ui.recycler.MultipleItemEntity;

/**
 * Created by Tony on 2017/12/28.
 */

public class IndexItemClickListener extends SimpleClickListener {

    private final BrownDelegate DELEGATE;

    private IndexItemClickListener(BrownDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(BrownDelegate delegate) {
        return new IndexItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final int goodsId = entity.getField(MultipleFields.ID);
        final GoodsDetailDelegate delegate = GoodsDetailDelegate.create(goodsId);
        DELEGATE.getSupportDelegate().start(delegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
