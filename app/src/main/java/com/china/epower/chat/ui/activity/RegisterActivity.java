package com.china.epower.chat.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.china.epower.chat.R;
import tech.jiangtao.support.kit.callback.RegisterCallBack;
import tech.jiangtao.support.kit.eventbus.RegisterAccount;
import tech.jiangtao.support.kit.userdata.SimpleRegister;
import work.wanghao.simplehud.SimpleHUD;

public class RegisterActivity extends BaseActivity {

  @BindView(R.id.register_username) AppCompatEditText mRegisterUsername;
  @BindView(R.id.register_password) AppCompatEditText mRegisterPassword;
  @BindView(R.id.register_retry_password) AppCompatEditText mRegisterRetryPassword;
  @BindView(R.id.register_button) AppCompatButton mRegisterButton;
  private SimpleRegister mSimpleRegister;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    setUpToolbar();
  }

  public  void setUpToolbar(){
    getTitleTextView().setText("注 册");
  }

  public void register(String username,String password){
    //注册
    mSimpleRegister = new SimpleRegister();
    SimpleHUD.showLoadingMessage(this,"正在注册",false);
    mSimpleRegister.startRegister(new RegisterAccount(username, password), new RegisterCallBack() {
      @Override public void success(RegisterAccount account) {
        //注册成功
        SimpleHUD.dismiss();
        SimpleHUD.showSuccessMessage(RegisterActivity.this,"注册成功");
        MainActivity.startMain(RegisterActivity.this);
      }

      @Override public void error(String reason) {
        //注册失败
        SimpleHUD.dismiss();
        SimpleHUD.showErrorMessage(RegisterActivity.this,"注册失败");
      }
    });
  }

  @OnClick(R.id.register_button)
  public void onClick(View v){
    String username = mRegisterUsername.getText().toString();
    String password = mRegisterPassword.getText().toString();
    String retryPasswd = mRegisterRetryPassword.getText().toString();
    if (username.equals("")){
      SimpleHUD.showErrorMessage(RegisterActivity.this,"用户名不能为空");
      return;
    }
    if (password.equals("")){
      SimpleHUD.showErrorMessage(RegisterActivity.this,"密码不能为空");
      return;
    }
    if (!(retryPasswd.equals(password))){
      SimpleHUD.showErrorMessage(RegisterActivity.this,"请确认密码输入是否正确");
      return;
    }
    register(username,password);
  }

  @Override protected boolean preSetupToolbar() {
    return true;
  }

  public static void startRegister(Activity activity) {
    activity.startActivity(new Intent(activity, RegisterActivity.class));
  }
}
