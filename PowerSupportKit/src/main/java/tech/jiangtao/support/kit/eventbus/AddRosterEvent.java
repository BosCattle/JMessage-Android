package tech.jiangtao.support.kit.eventbus;

/**
 * Class: AddRosterEvent </br>
 * Description: 添加好友通知 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/01/2017 9:58 PM</br>
 * Update: 06/01/2017 9:58 PM </br>
 **/

public class AddRosterEvent {
  public String jid;
  public String nickname;

  public AddRosterEvent(String jid,String nickname){
    this.jid = jid;
    this.nickname = nickname;
  }
}
