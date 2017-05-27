package tech.jiangtao.support.kit.eventbus;

/**
 * Class: IMDeleteContactRequestModel </br>
 * Description: 通讯录事件，用于删除好友 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/01/2017 6:24 PM</br>
 * Update: 06/01/2017 6:24 PM </br>
 **/

public class IMDeleteContactRequestModel {
  public String userId;

  public IMDeleteContactRequestModel(String userId){
    this.userId = userId;
  }
}
