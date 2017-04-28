package tech.jiangtao.support.kit.eventbus;

/**
 * Class: RecieveFriend </br>
 * Description: 是否同意添加好友 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 08/01/2017 5:25 PM</br>
 * Update: 08/01/2017 5:25 PM </br>
 **/

public class RecieveFriend {
  public boolean agreeFriends;
  // 响应对方的jid
  public String to;

  public RecieveFriend(boolean agreeFriend,String to){
    this.agreeFriends = agreeFriend;
    this.to = to;
  }
}
