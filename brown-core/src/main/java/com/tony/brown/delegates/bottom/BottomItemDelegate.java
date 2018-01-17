package com.tony.brown.delegates.bottom;

import android.widget.Toast;

import com.tony.brown.R;
import com.tony.brown.app.Brown;
import com.tony.brown.delegates.BrownDelegate;

/**
 * Created by Tony on 2017/12/15.
 */

public abstract class BottomItemDelegate extends BrownDelegate {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出" + Brown.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
