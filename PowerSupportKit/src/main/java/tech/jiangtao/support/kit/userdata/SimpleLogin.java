package tech.jiangtao.support.kit.userdata;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
import tech.jiangtao.support.kit.eventbus.LoginCallbackEvent;
import tech.jiangtao.support.kit.eventbus.LoginParam;
import tech.jiangtao.support.kit.eventbus.TextMessage;
import tech.jiangtao.support.kit.init.SupportIM;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.util.ErrorAction;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: SimpleLogin </br>
 * Description: 简化登录操作 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 02/12/2016 8:54 AM</br>
 * Update: 02/12/2016 8:54 AM </br>
 **/

public class SimpleLogin {

    public static final String TAG = SimpleLogin.class.getSimpleName();

    public LoginCallBack mLoginCallBack;

    public SimpleLogin(){
        if (!HermesEventBus.getDefault().isRegistered(this))
        HermesEventBus.getDefault().register(this);
    }

    public void startLogin(LoginParam param,LoginCallBack callBack){
        mLoginCallBack = callBack;
        HermesEventBus.getDefault().post(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginCallbackEvent event){
        if (event.error==null){
            mLoginCallBack.connectSuccess();
        }else {
            mLoginCallBack.connectionFailed(event.error);
        }
    }
}
