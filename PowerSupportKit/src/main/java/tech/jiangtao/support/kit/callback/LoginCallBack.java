package tech.jiangtao.support.kit.callback;


/**
 * Class: LoginCallBack </br>
 * Description: 登录回调 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 02/12/2016 9:05 AM</br>
 * Update: 02/12/2016 9:05 AM </br>
 **/

public interface LoginCallBack {

  void connectSuccess();

  void connectionFailed(String e);
}
