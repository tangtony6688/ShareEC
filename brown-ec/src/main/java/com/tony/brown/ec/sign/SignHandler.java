package com.tony.brown.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tony.brown.app.AccountManager;
import com.tony.brown.ec.database.DatabaseManager;
import com.tony.brown.ec.database.UserProfile;

import java.util.Objects;

/**
 * Created by Tony on 2017/12/13.
 */

public class SignHandler {

    public static String onSignUp(String response, ISignListener signListener) {
        final String message = JSON.parseObject(response).getString("message");
        if (Objects.equals(message, "OK")) {
            final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
            final long userId = profileJson.getLong("userId");
            final String name = profileJson.getString("name");
            final String avatar = profileJson.getString("avatar");
            final String gender = profileJson.getString("gender");
            final String address = profileJson.getString("address");

            final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
            DatabaseManager.getInstance().getDao().insertOrReplace(profile);

            //已经注册并登录成功了
            AccountManager.setSignState(true);
            signListener.onSignUpSuccess();
        }
        return message;
    }

    public static String onSignIn(String response, ISignListener signListener) {
        final String message = JSON.parseObject(response).getString("message");
        if (Objects.equals(message, "OK")) {
            final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
            final long userId = profileJson.getLong("userId");
            final String name = profileJson.getString("name");
            final String avatar = profileJson.getString("avatar");
            final String gender = profileJson.getString("gender");
            final String address = profileJson.getString("address");

            final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
            DatabaseManager.getInstance().getDao().insertOrReplace(profile);

            //已经注册并登录成功了
            AccountManager.setSignState(true);
            signListener.onSignInSuccess();
        }
        return message;
    }
}
