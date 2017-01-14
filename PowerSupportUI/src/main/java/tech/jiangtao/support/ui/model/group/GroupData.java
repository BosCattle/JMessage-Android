package tech.jiangtao.support.ui.model.group;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class: GroupData </br>
 * Description: 构建的群组数据 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 14/01/2017 8:28 PM</br>
 * Update: 14/01/2017 8:28 PM </br>
 **/

public class GroupData implements Parcelable {
  public String groupJid;
  public String groupName;
  public String groupAvatar;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.groupJid);
    dest.writeString(this.groupName);
    dest.writeString(this.groupAvatar);
  }

  public GroupData() {
  }

  protected GroupData(Parcel in) {
    this.groupJid = in.readString();
    this.groupName = in.readString();
    this.groupAvatar = in.readString();
  }

  public static final Creator<GroupData> CREATOR = new Creator<GroupData>() {
    @Override public GroupData createFromParcel(Parcel source) {
      return new GroupData(source);
    }

    @Override public GroupData[] newArray(int size) {
      return new GroupData[size];
    }
  };
}
