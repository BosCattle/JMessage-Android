package tech.jiangtao.support.kit.callback;

import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Class: IMListenerCollection </br>
 * Description: 监听器集合 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 07:12</br>
 * Update: 27/05/2017 07:12 </br>
 **/

public interface IMListenerCollection {

  interface IMRealmChangeListener<T extends RealmObject> {

    void change(RealmResults<T> realmResults);
  }
}
