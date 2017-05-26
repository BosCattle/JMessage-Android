package tech.jiangtao.support.kit.realm;

import android.os.Parcel;
import android.os.Parcelable;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.List;

/**
 * Class: GroupRealm </br>
 * Description: 我的所有群组 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 10/01/2017 11:52 PM</br>
 * Update: 10/01/2017 11:52 PM </br>
 **/

public class GroupRealm extends RealmObject implements Parcelable {
  @PrimaryKey
  public Long uid;

  public String groupId;

  public String name;

  public String avatar;

  public String description;

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

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(this.uid);
    dest.writeString(this.groupId);
    dest.writeString(this.name);
    dest.writeString(this.avatar);
    dest.writeString(this.description);
  }

  protected GroupRealm(Parcel in) {
    this.uid = (Long) in.readValue(Long.class.getClassLoader());
    this.groupId = in.readString();
    this.name = in.readString();
    this.avatar = in.readString();
    this.description = in.readString();
  }

  public static final Creator<GroupRealm> CREATOR = new Creator<GroupRealm>() {
    @Override public GroupRealm createFromParcel(Parcel source) {
      return new GroupRealm(source);
    }

    @Override public GroupRealm[] newArray(int size) {
      return new GroupRealm[size];
    }
  };
}
