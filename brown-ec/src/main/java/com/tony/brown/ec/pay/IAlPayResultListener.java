package com.tony.brown.ec.pay;

/**
 * Created by Tony on 2018/1/16.
 */

public interface IAlPayResultListener {

    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();
}
