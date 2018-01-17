package com.tony.brown.ec.main.personal.list;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tony.brown.delegates.BrownDelegate;

/**
 * Created by Tony on 2018/1/17.
 */

public class ListBean implements MultiItemEntity {

    private int mItemType = 0;
    private String mImageUrl = null;
    private String mText = null;
    private String mValue = null;
    private int mId = 0;
    private BrownDelegate mDelegate = null;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = null;

    ListBean(int mItemType, String mImageUrl, String mText, String mValue, int mId, BrownDelegate mDelegate, CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener) {
        this.mItemType = mItemType;
        this.mImageUrl = mImageUrl;
        this.mText = mText;
        this.mValue = mValue;
        this.mId = mId;
        this.mDelegate = mDelegate;
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getText() {
        if (mText == null) {
            return "";
        }
        return mText;
    }

    public String getValue() {
        if (mValue == null) {
            return "";
        }
        return mValue;
    }

    public int getId() {
        return mId;
    }

    public BrownDelegate getDelegate() {
        return mDelegate;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static final class Builder {

        private int id = 0;
        private int itemType = 0;
        private String imageUrl = null;
        private String text = null;
        private String value = null;
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = null;
        private BrownDelegate delegate = null;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            this.onCheckedChangeListener = onCheckedChangeListener;
            return this;
        }

        public Builder setDelegate(BrownDelegate delegate) {
            this.delegate = delegate;
            return this;
        }

        public ListBean build() {
            return new ListBean(itemType, imageUrl, text, value, id, delegate, onCheckedChangeListener);
        }
    }
}
