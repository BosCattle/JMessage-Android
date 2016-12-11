package com.china.epower.chat.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kevin on 13/11/2016.
 */

public class ChatItems implements Parcelable {
  public String avatar;
  public String username;
  public String message;
  public String time;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.avatar);
    dest.writeString(this.username);
    dest.writeString(this.message);
    dest.writeString(this.time);
  }

  public ChatItems() {
  }

  protected ChatItems(Parcel in) {
    this.avatar = in.readString();
    this.username = in.readString();
    this.message = in.readString();
    this.time = in.readString();
  }

  public static final Creator<ChatItems> CREATOR = new Creator<ChatItems>() {
    @Override public ChatItems createFromParcel(Parcel source) {
      return new ChatItems(source);
    }

    @Override public ChatItems[] newArray(int size) {
      return new ChatItems[size];
    }
  };
}
