package tech.jiangtao.support.kit.eventbus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiang on 2017/1/5.
 */

public class LocalVCardEvent implements Parcelable {

    private String jid;
    private String nickName;
    private String sex;
    private String subject;
    private String office;
    private String email;
    private String phoneNumber;
    private String signature;
    private String avatar;
    private String firstLetter;
    private String allPinYin;
    private boolean friend;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getAllPinYin() {
        return allPinYin;
    }

    public void setAllPinYin(String allPinYin) {
        this.allPinYin = allPinYin;
    }

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }

    public LocalVCardEvent(String jid, String nickName, String avatar,
                           String firstLetter, String allPinYin, boolean friend) {
        this.jid = jid;
        this.nickName = nickName;
        this.avatar = avatar;
        this.firstLetter = firstLetter;
        this.allPinYin = allPinYin;
        this.friend = friend;
    }

    public LocalVCardEvent() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jid);
        dest.writeString(this.nickName);
        dest.writeString(this.sex);
        dest.writeString(this.subject);
        dest.writeString(this.office);
        dest.writeString(this.email);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.signature);
        dest.writeString(this.avatar);
        dest.writeString(this.firstLetter);
        dest.writeString(this.allPinYin);
        dest.writeByte(this.friend ? (byte) 1 : (byte) 0);
    }

    protected LocalVCardEvent(Parcel in) {
        this.jid = in.readString();
        this.nickName = in.readString();
        this.sex = in.readString();
        this.subject = in.readString();
        this.office = in.readString();
        this.email = in.readString();
        this.phoneNumber = in.readString();
        this.signature = in.readString();
        this.avatar = in.readString();
        this.firstLetter = in.readString();
        this.allPinYin = in.readString();
        this.friend = in.readByte() != 0;
    }

    public static final Creator<LocalVCardEvent> CREATOR = new Creator<LocalVCardEvent>() {
        @Override
        public LocalVCardEvent createFromParcel(Parcel source) {
            return new LocalVCardEvent(source);
        }

        @Override
        public LocalVCardEvent[] newArray(int size) {
            return new LocalVCardEvent[size];
        }
    };
}
