package tech.jiangtao.support.kit.userdata;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import tech.jiangtao.support.kit.callback.RegisterCallBack;
import tech.jiangtao.support.kit.eventbus.RegisterAccount;
import tech.jiangtao.support.kit.eventbus.RegisterResult;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: SimpleRegister </br>
 * Description: 封装一下注册 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/01/2017 11:16 PM</br>
 * Update: 06/01/2017 11:16 PM </br>
 **/

public class SimpleRegister {

  private RegisterCallBack mCallback;

  public SimpleRegister(){
    if (!HermesEventBus.getDefault().isRegistered(this)){
      HermesEventBus.getDefault().register(this);
    }
  }

  public void startRegister(RegisterAccount account,RegisterCallBack callback){
    this.mCallback = callback;
    HermesEventBus.getDefault().post(account);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void registerCallBack(RegisterResult result){
    if (result.account!=null) {
      mCallback.success(result.account);
    }else {
      mCallback.error(result.errorMessage);
    }
  }

  public void destory(){
    HermesEventBus.getDefault().unregister(this);
  }
}
