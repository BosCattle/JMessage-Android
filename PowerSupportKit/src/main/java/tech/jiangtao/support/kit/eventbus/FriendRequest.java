package tech.jiangtao.support.kit.eventbus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class: FriendRequest </br>
 * Description: 添加好友基类 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 08/01/2017 2:35 PM</br>
 * Update: 08/01/2017 2:35 PM </br>
 **/

public class FriendRequest implements Parcelable {
  public String fullUserJid;
  public String username;
  public String avatar;
  // true: 代表有好友请求发送过来
  // false: 代表别人拒绝了你的好友请求
  public boolean type;


  public FriendRequest(String fullUserJid, String username, String avatar) {
    this.fullUserJid = fullUserJid;
    this.username = username;
    this.avatar = avatar;
  }

  public FriendRequest(String fullUserJid, String username, String avatar,boolean type) {
    this.fullUserJid = fullUserJid;
    this.username = username;
    this.avatar = avatar;
    this.type = type;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.fullUserJid);
    dest.writeString(this.username);
    dest.writeString(this.avatar);
  }

  protected FriendRequest(Parcel in) {
    this.fullUserJid = in.readString();
    this.username = in.readString();
    this.avatar = in.readString();
  }

  public static final Creator<FriendRequest> CREATOR = new Creator<FriendRequest>() {
    @Override public FriendRequest createFromParcel(Parcel source) {
      return new FriendRequest(source);
    }

    @Override public FriendRequest[] newArray(int size) {
      return new FriendRequest[size];
    }
  };
}
