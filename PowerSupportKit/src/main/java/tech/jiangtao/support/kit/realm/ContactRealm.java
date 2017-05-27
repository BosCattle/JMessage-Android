package tech.jiangtao.support.kit.realm;

import android.os.Parcel;
import android.os.Parcelable;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import tech.jiangtao.support.kit.util.PinYinUtils;

/**
 * Class: ContactRealm </br>
 * Description: 通讯录 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 12/04/2017 3:40 PM</br>
 * Update: 12/04/2017 3:40 PM </br>
 **/
@RealmClass public class ContactRealm extends RealmObject implements Parcelable {

  public int uid;
  public int nid;
  @PrimaryKey
  public String userId;
  public String nickName;
  public String avatar;
  public boolean sex;
  public String signature;
  public String relative;
  public String inviteType;
  public Integer onlineStatus;
  public String pinYin;
  public boolean authority;
  public String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public boolean isAuthority() {
    return authority;
  }

  public void setAuthority(boolean authority) {
    this.authority = authority;
  }

  public String getPinYin() {
    return pinYin;
  }

  public void setPinYin(String pinYin) {
    this.pinYin = PinYinUtils.ccs2Pinyin(pinYin);
  }

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
    if (avatar == null) {
      avatar = "";
    }
    return avatar;
  }

  public void setAvatar(String avatar) {
    if (avatar == null) {
      avatar = "";
    }
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

  public String getRelative() {
    return relative;
  }

  public void setRelative(String relative) {
    this.relative = relative;
  }

  public String getInviteType() {
    return inviteType;
  }

  public void setInviteType(String inviteType) {
    this.inviteType = inviteType;
  }

  public Integer getOnlineStatus() {
    return onlineStatus;
  }

  public void setOnlineStatus(Integer onlineStatus) {
    this.onlineStatus = onlineStatus;
  }

  public ContactRealm() {
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
    dest.writeString(this.relative);
    dest.writeString(this.inviteType);
    dest.writeValue(this.onlineStatus);
    dest.writeString(this.pinYin);
    dest.writeByte(this.authority ? (byte) 1 : (byte) 0);
    dest.writeString(this.token);
  }

  protected ContactRealm(Parcel in) {
    this.uid = in.readInt();
    this.nid = in.readInt();
    this.userId = in.readString();
    this.nickName = in.readString();
    this.avatar = in.readString();
    this.sex = in.readByte() != 0;
    this.signature = in.readString();
    this.relative = in.readString();
    this.inviteType = in.readString();
    this.onlineStatus = (Integer) in.readValue(Integer.class.getClassLoader());
    this.pinYin = in.readString();
    this.authority = in.readByte() != 0;
    this.token = in.readString();
  }

  public static final Creator<ContactRealm> CREATOR = new Creator<ContactRealm>() {
    @Override public ContactRealm createFromParcel(Parcel source) {
      return new ContactRealm(source);
    }

    @Override public ContactRealm[] newArray(int size) {
      return new ContactRealm[size];
    }
  };
}
