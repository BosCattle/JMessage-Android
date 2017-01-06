package tech.jiangtao.support.kit.eventbus;

/**
 * Class: RosterEntryBus </br>
 * Description: 通讯录事件，用于删除好友 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/01/2017 6:24 PM</br>
 * Update: 06/01/2017 6:24 PM </br>
 **/

public class RosterEntryBus {
  public String jid;

  public RosterEntryBus(String jid){
    this.jid = jid;
  }
}
