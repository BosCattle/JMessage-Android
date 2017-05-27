package tech.jiangtao.support.kit.callback;

import io.realm.RealmObject;
import io.realm.RealmResults;
import java.util.List;
import tech.jiangtao.support.kit.eventbus.IMAddContactResponseModel;
import tech.jiangtao.support.kit.eventbus.IMDeleteContactResponseModel;
import tech.jiangtao.support.kit.model.Account;
import tech.jiangtao.support.kit.model.Result;
import tech.jiangtao.support.kit.realm.ContactRealm;

/**
 * Class: IMListenerCollection </br>
 * Description: 监听器集合 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 07:12</br>
 * Update: 27/05/2017 07:12 </br>
 **/

public interface IMListenerCollection {

  /**
   * 数据库改变
   * @param <T>
   */
  interface IMRealmChangeListener<T extends RealmObject> {

    void change(List<T> realmResults);
  }

  /**
   * 登录回调
   */
  interface IMLoginListener {

    void loginSuccess(Account account);

    void loginFailed(Result result);
  }

  /**
   * 退出登录
   */
  interface IMExitlistener {

    void exitSuccess();
  }

  /**
   * 添加好友的回调
   */
  interface IMAddContactListener{

    void addContactSuccess(IMAddContactResponseModel model);

    void addContactFailed(IMAddContactResponseModel model);
  }

  /**
   * 删除好友的回调
   */
  interface IMDeleteContactListener<T extends ContactRealm>{

    void deleteContactSuccess(IMDeleteContactResponseModel model);

    void deleteContactFailed(IMDeleteContactResponseModel model);
  }


}
