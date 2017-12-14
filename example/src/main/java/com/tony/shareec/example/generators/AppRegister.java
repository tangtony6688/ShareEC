package com.tony.shareec.example.generators;

import com.tony.brown.annotations.AppRegisterGenerator;
import com.tony.brown.wechat.templates.AppRegisterTemplate;

/**
 * Created by Tony on 2017/12/14.
 */

@SuppressWarnings("unused")
@AppRegisterGenerator(
        packageName = "com.tony.shareec.example",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegister {
}
