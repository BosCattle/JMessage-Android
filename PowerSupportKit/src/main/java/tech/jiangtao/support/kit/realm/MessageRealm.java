package tech.jiangtao.support.kit.realm;

import android.os.Parcel;
import android.os.Parcelable;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.sql.Date;

/**
 * Class: MessageRealm </br>
 * Description: 消息存储 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 05/12/2016 12:54 AM</br>
 * Update: 05/12/2016 12:54 AM </br>
 * 将time转换为{@link Date}
 * http://xmpp.org/extensions/xep-0082.html
 * Example 2. Datetime of the first human steps on the Moon (UTC)
 * 1969-07-21T02:56:15Z
 **/

public class MessageRealm extends RealmObject implements Parcelable {

  @PrimaryKey
  public String id;
  // message_from
  public String sender;
  // message_to
  public String receiver;
  // 消息内容
  public String textMessage;
  // 暂时保留时间戳，备用
  public java.util.Date time;
  // 暂时保留，thread_id
  public String thread;
  // 具体到聊天类型
  public String type;
  // 消息类型
  public String messageType;
  // 消息状态 true: 表示已读，false:表示未读
  public boolean messageStatus;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getTextMessage() {
    return textMessage;
  }

  public void setTextMessage(String textMessage) {
    this.textMessage = textMessage;
  }

  public java.util.Date getTime() {
    return time;
  }

  public void setTime(java.util.Date time) {
    this.time = time;
  }

  public String getThread() {
    return thread;
  }

  public void setThread(String thread) {
    this.thread = thread;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMessageType() {
    return messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public boolean isMessageStatus() {
    return messageStatus;
  }

  public void setMessageStatus(boolean messageStatus) {
    this.messageStatus = messageStatus;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.sender);
    dest.writeString(this.receiver);
    dest.writeString(this.textMessage);
    dest.writeLong(this.time != null ? this.time.getTime() : -1);
    dest.writeString(this.thread);
    dest.writeString(this.type);
    dest.writeString(this.messageType);
    dest.writeByte(this.messageStatus ? (byte) 1 : (byte) 0);
  }

  public MessageRealm() {
  }

  protected MessageRealm(Parcel in) {
    this.id = in.readString();
    this.sender = in.readString();
    this.receiver = in.readString();
    this.textMessage = in.readString();
    long tmpTime = in.readLong();
    this.time = tmpTime == -1 ? null : new java.util.Date(tmpTime);
    this.thread = in.readString();
    this.type = in.readString();
    this.messageType = in.readString();
    this.messageStatus = in.readByte() != 0;
  }

  public static final Creator<MessageRealm> CREATOR = new Creator<MessageRealm>() {
    @Override public MessageRealm createFromParcel(Parcel source) {
      return new MessageRealm(source);
    }

    @Override public MessageRealm[] newArray(int size) {
      return new MessageRealm[size];
    }
  };
}
