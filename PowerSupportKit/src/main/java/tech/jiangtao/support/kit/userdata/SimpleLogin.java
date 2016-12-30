package tech.jiangtao.support.kit.userdata;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import tech.jiangtao.support.kit.callback.LoginCallBack;
import tech.jiangtao.support.kit.eventbus.LoginCallbackEvent;
import tech.jiangtao.support.kit.eventbus.LoginParam;
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
