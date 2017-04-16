package tech.jiangtao.support.kit.realm;


import android.os.Parcel;
import android.os.Parcelable;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SessionRealm extends RealmObject implements Parcelable {

    // 回话id
    @PrimaryKey
    public String sessionId;
    //  回话对方id
    public String senderFriendId;
    // 消息id
    public String messageId;
    // 未读条数
    public int unReadCount;
    // 消息类型，单聊---群聊
    public boolean messageType;

  public boolean isMessageType() {
    return messageType;
  }

  public void setMessageType(boolean messageType) {
    this.messageType = messageType;
  }

  public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSenderFriendId() {
        return senderFriendId;
    }

    public void setSenderFriendId(String senderFriendId) {
        this.senderFriendId = senderFriendId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

  public SessionRealm() {
    }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.sessionId);
    dest.writeString(this.senderFriendId);
    dest.writeString(this.messageId);
    dest.writeInt(this.unReadCount);
    dest.writeByte(this.messageType ? (byte) 1 : (byte) 0);
  }

  protected SessionRealm(Parcel in) {
    this.sessionId = in.readString();
    this.senderFriendId = in.readString();
    this.messageId = in.readString();
    this.unReadCount = in.readInt();
    this.messageType = in.readByte() != 0;
  }

  public static final Creator<SessionRealm> CREATOR = new Creator<SessionRealm>() {
    @Override public SessionRealm createFromParcel(Parcel source) {
      return new SessionRealm(source);
    }

    @Override public SessionRealm[] newArray(int size) {
      return new SessionRealm[size];
    }
  };
}
