package com.tony.brown.ui.date;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tony.brown.app.AccountManager;
import com.tony.brown.net.RestClient;
import com.tony.brown.net.callback.ISuccess;
import com.tony.brown.util.log.BrownLogger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static com.tony.brown.app.AccountManager.setBirth;
import static com.tony.brown.util.storage.BrownPreference.getUserId;

/**
 * Created by Tony on 2018/1/17.
 */

public class DateDialogUtil {

    public interface IDateListener {

        void onDateChange(String date);
    }

    private IDateListener mDateListener = null;

    public void setDateListener(IDateListener listener) {
        this.mDateListener = listener;
    }

    public void showDialog(final Context context) {
        RestClient.builder()
                .url("profile_birth.php")
                .params("user_id", getUserId(AccountManager.SignTag.USER_ID.name()))
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject data = JSON.parseObject(response).getJSONObject("data");
                        final int year = Integer.valueOf(data.getString("year"));
                        final int month = Integer.valueOf(data.getString("month")) - 1;
                        final int day = Integer.valueOf(data.getString("day"));

                        final LinearLayout ll = new LinearLayout(context);
                        final DatePicker picker = new DatePicker(context);
                        final LinearLayout.LayoutParams lp =
                                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT);

                        picker.setLayoutParams(lp);

                        final String[] date = {""};
                        picker.init(year, month, day, new DatePicker.OnDateChangedListener() {
                            @Override
                            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                final Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                date[0] = format.format(calendar.getTime());
                                if(mDateListener!=null){
                                    mDateListener.onDateChange(date[0]);
                                }
                            }
                        });

                        ll.addView(picker);

                        new AlertDialog.Builder(context)
                                .setTitle("选择日期")
                                .setView(ll)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        RestClient.builder()
                                                .url("profile_birth_change.php")
                                                .params("user_id", getUserId(AccountManager.SignTag.USER_ID.name()))
                                                .params("user_birth", date[0])
                                                .success(new ISuccess() {
                                                    @Override
                                                    public void onSuccess(String response) {
                                                        if (Objects.equals(response, "OK")) {
                                                            setBirth(date[0]);
                                                        }
                                                    }
                                                })
                                                .build()
                                                .get();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    }
                })
                .build()
                .get();
    }
}
