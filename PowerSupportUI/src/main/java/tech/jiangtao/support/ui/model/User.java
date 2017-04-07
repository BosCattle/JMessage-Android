package tech.jiangtao.support.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class: User </br>
 * Description: 用户信息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 13/11/2016 3:35 PM</br>
 * Update: 13/11/2016 3:35 PM </br>
 **/

public class User implements Parcelable {
  public String userId;
  public String nickName;
  public String avatar;
  public String relative;

  public User() {
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.userId);
    dest.writeString(this.nickName);
    dest.writeString(this.avatar);
    dest.writeString(this.relative);
  }

  protected User(Parcel in) {
    this.userId = in.readString();
    this.nickName = in.readString();
    this.avatar = in.readString();
    this.relative = in.readString();
  }

  public static final Creator<User> CREATOR = new Creator<User>() {
    @Override
    public User createFromParcel(Parcel source) {
      return new User(source);
    }

    @Override
    public User[] newArray(int size) {
      return new User[size];
    }
  };
}
