package tech.jiangtao.support.kit.realm;


import android.os.Parcel;
import android.os.Parcelable;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
/**
 * Class: SessionRealm </br>
 * Description:  </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 02:50</br>
 * Update: 27/05/2017 02:50 </br>
 **/
// TODO: 27/05/2017 添加会话的用户,群组，类型
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
    public int messageType;
    // 用户信息
    public ContactRealm contactRealm;
    // 群组信息
    public GroupRealm groupRealm;
    // 消息
    public MessageRealm messageRealm;

  public MessageRealm getMessageRealm() {
    return messageRealm;
  }

  public void setMessageRealm(MessageRealm messageRealm) {
    this.messageRealm = messageRealm;
  }

  public ContactRealm getContactRealm() {
    return contactRealm;
  }

  public void setContactRealm(ContactRealm contactRealm) {
    this.contactRealm = contactRealm;
  }

  public GroupRealm getGroupRealm() {
    return groupRealm;
  }

  public void setGroupRealm(GroupRealm groupRealm) {
    this.groupRealm = groupRealm;
  }

  public int getMessageType() {
    return messageType;
  }

  public void setMessageType(int messageType) {
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
    dest.writeInt(this.messageType);
    dest.writeParcelable(this.contactRealm, flags);
    dest.writeParcelable(this.groupRealm, flags);
    dest.writeParcelable(this.messageRealm, flags);
  }

  protected SessionRealm(Parcel in) {
    this.sessionId = in.readString();
    this.senderFriendId = in.readString();
    this.messageId = in.readString();
    this.unReadCount = in.readInt();
    this.messageType = in.readInt();
    this.contactRealm = in.readParcelable(ContactRealm.class.getClassLoader());
    this.groupRealm = in.readParcelable(GroupRealm.class.getClassLoader());
    this.messageRealm = in.readParcelable(MessageRealm.class.getClassLoader());
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
