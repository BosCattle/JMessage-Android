package tech.jiangtao.support.kit.eventbus.muc.model;

import java.util.Collection;

/**
 * Class: GroupCreateParam </br>
 * Description: 创建群所传入的对象 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 12/01/2017 1:00 AM</br>
 * Update: 12/01/2017 1:00 AM </br>
 **/

public class GroupCreateParam {

  public String roomName;
  public String nickname;
  public Collection<String> owner;
  public int maxNumber;

  public GroupCreateParam(String roomName, String nickname, Collection<String> owner) {
    this.roomName = roomName;
    this.nickname = nickname;
    this.owner = owner;
  }

  public GroupCreateParam(String roomName, String nickname, Collection<String> owner,
      int maxNumber) {
    this.roomName = roomName;
    this.nickname = nickname;
    this.owner = owner;
    this.maxNumber = maxNumber;
  }
}
