package tech.jiangtao.support.kit.eventbus;

/**
 * Class: IMNotificationConnection </br>
 * Description: 启动服务 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 30/12/2016 8:48 PM</br>
 * Update: 30/12/2016 8:48 PM </br>
 **/

public class IMNotificationConnection {
  /**
   * true : 连接
   * false：断开连接
   */

  public boolean connectChoice;
  public IMNotificationConnection(boolean connC){
    this.connectChoice = connC;
  }
}
