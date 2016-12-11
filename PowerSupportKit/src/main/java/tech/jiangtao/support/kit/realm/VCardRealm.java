package tech.jiangtao.support.kit.realm;

import android.os.Parcel;
import android.os.Parcelable;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Class: VCardRealm </br>
 * Description: realm数据持久层 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 01/12/2016 11:18 PM</br>
 * Update: 01/12/2016 11:18 PM </br>
 **/

public class VCardRealm extends RealmObject implements Parcelable {

  @PrimaryKey
  private String jid;
  private String nickName;
  private String sex;
  private String subject;
  private String office;
  private String email;
  private String phoneNumber;
  private String signature;
  private byte[] avatar;

  public String getJid() {
    return jid;
  }

  public void setJid(String jid) {
    this.jid = jid;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getOffice() {
    return office;
  }

  public void setOffice(String office) {
    this.office = office;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public byte[] getAvatar() {
    return avatar;
  }

  public void setAvatar(byte[] avatar) {
    this.avatar = avatar;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.jid);
    dest.writeString(this.nickName);
    dest.writeString(this.sex);
    dest.writeString(this.subject);
    dest.writeString(this.office);
    dest.writeString(this.email);
    dest.writeString(this.phoneNumber);
    dest.writeString(this.signature);
    dest.writeByteArray(this.avatar);
  }

  public VCardRealm() {
  }

  protected VCardRealm(Parcel in) {
    this.jid = in.readString();
    this.nickName = in.readString();
    this.sex = in.readString();
    this.subject = in.readString();
    this.office = in.readString();
    this.email = in.readString();
    this.phoneNumber = in.readString();
    this.signature = in.readString();
    this.avatar = in.createByteArray();
  }

  public static final Creator<VCardRealm> CREATOR = new Creator<VCardRealm>() {
    @Override public VCardRealm createFromParcel(Parcel source) {
      return new VCardRealm(source);
    }

    @Override public VCardRealm[] newArray(int size) {
      return new VCardRealm[size];
    }
  };
}
