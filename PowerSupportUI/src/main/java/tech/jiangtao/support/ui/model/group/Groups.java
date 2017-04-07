package tech.jiangtao.support.ui.model.group;

import android.os.Parcel;
import android.os.Parcelable;
import java.security.Timestamp;
import java.util.List;

/**
 * Class: Groups </br>
 * Description: 群model </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 2017/4/6 下午12:45</br>
 * Update: 2017/4/6 下午12:45 </br>
 **/

public class Groups implements Parcelable {
  public String creator;
  public Timestamp createDate;
  public Timestamp lastAccessDate;
  public String roomName;
  public String groupUid;
  public String owner;
  public List<Friends> members;
  public String node;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.creator);
    dest.writeSerializable(this.createDate);
    dest.writeSerializable(this.lastAccessDate);
    dest.writeString(this.roomName);
    dest.writeString(this.groupUid);
    dest.writeString(this.owner);
    dest.writeTypedList(this.members);
    dest.writeString(this.node);
  }

  public Groups() {
  }

  protected Groups(Parcel in) {
    this.creator = in.readString();
    this.createDate = (Timestamp) in.readSerializable();
    this.lastAccessDate = (Timestamp) in.readSerializable();
    this.roomName = in.readString();
    this.groupUid = in.readString();
    this.owner = in.readString();
    this.members = in.createTypedArrayList(Friends.CREATOR);
    this.node = in.readString();
  }

  public static final Creator<Groups> CREATOR = new Creator<Groups>() {
    @Override public Groups createFromParcel(Parcel source) {
      return new Groups(source);
    }

    @Override public Groups[] newArray(int size) {
      return new Groups[size];
    }
  };
}
