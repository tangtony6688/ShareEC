package com.tony.brown.app;

import com.tony.brown.util.storage.BrownPreference;

/**
 * Created by Tony on 2017/12/13.
 */

public class AccountManager {

    public enum SignTag {
        SIGN_TAG,
        USER_ID,
        USER_NAME,
        USER_AVATAR,
        USER_GENDER,
        USER_BIRTH,
        USER_MONEY
    }

    //保存用户登录状态，登录后调用
    public static void setSignState(boolean state) {
        BrownPreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    public static void setUserId(long id) {
        BrownPreference.setUserId(SignTag.USER_ID.name(), id);
    }

    public static void setUserName(String name) {
        BrownPreference.setUserName(SignTag.USER_NAME.name(), name);
    }

    public static void setGender(String gender) {
        BrownPreference.setGender(SignTag.USER_GENDER.name(), gender);
    }

    public static void setBirth(String birth) {
        BrownPreference.setBirth(SignTag.USER_BIRTH.name(), birth);
    }

    public static void setMoney(int money) {
        BrownPreference.setMoney(SignTag.USER_MONEY.name(), money);
    }

    private static boolean isSignIn() {
        return BrownPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }
}
