package tech.jiangtao.support.kit.eventbus;

import java.util.List;

/**
 * Class: IMRoomInviteRequestModel </br>
 * Description: 邀请用户入群的邀请 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 01/06/2017 23:13</br>
 * Update: 01/06/2017 23:13 </br>
 **/

public class IMRoomInviteRequestModel {
  public String groupId;
  public List<String> users;

  public IMRoomInviteRequestModel(String groupId,List<String> users){
    this.groupId = groupId;
    this.users = users;
  }
}
