package tech.jiangtao.support.kit.eventbus;

import tech.jiangtao.support.kit.model.Result;

/**
 * Class: IMDeleteContactResponseModel </br>
 * Description: 删除VCardRealm </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/01/2017 7:52 PM</br>
 * Update: 06/01/2017 7:52 PM </br>
 **/

public class IMDeleteContactResponseModel {
  public String userId;
  public Result result;
  public IMDeleteContactResponseModel(String userId,Result result){
    this.userId = userId;
    this.result = result;
  }
}
