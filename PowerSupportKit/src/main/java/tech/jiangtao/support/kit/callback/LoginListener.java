package tech.jiangtao.support.kit.callback;

import tech.jiangtao.support.kit.Account;
import tech.jiangtao.support.kit.Result;

/**
 * Class: LoginListener </br>
 * Description: 登录会调 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 26/05/2017 23:39</br>
 * Update: 26/05/2017 23:39 </br>
 **/
public interface LoginListener {

  void loginSuccess(Account user);

  void loginFailed(Result result);
}
