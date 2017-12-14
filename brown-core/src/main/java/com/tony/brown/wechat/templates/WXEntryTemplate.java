package com.tony.brown.wechat.templates;

import com.tony.brown.wechat.BaseWXEntryActivity;
import com.tony.brown.wechat.BrownWeChat;

/**
 * Created by Tony on 2017/12/14.
 */

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        BrownWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }
}
