package com.tony.brown.ec.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;

import com.tony.brown.delegates.BrownDelegate;
import com.tony.brown.ec.R;
import com.tony.brown.ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Tony on 2017/12/13.
 */

public class SignInDelegate extends BrownDelegate {

    @BindView(R2.id.edit_sign_in_name_email_phone)
    TextInputEditText mNameEmailPhone = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkForm() != null) {
            String form = checkForm();

        }
    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWechat() {

    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink() {
        getSupportDelegate().start(new SignUpDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    private String checkForm() {
        final String nameEmailPhone = mNameEmailPhone.getText().toString();
        final String password = mPassword.getText().toString();

        String form = null;
        boolean isPass = true;

        if (nameEmailPhone.isEmpty()) {
            isPass = false;
            mNameEmailPhone.setError("请输入姓名/邮箱/手机");
        } else if (Patterns.EMAIL_ADDRESS.matcher(nameEmailPhone).matches()) {
            form = "FORM_EMAIL";
            mNameEmailPhone.setError(null);
        } else if (checkCellphone(nameEmailPhone)) {
            form = "FORM_PHONE";
            mNameEmailPhone.setError(null);
        } else {
            form = "FORM_NAME";
            mNameEmailPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (isPass) {
            return form;
        } else {
            return null;
        }
    }

    /**
     * 验证手机号码
     * <p>
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *
     * @param cellphone
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
        return cellphone.matches(regex);
    }
}

