package com.tony.brown.ec.main.publish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tony.brown.delegates.bottom.BottomItemDelegate;
import com.tony.brown.ec.R;

/**
 * Created by Tony on 2017/12/15.
 */

public class PublishDelegate extends BottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_publish;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
