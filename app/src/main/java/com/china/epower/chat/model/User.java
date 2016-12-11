package com.china.epower.chat.model;

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
  public String username;
  public String name;
  public String jid;
  public String email;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.username);
    dest.writeString(this.name);
    dest.writeString(this.jid);
    dest.writeString(this.email);
  }

  public User() {
  }

  protected User(Parcel in) {
    this.username = in.readString();
    this.name = in.readString();
    this.jid = in.readString();
    this.email = in.readString();
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
