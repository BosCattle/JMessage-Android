package tech.jiangtao.support.kit.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.io.Serializable;

/**
 * Class: RoomRealm </br>
 * Description: 房间 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 01/06/2017 21:29</br>
 * Update: 01/06/2017 21:29 </br>
 **/

public class RoomRealm extends RealmObject implements Serializable {
  // 主键，随机生成
  @PrimaryKey public String uuid;
  // 群组id
  public String groupId;
  public String name;
  public String avatar;
  public String description;
  // 用户信息的realm
  public RealmList<ContactRealm> contactRealms;
  // 用户权限的relam
  public RealmList<RoleRealm> roleRealms;
}
