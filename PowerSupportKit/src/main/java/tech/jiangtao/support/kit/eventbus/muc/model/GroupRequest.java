package tech.jiangtao.support.kit.eventbus.muc.model;

/**
 * Class: GroupRequest </br>
 * Description: 加群请求 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 10/04/2017 3:00 PM</br>
 * Update: 10/04/2017 3:00 PM </br>
 **/

public class GroupRequest {
  
  public String groupJid;
  public String nickName;
  
  public GroupRequest(String groupJid,String nickName){
    this.groupJid = groupJid;
    this.nickName = nickName;
  }
}
