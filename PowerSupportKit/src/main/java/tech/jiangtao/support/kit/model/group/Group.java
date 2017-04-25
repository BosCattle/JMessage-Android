package tech.jiangtao.support.kit.model.group;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class: Group </br>
 * Description: 群组 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 16/04/2017 5:07 PM</br>
 * Update: 16/04/2017 5:07 PM </br>
 **/

public class Group implements Parcelable {
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

  public Group() {
  }

  protected Group(Parcel in) {
    this.uid = (Long) in.readValue(Long.class.getClassLoader());
    this.groupId = in.readString();
    this.name = in.readString();
    this.avatar = in.readString();
    this.description = in.readString();
  }

  public static final Creator<Group> CREATOR = new Creator<Group>() {
    @Override public Group createFromParcel(Parcel source) {
      return new Group(source);
    }

    @Override public Group[] newArray(int size) {
      return new Group[size];
    }
  };
}
