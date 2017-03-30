package tech.jiangtao.support.kit.archive;

import android.os.Parcel;
import android.os.Parcelable;
import tech.jiangtao.support.kit.archive.type.MessageBodyType;

/**
 * Class: MessageBody </br>
 * Description: 消息体 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/12/2016 10:06 AM</br>
 * Update: 06/12/2016 10:06 AM </br>
 **/

public class MessageBody implements Parcelable {
  //消息的主键id
  public String id;
  // 具体到聊天类型
  public String chatType;
  // 消息类型
  public String messageType;
  // 消息状态
  // TODO: 03/01/2017 true: 表示已读，false:表示未读
  public boolean messageStatus;
  //根据消息类型判断是谁发送的消息
  public MessageBodyType type;
  //可以计算出消息发送的时间
  public String secs;
  // 谁发送的消息
  public String with;
  //消息内容
  public String body;
  //消息线程
  public String thread;

  public MessageBody(String id, String chatType, String messageType, boolean messageStatus,
      MessageBodyType type, String secs, String with, String body, String thread) {
    this.id = id;
    this.chatType = chatType;
    this.messageType = messageType;
    this.messageStatus = messageStatus;
    this.type = type;
    this.secs = secs;
    this.with = with;
    this.body = body;
    this.thread = thread;
  }

  public String getSecs() {
    return secs;
  }

  public void setSecs(String secs) {
    this.secs = secs;
  }

  public String getWith() {
    return with;
  }

  public void setWith(String with) {
    this.with = with;
  }

  public MessageBody(MessageBodyType type, String secs, String with, String body, String thread) {
    this.type = type;
    this.secs = secs;
    this.with = with;
    this.body = body;
    this.thread = thread;
  }

  public MessageBody(MessageBodyType type, String secs, String body, String thread) {
    this.type = type;
    this.secs = secs;
    this.body = body;
    this.thread = thread;
  }

  public MessageBody(String body, String thread) {
    this.body = body;
    this.thread = thread;
  }

  public MessageBodyType getType() {
    return type;
  }

  public void setType(MessageBodyType type) {
    this.type = type;
  }

  public MessageBody(MessageBodyType type, String body, String thread) {
    this.type = type;
    this.body = body;
    this.thread = thread;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getThread() {
    return thread;
  }

  public void setThread(String thread) {
    this.thread = thread;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getChatType() {
    return chatType;
  }

  public void setChatType(String chatType) {
    this.chatType = chatType;
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
    dest.writeString(this.chatType);
    dest.writeString(this.messageType);
    dest.writeByte(this.messageStatus ? (byte) 1 : (byte) 0);
    dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    dest.writeString(this.secs);
    dest.writeString(this.with);
    dest.writeString(this.body);
    dest.writeString(this.thread);
  }

  protected MessageBody(Parcel in) {
    this.id = in.readString();
    this.chatType = in.readString();
    this.messageType = in.readString();
    this.messageStatus = in.readByte() != 0;
    int tmpType = in.readInt();
    this.type = tmpType == -1 ? null : MessageBodyType.values()[tmpType];
    this.secs = in.readString();
    this.with = in.readString();
    this.body = in.readString();
    this.thread = in.readString();
  }

  public static final Creator<MessageBody> CREATOR = new Creator<MessageBody>() {
    @Override public MessageBody createFromParcel(Parcel source) {
      return new MessageBody(source);
    }

    @Override public MessageBody[] newArray(int size) {
      return new MessageBody[size];
    }
  };

  @Override public String toString() {
    return "MessageBody{" +
        "id='" + id + '\'' +
        ", chatType='" + chatType + '\'' +
        ", messageType='" + messageType + '\'' +
        ", messageStatus=" + messageStatus +
        ", type=" + type +
        ", secs='" + secs + '\'' +
        ", with='" + with + '\'' +
        ", body='" + body + '\'' +
        ", thread='" + thread + '\'' +
        '}';
  }
}
