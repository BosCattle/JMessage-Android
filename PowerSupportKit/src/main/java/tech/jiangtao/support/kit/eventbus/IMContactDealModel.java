package tech.jiangtao.support.kit.eventbus;

/**
 * Class: IMContactDealModel </br>
 * Description: 是否同意添加好友 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 08/01/2017 5:25 PM</br>
 * Update: 08/01/2017 5:25 PM </br>
 **/

public class IMContactDealModel {
  public boolean value;
  public String userId;

  public IMContactDealModel(boolean value,String userId){
    this.value = value;
    this.userId = userId;
  }
}
