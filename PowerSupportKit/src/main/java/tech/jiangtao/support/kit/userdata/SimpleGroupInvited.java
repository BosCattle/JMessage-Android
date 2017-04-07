package tech.jiangtao.support.kit.userdata;

import tech.jiangtao.support.kit.eventbus.InvitedFriendToGroup;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: SimpleGroupInvited </br>
 * Description: 邀请好友入群 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 2017/4/7 下午9:51</br>
 * Update: 2017/4/7 下午9:51 </br>
 **/

public class SimpleGroupInvited {

  public void startPost(InvitedFriendToGroup invitedFriend){
    HermesEventBus.getDefault().post(invitedFriend);
  }
}
