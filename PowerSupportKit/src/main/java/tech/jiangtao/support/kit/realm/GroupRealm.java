package tech.jiangtao.support.kit.realm;

import android.os.Parcel;
import android.os.Parcelable;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.io.Serializable;
import java.util.List;

/**
 * Class: GroupRealm </br>
 * Description: 我的所有群组 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 10/01/2017 11:52 PM</br>
 * Update: 10/01/2017 11:52 PM </br>
 **/

public class GroupRealm extends RealmObject  implements Serializable {
  public Long uid;
  @PrimaryKey public String groupId;
  public String name;
  public String avatar;
  public String description;
  public RealmList<ContactRealm> contactRealms;

  public RealmList<ContactRealm> getContactRealms() {
    return contactRealms;
  }

  public void setContactRealms(RealmList<ContactRealm> contactRealms) {
    this.contactRealms = contactRealms;
  }

  public Long getUid() {
    return uid;
  }

  public void setUid(Long uid) {
    this.uid = uid;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public GroupRealm() {
  }
}
