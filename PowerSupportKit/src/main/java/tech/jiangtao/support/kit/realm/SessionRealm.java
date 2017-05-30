package tech.jiangtao.support.kit.realm;


import android.os.Parcel;
import android.os.Parcelable;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.io.Serializable;

/**
 * Class: SessionRealm </br>
 * Description:  </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 02:50</br>
 * Update: 27/05/2017 02:50 </br>
 **/
// TODO: 27/05/2017 添加会话的用户,群组，类型
public class SessionRealm extends RealmObject implements Serializable {

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
}
