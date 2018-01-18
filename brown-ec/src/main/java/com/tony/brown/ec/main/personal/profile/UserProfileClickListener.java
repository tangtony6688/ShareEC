package com.tony.brown.ec.main.personal.profile;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.tony.brown.delegates.BrownDelegate;
import com.tony.brown.ec.R;
import com.tony.brown.ec.main.personal.list.ListBean;
import com.tony.brown.net.RestClient;
import com.tony.brown.net.callback.ISuccess;
import com.tony.brown.ui.date.DateDialogUtil;
import com.tony.brown.util.callback.CallbackManager;
import com.tony.brown.util.callback.CallbackType;
import com.tony.brown.util.callback.IGlobalCallback;
import com.tony.brown.util.log.BrownLogger;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Tony on 2018/1/17.
 */

public class UserProfileClickListener extends SimpleClickListener {

    private final UserProfileDelegate DELEGATE;

    private String[] mGenders = new String[]{"男", "女", "保密"};

    UserProfileClickListener(UserProfileDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getId();
        switch (id) {
            case 1:
                //开始照相机或选择图片
                CallbackManager.getInstance()
                        .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                            @Override
                            public void executeCallback(@Nullable Uri args) {
                                BrownLogger.d("ON_CROP", args);
                                final CircleImageView avatar = view.findViewById(R.id.img_arrow_avatar);
                                Glide.with(DELEGATE)
                                        .load(args)
                                        .into(avatar);

//                                RestClient.builder()
//                                        .url("服务器上传地址")
//                                        .loader(DELEGATE.getContext())
//                                        .file(args.getPath())
//                                        .success(new ISuccess() {
//                                            @Override
//                                            public void onSuccess(String response) {
//                                                BrownLogger.d("ON_CROP_UPLOAD", response);
////                                                final String path = JSON.parseObject(response).getJSONObject("result")
////                                                        .getString("path");
////
////                                                //通知服务器更新信息
////                                                RestClient.builder()
////                                                        .url("user_profile.php")
////                                                        .params("avatar", path)
////                                                        .loader(DELEGATE.getContext())
////                                                        .success(new ISuccess() {
////                                                            @Override
////                                                            public void onSuccess(String response) {
////                                                                //获取更新后的用户信息，然后更新本地数据库
////                                                                //没有本地数据的APP，每次打开APP都请求API，获取信息
////                                                            }
////                                                        })
////                                                        .build()
////                                                        .post();
//                                            }
//                                        })
//                                        .build()
//                                        .upload();
                            }
                        });
                DELEGATE.startCameraWithCheck();
                break;
            case 2:
                //设置姓名
                final BrownDelegate nameDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().start(nameDelegate);
                break;
            case 3:
                //设置性别
                getGenderDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final AppCompatTextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(mGenders[which]);
                        dialog.cancel();
                    }
                });
                break;
            case 4:
                //设置生日
                final DateDialogUtil dateDialogUtil = new DateDialogUtil();
                dateDialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDateChange(String date) {
                        final AppCompatTextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(date);
                    }
                });
                dateDialogUtil.showDialog(DELEGATE.getContext());
                break;
            default:
                break;
        }
    }

    private void getGenderDialog(DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
        builder.setSingleChoiceItems(mGenders, 2, listener);
        builder.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
