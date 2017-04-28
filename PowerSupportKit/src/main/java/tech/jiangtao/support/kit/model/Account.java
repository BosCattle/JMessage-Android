package tech.jiangtao.support.kit.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class: Account </br>
 * Description: 账户 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 11/04/2017 10:53 PM</br>
 * Update: 11/04/2017 10:53 PM </br>
 **/
public class Account implements Parcelable {
  public int uid;
  public int nid;
  public String userId;
  public String nickName;
  public String avatar;
  public boolean sex;
  public String signature;
  public String token;

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public int getNid() {
    return nid;
  }

  public void setNid(int nid) {
    this.nid = nid;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public boolean isSex() {
    return sex;
  }

  public void setSex(boolean sex) {
    this.sex = sex;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Account() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.uid);
    dest.writeInt(this.nid);
    dest.writeString(this.userId);
    dest.writeString(this.nickName);
    dest.writeString(this.avatar);
    dest.writeByte(this.sex ? (byte) 1 : (byte) 0);
    dest.writeString(this.signature);
    dest.writeString(this.token);
  }

  protected Account(Parcel in) {
    this.uid = in.readInt();
    this.nid = in.readInt();
    this.userId = in.readString();
    this.nickName = in.readString();
    this.avatar = in.readString();
    this.sex = in.readByte() != 0;
    this.signature = in.readString();
    this.token = in.readString();
  }

  public static final Creator<Account> CREATOR = new Creator<Account>() {
    @Override public Account createFromParcel(Parcel source) {
      return new Account(source);
    }

    @Override public Account[] newArray(int size) {
      return new Account[size];
    }
  };
}
