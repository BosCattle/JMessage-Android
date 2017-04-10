package tech.jiangtao.support.kit.eventbus;

import java.util.ArrayList;

/**
 * Class: InvitedFriendToGroup </br>
 * Description: 邀请好友入群 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 2017/4/7 下午9:53</br>
 * Update: 2017/4/7 下午9:53 </br>
 **/

public class InvitedFriendToGroup {

  public String mucJid;
  public ArrayList<String> userId;
  public String reason;

  public InvitedFriendToGroup(String mucJid, ArrayList<String> userId, String reason) {
    this.mucJid = mucJid;
    this.userId = userId;
    this.reason = reason;
  }

  public String getMucJid() {
    return mucJid;
  }

  public void setMucJid(String mucJid) {
    this.mucJid = mucJid;
  }

  public ArrayList<String> getUserId() {
    return userId;
  }

  public void setUserId(ArrayList<String> userId) {
    this.userId = userId;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
}
