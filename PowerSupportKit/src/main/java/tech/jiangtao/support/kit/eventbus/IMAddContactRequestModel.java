package tech.jiangtao.support.kit.eventbus;

/**
 * Class: IMDeleteContactRequestModel </br>
 * Description: 添加好友的请求 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/01/2017 6:24 PM</br>
 * Update: 06/01/2017 6:24 PM </br>
 **/

public class IMAddContactRequestModel {
  public String userId;
  public String nickName;

  public IMAddContactRequestModel(String userId,String nickName){
    this.userId = userId;
    this.nickName = nickName;
  }

  public IMAddContactRequestModel(String userId){
    this.userId = userId;
  }
}
