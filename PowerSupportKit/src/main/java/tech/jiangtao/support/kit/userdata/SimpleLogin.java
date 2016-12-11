package tech.jiangtao.support.kit.userdata;

import android.util.Log;
import java.io.IOException;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.callback.ConnectionCallback;
import tech.jiangtao.support.kit.callback.LoginCallBack;
import tech.jiangtao.support.kit.init.SupportIM;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.util.ErrorAction;

/**
 * Class: SimpleLogin </br>
 * Description: 简化登录操作 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 02/12/2016 8:54 AM</br>
 * Update: 02/12/2016 8:54 AM </br>
 **/

public class SimpleLogin implements ConnectionListener {

  public static final String TAG = SimpleLogin.class.getSimpleName();
  private Observable<AbstractXMPPConnection> mConnectObservable;
  private Subscriber<AbstractXMPPConnection> mConnectSubscriber;
  private XMPPTCPConnection mXMPPTCPConnection;

  private LoginCallBack mLoginCallBack;
  private ConnectionCallback mConnectionCallback;

  public SimpleLogin(String username, String password) {
    init(username, password);
    mConnectObservable = Observable.create(new Observable.OnSubscribe<AbstractXMPPConnection>() {
      @Override public void call(Subscriber<? super AbstractXMPPConnection> subscriber) {
        try {
          subscriber.onNext(mXMPPTCPConnection.connect());
        } catch (SmackException | IOException | XMPPException e) {
          subscriber.onError(new Throwable(e.toString()));
          e.printStackTrace();
        }
      }
    });
    mConnectSubscriber = new Subscriber<AbstractXMPPConnection>() {
      @Override public void onCompleted() {
        Log.d(TAG, "onCompleted: ");
      }

      @Override public void onError(Throwable e) {
        Log.d(TAG, "onError: " + e.toString());
        mLoginCallBack.connectionFailed(new Exception(e));
      }

      @Override public void onNext(AbstractXMPPConnection abstractXMPPConnection) {
        Log.d("------>", "onNext: ");
        Observable.create(subscriber2 -> {
          try {
            abstractXMPPConnection.login();
            subscriber2.onCompleted();
          } catch (XMPPException | SmackException | IOException e) {
            e.printStackTrace();
          }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {

        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
            mLoginCallBack.connectionFailed(new Exception(throwable));
          }
        }, () -> {
          mLoginCallBack.connectSuccess(abstractXMPPConnection);
          mLoginCallBack.connectSuccess(abstractXMPPConnection);
          mConnectionCallback.connection(abstractXMPPConnection);
        });
      }
    };
  }

  public void init(String username, String password) {
    XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
    configBuilder.setUsernameAndPassword(username, password);
    configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
    configBuilder.setDebuggerEnabled(true);
    configBuilder.setResource(SupportIM.mResource);
    configBuilder.setServiceName(SupportIM.mDomain);
    configBuilder.setHost(SupportIM.mHost);
    configBuilder.setPort(SupportIM.mPort);
    mXMPPTCPConnection = new XMPPTCPConnection(configBuilder.build());
    mXMPPTCPConnection.setPacketReplyTimeout(20000);
    mXMPPTCPConnection.addConnectionListener(this);
  }

  public void login() {
    mConnectObservable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(mConnectSubscriber);
    Log.d("----->", "login: 开始订阅数据");
  }

  public void setLoginCallBack(LoginCallBack callback) {
    mLoginCallBack = callback;
  }

  private void setConnectionCallback(ConnectionCallback callback) {
    mConnectionCallback = callback;
  }

  @Override public void connected(XMPPConnection connection) {
    mXMPPTCPConnection = (XMPPTCPConnection) connection;
    Log.d(TAG, "connected: ");
  }

  @Override public void authenticated(XMPPConnection connection, boolean resumed) {
    Log.d(TAG, "authenticated: ");
  }

  @Override public void connectionClosed() {
    Log.d(TAG, "connectionClosed: ");
  }

  @Override public void connectionClosedOnError(Exception e) {
    Log.d(TAG, "connectionClosedOnError: ");
  }

  @Override public void reconnectionSuccessful() {
    Log.d(TAG, "reconnectionSuccessful: ");
  }

  @Override public void reconnectingIn(int seconds) {
    Log.d(TAG, "reconnectingIn: ");
  }

  @Override public void reconnectionFailed(Exception e) {
    Log.d(TAG, "reconnectionFailed: ");
  }
}
