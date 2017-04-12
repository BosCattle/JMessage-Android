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

public class User extends Account implements Parcelable {
  public String relative;
  public String inviteType;
  public Integer onlineStatus;

  public User() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(this.relative);
    dest.writeString(this.inviteType);
    dest.writeValue(this.onlineStatus);
  }

  protected User(Parcel in) {
    super(in);
    this.relative = in.readString();
    this.inviteType = in.readString();
    this.onlineStatus = (Integer) in.readValue(Integer.class.getClassLoader());
  }

  public static final Creator<User> CREATOR = new Creator<User>() {
    @Override public User createFromParcel(Parcel source) {
      return new User(source);
    }

    @Override public User[] newArray(int size) {
      return new User[size];
    }
  };
}
