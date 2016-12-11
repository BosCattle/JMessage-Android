package tech.jiangtao.support.kit.realm.sharepreference;

import com.cocosw.favor.AllFavor;

/**
 * Class: Account </br>
 * Description: 用户登陆后保存的用户信息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 10/12/2016 3:00 PM</br>
 * Update: 10/12/2016 3:00 PM </br>
 **/
@AllFavor
public interface Account {
  String getUserName();
  String getPassword();
  void setUserName(String name);
  void setPassword(String password);
}
