package com.tony.shareec.example.generators;

import com.tony.brown.annotations.EntryGenerator;
import com.tony.brown.wechat.templates.WXEntryTemplate;

/**
 * Created by Tony on 2017/12/14.
 */

@SuppressWarnings("unused")
@EntryGenerator(
        packageName = "com.tony.shareec.example",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
