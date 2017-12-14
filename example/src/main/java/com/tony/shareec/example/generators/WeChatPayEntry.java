package com.tony.shareec.example.generators;

import com.tony.brown.annotations.PayEntryGenerator;
import com.tony.brown.wechat.templates.WXPayEntryTemplate;

/**
 * Created by Tony on 2017/12/14.
 */

@SuppressWarnings("unused")
@PayEntryGenerator(
        packageName = "com.tony.shareec.example",
        payEntryTemplate = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
