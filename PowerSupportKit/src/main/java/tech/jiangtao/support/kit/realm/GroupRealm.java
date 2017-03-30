package tech.jiangtao.support.kit.realm;

import io.realm.RealmObject;

/**
 * Class: GroupRealm </br>
 * Description: 我的所有群组 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 10/01/2017 11:52 PM</br>
 * Update: 10/01/2017 11:52 PM </br>
 **/

public class GroupRealm extends RealmObject {
  public String groupName;

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
}
