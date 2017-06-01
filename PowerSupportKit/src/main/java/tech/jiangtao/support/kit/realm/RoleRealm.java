package tech.jiangtao.support.kit.realm;

import io.realm.RealmObject;
import java.io.Serializable;

/**
 * Class: RoleRealm </br>
 * Description: 群组成员 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 01/06/2017 21:30</br>
 * Update: 01/06/2017 21:30 </br>
 **/

public class RoleRealm extends RealmObject implements Serializable{
  // 角色,不同的群的角色是不一样的,需要存储类型为map，或者有角色表

  public String role;
  public String userId;
}
