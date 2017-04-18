package tech.jiangtao.support.ui.model.group;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class: Authority </br>
 * Description: 是否接收消息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 18/04/2017 9:36 PM</br>
 * Update: 18/04/2017 9:36 PM </br>
 **/

public class Authority implements Parcelable{

  public int authority;
  public int isReceived;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.authority);
    dest.writeInt(this.isReceived);
  }

  public Authority() {
  }

  protected Authority(Parcel in) {
    this.authority = in.readInt();
    this.isReceived = in.readInt();
  }

  public static final Creator<Authority> CREATOR = new Creator<Authority>() {
    @Override public Authority createFromParcel(Parcel source) {
      return new Authority(source);
    }

    @Override public Authority[] newArray(int size) {
      return new Authority[size];
    }
  };
}
