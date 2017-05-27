package com.china.epower.chat.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.china.epower.chat.R;
import tech.jiangtao.support.kit.callback.IMListenerCollection;
import tech.jiangtao.support.kit.eventbus.IMLoginRequestModel;
import tech.jiangtao.support.kit.manager.IMAccountManager;
import tech.jiangtao.support.kit.model.Account;
import tech.jiangtao.support.kit.model.Result;
import work.wanghao.simplehud.SimpleHUD;

import static java.lang.System.exit;

/**
 * Class: LoginActivity </br>
 * Description: 登录界面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 10/11/2016 3:08 PM</br>
 * Update: 10/11/2016 3:08 PM </br>
 * 登录的功能拿到服务去做，登录成功，在服务器中跳转到主页面
 * 保存用户信息到数据库中
 **/
public class LoginActivity extends BaseActivity implements IMListenerCollection.IMLoginListener {

  @BindView(R.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.login_username) AppCompatEditText mLoginUsername;
  @BindView(R.id.login_password) AppCompatEditText mLoginPassword;
  @BindView(R.id.login_button) AppCompatButton mLoginButton;
  @BindView(R.id.register) AppCompatTextView mRegisterText;
  private IMAccountManager mIMAccountManager;

  public static final String TAG = LoginActivity.class.getSimpleName();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);
    setUpToolbar();
    mIMAccountManager = new IMAccountManager(this);
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public void setUpToolbar() {
    if (mToolbar != null) {
      mToolbar.setTitle("");
      mTvToolbar.setText("登录");
      setSupportActionBar(mToolbar);
      mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
      mToolbar.setNavigationOnClickListener(
          v -> ActivityCompat.finishAfterTransition(LoginActivity.this));
    }
  }

  @OnClick({ R.id.login_button, R.id.register }) public void onClick(View v) {
    switch (v.getId()) {
      case R.id.login_button:
        if (mLoginUsername.getText() == null || mLoginUsername.getText().toString().equals("")) {
          SimpleHUD.showErrorMessage(LoginActivity.this, "用户名不能为空。");
          return;
        }
        if (mLoginPassword.getText() == null || mLoginPassword.getText().toString().equals("")) {
          SimpleHUD.showErrorMessage(LoginActivity.this, "密码不能为空。");
          return;
        }
        SimpleHUD.showLoadingMessage(LoginActivity.this, (String) getText(R.string.profile_loading),
            false);
        mIMAccountManager.login(new IMLoginRequestModel(mLoginUsername.getText().toString(),
            mLoginPassword.getText().toString()), this);
        break;
      case R.id.register:
        RegisterActivity.startRegister(LoginActivity.this);
        break;
    }
  }

  public static void startLogin(Activity activity) {
    Intent intent = new Intent(activity, LoginActivity.class);
    activity.startActivity(intent);
    activity.finish();
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    exit(0);
  }

  @Override public void loginSuccess(Account account) {
    SimpleHUD.dismiss();
    SimpleHUD.showSuccessMessage(LoginActivity.this,
        (String) getText(R.string.connect_success), () -> MainActivity.startMain(LoginActivity.this));
  }

  @Override public void loginFailed(Result result) {
    SimpleHUD.dismiss();
    SimpleHUD.showErrorMessage(LoginActivity.this, getText(R.string.connect_fail) + result.getMsg());
  }
}
