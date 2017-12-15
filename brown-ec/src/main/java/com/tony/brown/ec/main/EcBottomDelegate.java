package com.tony.brown.ec.main;

import android.graphics.Color;

import com.tony.brown.delegates.bottom.BaseBottomDelegate;
import com.tony.brown.delegates.bottom.BottomItemDelegate;
import com.tony.brown.delegates.bottom.BottomTabBean;
import com.tony.brown.delegates.bottom.ItemBuilder;
import com.tony.brown.ec.main.cart.ShopCartDelegate;
import com.tony.brown.ec.main.index.IndexDelegate;
import com.tony.brown.ec.main.personal.PersonalDelegate;
import com.tony.brown.ec.main.publish.PublishDelegate;
import com.tony.brown.ec.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 * Created by Tony on 2017/12/15.
 */

public class EcBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "首页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-bars}", "分类"), new SortDelegate());
        items.put(new BottomTabBean("{fa-plus-circle}", "发布"), new PublishDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new ShopCartDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new PersonalDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ad2f6c");
    }
}
