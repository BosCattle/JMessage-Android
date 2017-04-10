package tech.jiangtao.support.kit.userdata;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import tech.jiangtao.support.kit.callback.GroupCreateCallBack;
import tech.jiangtao.support.kit.eventbus.muc.model.GroupCreateCallBackEvent;
import tech.jiangtao.support.kit.eventbus.muc.model.GroupCreateParam;
import tech.jiangtao.support.kit.eventbus.muc.model.GroupRequest;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: SimpleCGroup </br>
 * Description: 封装群组创建功能 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 12/01/2017 12:50 AM</br>
 * Update: 12/01/2017 12:50 AM </br>
 **/

public class SimpleCGroup {

  private GroupCreateCallBack mCallBack;

  public SimpleCGroup(){
    if (!HermesEventBus.getDefault().isRegistered(this)){
      HermesEventBus.getDefault().register(this);
    }
  }

  public void startCreateGroup(GroupCreateParam param,GroupCreateCallBack callback){
    mCallBack  = callback;
    HermesEventBus.getDefault().post(param);
  }

  /**
   * 入群
   * @param groupId
   * @param nickname
   */
  public void startGroupRequest(String groupId,String nickname){
    GroupRequest request = new GroupRequest(groupId,nickname);
    HermesEventBus.getDefault().post(request);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void recievedGroupInfo(GroupCreateCallBackEvent event){
    if (event.message!=null){
      mCallBack.createSuccess();
    }else {
      mCallBack.createFailed("发生错误");
    }
  }

  public  void destory(){
    HermesEventBus.getDefault().unregister(this);
  }
}
