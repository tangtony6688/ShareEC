package com.tony.shareec.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tony.brown.delegates.BrownDelegate;
import com.tony.brown.net.RestClient;
import com.tony.brown.net.callback.IError;
import com.tony.brown.net.callback.IFailure;
import com.tony.brown.net.callback.ISuccess;

/**
 * Created by Tony on 2017/12/10.
 */

public class ExampleDelegate extends BrownDelegate {
    @Override
    public Object setLayout() {
        return com.tony.shareec.example.R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        testRestClient();
    }

    private void testRestClient() {
        RestClient.builder()
                .url("http://127.0.0.1/test")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("NIUBILITY", response);
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}
