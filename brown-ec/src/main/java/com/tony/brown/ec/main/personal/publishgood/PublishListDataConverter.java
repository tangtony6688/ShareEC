package com.tony.brown.ec.main.personal.publishgood;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tony.brown.ui.recycler.DataConverter;
import com.tony.brown.ui.recycler.MultipleFields;
import com.tony.brown.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by Tony on 2018/3/5.
 */

public class PublishListDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = array.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String name = data.getString("name");
            final int id = data.getInteger("id");
            final double price = data.getDouble("price");
            final String time = data.getString("time");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(PublishListItemType.ITEM_PUBLISH_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.IMAGE_URL, thumb)
                    .setField(MultipleFields.TITLE, name)
                    .setField(PublishItemFields.PRICE, price)
                    .setField(PublishItemFields.TIME, time)
                    .build();

            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
