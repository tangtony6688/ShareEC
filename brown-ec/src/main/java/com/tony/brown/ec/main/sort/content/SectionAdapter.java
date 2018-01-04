package com.tony.brown.ec.main.sort.content;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tony.brown.ec.R;
import com.tony.brown.ec.detail.GoodsDetailDelegate;
import com.tony.brown.ec.main.EcBottomDelegate;
import com.tony.brown.ec.main.sort.SortDelegate;
import com.tony.brown.util.log.BrownLogger;

import java.util.List;

/**
 * Created by Tony on 2017/12/28.
 */

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {

    private final SortDelegate DELEGATE;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data, SortDelegate delegate) {
        super(layoutResId, sectionHeadResId, data);
        this.DELEGATE = delegate;
    }

    @Override
    protected void convertHead(BaseViewHolder holder, SectionBean item) {
        holder.setText(R.id.header, item.header);
        holder.setVisible(R.id.more, item.isMore());
        holder.addOnClickListener(R.id.more);
    }

    @Override
    protected void convert(BaseViewHolder holder, SectionBean item) {
        //item.t返回SectionBean类型
        final String preImageUrl = "http://172.19.128.209:8080/~Tony/BrownServer/data/img/";
        final String thumb = item.t.getGoodsThumb();
        final String name = item.t.getGoodsName();
        final int goodsId = item.t.getGoodsId();
        final SectionContentItemEntity entity = item.t;
        holder.setText(R.id.tv, name);
        final AppCompatImageView goodsImageView = holder.getView(R.id.iv);
        Glide.with(mContext)
                .load(preImageUrl + thumb)
                .into(goodsImageView);

        //点击事件
        final View itemView = holder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BrownLogger.d("CLICKED", goodsId);
                toGoodsDetail(goodsId);
            }
        });
    }

    private void toGoodsDetail(int goodsId) {
        final EcBottomDelegate ecBottomDelegate = DELEGATE.getParentDelegate();
        final GoodsDetailDelegate delegate = GoodsDetailDelegate.create(goodsId);
        ecBottomDelegate.getSupportDelegate().start(delegate);
    }
}
