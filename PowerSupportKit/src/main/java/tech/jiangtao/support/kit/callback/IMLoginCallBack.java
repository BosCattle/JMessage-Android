package tech.jiangtao.support.kit.callback;

import tech.jiangtao.support.kit.model.Account;
import tech.jiangtao.support.kit.model.Result;

/**
 * Class: IMLoginCallBack </br>
 * Description: 登录回调 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 02/12/2016 9:05 AM</br>
 * Update: 02/12/2016 9:05 AM </br>
 **/

public interface IMLoginCallBack {

  void connectSuccess(Account account);

  void connectionFailed(Result result);
}
