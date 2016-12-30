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
import com.cocosw.favor.FavorAdapter;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.iqregister.AccountManager;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.callback.ConnectionCallback;
import tech.jiangtao.support.kit.realm.sharepreference.Account;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.util.ErrorAction;
import work.wanghao.simplehud.SimpleHUD;

public class RegisterActivity extends BaseActivity {

  @BindView(R.id.register_username) AppCompatEditText mRegisterUsername;
  @BindView(R.id.register_password) AppCompatEditText mRegisterPassword;
  @BindView(R.id.register_retry_password) AppCompatEditText mRegisterRetryPassword;
  @BindView(R.id.register_button) AppCompatButton mRegisterButton;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    setUpToolbar();
  }

  public  void setUpToolbar(){
    getTitleTextView().setText("注 册");
  }

  public void register(String username,String password){
    AccountManager manager = AccountManager.getInstance(SupportService.getmXMPPConnection());
    Observable.create(new Observable.OnSubscribe<Object>() {
      @Override public void call(Subscriber<? super Object> subscriber) {
        try {
          manager.createAccount(username,password);
          subscriber.onCompleted();
        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
          subscriber.onError(e);
          e.printStackTrace();
        }
      }
    }).subscribeOn(Schedulers.io()).doOnSubscribe(()->SimpleHUD.showLoadingMessage(RegisterActivity.this,"正在注册...",false)).observeOn(
        AndroidSchedulers.mainThread()).subscribe(o -> {

    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
        SimpleHUD.dismiss();
        SimpleHUD.showErrorMessage(RegisterActivity.this,"注册失败"+throwable.toString());
      }
    }, new Action0() {
      @Override public void call() {
//        SupportService.login(username, password, new ConnectionCallback() {
//          @Override public void connection(XMPPConnection connection) {
//            SimpleHUD.dismiss();
//            SimpleHUD.showSuccessMessage(RegisterActivity.this,"注册成功");
//            saveSharePreference(username,password);
//            MainActivity.startMain(RegisterActivity.this);
//          }
//
//          @Override public void connectionFailed(Exception e) {
//            SimpleHUD.dismiss();
//            SimpleHUD.showErrorMessage(RegisterActivity.this,"登录失败");
//          }
//        });
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

  public void saveSharePreference(String name, String passwd) {
    Account account = new FavorAdapter.Builder(this).build().create(Account.class);
    account.setPassword(passwd);
    account.setUserName(name);
  }
}
