package tech.jiangtao.support.kit.eventbus.muc.model;

/**
 * Class: GroupCreateCallBackEvent </br>
 * Description: 群创建回调类 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 12/01/2017 1:07 AM</br>
 * Update: 12/01/2017 1:07 AM </br>
 **/

public class GroupCreateCallBackEvent {

  public String message;

  public GroupCreateCallBackEvent(String message){
    this.message = message;
  }
}
