package tech.jiangtao.support.kit.callback;

import org.jivesoftware.smack.XMPPConnection;

/**
 * Class: ConnectionCallback </br>
 * Description: 登录成功之后使用接口回调传递connection </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 02/12/2016 9:51 AM</br>
 * Update: 02/12/2016 9:51 AM </br>
 **/

public interface ConnectionCallback {

  void connection(XMPPConnection connection);

  void connectionFailed(Exception e);
}
