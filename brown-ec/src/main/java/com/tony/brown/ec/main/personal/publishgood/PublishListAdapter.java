package com.tony.brown.ec.main.personal.publishgood;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tony.brown.ec.R;
import com.tony.brown.net.ApiHost;
import com.tony.brown.ui.recycler.MultipleFields;
import com.tony.brown.ui.recycler.MultipleItemEntity;
import com.tony.brown.ui.recycler.MultipleRecyclerAdapter;
import com.tony.brown.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by Tony on 2018/3/5.
 */

public class PublishListAdapter extends MultipleRecyclerAdapter {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    public PublishListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(PublishListItemType.ITEM_PUBLISH_LIST, R.layout.item_order_list);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case PublishListItemType.ITEM_PUBLISH_LIST:
                final AppCompatImageView imageView = holder.getView(R.id.image_order_list);
                final AppCompatTextView title = holder.getView(R.id.tv_order_list_title);
                final AppCompatTextView price = holder.getView(R.id.tv_order_list_price);
                final AppCompatTextView time = holder.getView(R.id.tv_order_list_time);

                final String titleVal = entity.getField(MultipleFields.TITLE);
                final String timeVal = entity.getField(PublishItemFields.TIME);
                final double priceVal = entity.getField(PublishItemFields.PRICE);
                final String imageUrl = ApiHost.IMG_API_HOST + entity.getField(MultipleFields.IMAGE_URL);

                final String[] stringArray = timeVal.split(" ");

                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into(imageView);

                title.setText(titleVal);
                price.setText("价格：￥" + String.valueOf(priceVal) + "/天");
                time.setText("时间：" + stringArray[0]);
                break;
            default:
                break;
        }
    }
}
