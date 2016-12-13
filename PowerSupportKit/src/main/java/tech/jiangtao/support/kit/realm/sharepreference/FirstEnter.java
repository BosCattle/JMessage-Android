package tech.jiangtao.support.kit.realm.sharepreference;

import com.cocosw.favor.AllFavor;

/**
 * Class: FirstEnter </br>
 * Description: 判断是否是第一次登录 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 13/12/2016 9:14 AM</br>
 * Update: 13/12/2016 9:14 AM </br>
 **/
@AllFavor
public interface FirstEnter {

  boolean getEntered();

  /**
   * 设置为true，表示已经进入过应用
   * @param enter
   */
  void setEntered(boolean enter);
}
