package tech.jiangtao.support.kit.userdata;

import tech.jiangtao.support.kit.eventbus.muc.model.InviteParam;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: SimpleCGroup </br>
 * Description: 封装被邀请反馈功能 </br>
 * Creator: Vurtex </br>
 * Email: hongkeshu@gmail.com </br>
 * Date: 07/04/2017 13:00 </br>
 * Update: 07/04/2017 13:00 </br>
 **/

public class SimpleInvite {

  public SimpleInvite(){
    if (!HermesEventBus.getDefault().isRegistered(this)){
      HermesEventBus.getDefault().register(this);
    }
  }

  public void startReceived(InviteParam param){
    HermesEventBus.getDefault().post(param);
  }




  public  void destory(){
    HermesEventBus.getDefault().unregister(this);
  }
}
