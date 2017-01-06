package tech.jiangtao.support.kit.callback;

import tech.jiangtao.support.kit.eventbus.RegisterAccount;

/**
 * Class: RegisterCallBack </br>
 * Description: 注册回调 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/01/2017 11:14 PM</br>
 * Update: 06/01/2017 11:14 PM </br>
 **/

public interface RegisterCallBack {

  void success(RegisterAccount account);

  void error(String reason);
}
