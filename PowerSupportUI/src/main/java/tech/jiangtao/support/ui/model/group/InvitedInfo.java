package tech.jiangtao.support.ui.model.group;

import android.os.Parcel;
import android.os.Parcelable;
import tech.jiangtao.support.kit.eventbus.InviteFriend;
import tech.jiangtao.support.ui.model.User;

/**
 * Created by Vurtex on 2017/4/10.
 */

public class InvitedInfo implements Parcelable {

  public User account;
  public Groups group;

  public InvitedInfo(User account, Groups groups) {
    this.account = account;
    this.group = groups;
  }

  public Groups getGroup() {
    return group;
  }

  public User getAccount() {
    return account;
  }

  public void setAccount(User account) {
    this.account = account;
  }

  public void setGroup(Groups group) {
    this.group = group;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(this.account, flags);
    dest.writeParcelable(this.group, flags);
  }

  public InvitedInfo() {
  }

  protected InvitedInfo(Parcel in) {
    this.account = in.readParcelable(User.class.getClassLoader());
    this.group = in.readParcelable(Groups.class.getClassLoader());
  }

  public static final Creator<InvitedInfo> CREATOR = new Creator<InvitedInfo>() {
    @Override public InvitedInfo createFromParcel(Parcel source) {
      return new InvitedInfo(source);
    }

    @Override public InvitedInfo[] newArray(int size) {
      return new InvitedInfo[size];
    }
  };
}
