package tech.jiangtao.support.kit.eventbus;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;
import org.jivesoftware.smack.packet.Message;
import tech.jiangtao.support.kit.archive.type.MessageAuthor;
import tech.jiangtao.support.kit.archive.type.DataExtensionType;
import tech.jiangtao.support.kit.archive.type.MessageExtensionType;

/**
 * Class: RecieveMessage </br>
 * Description: 接受到的消息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/12/2016 8:30 PM</br>
 * Update: 06/12/2016 8:30 PM </br>
 **/

public class RecieveLastMessage  implements Parcelable {
  public String id;
  public Message.Type type;
  //user from
  public String userJID;
  // user to
  public String ownJid;
  public Date date;
  public String thread;
  public String message;
  // 消息类型，包括图片，语音，文字等等。
  public DataExtensionType messageType;
  public MessageExtensionType messageExtensionType;
  public boolean readStatus;
  public MessageAuthor messageAuthor;
  public String groupId;

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Message.Type getType() {
    return type;
  }

  public void setType(Message.Type type) {
    this.type = type;
  }

  public String getUserJID() {
    return userJID;
  }

  public void setUserJID(String userJID) {
    this.userJID = userJID;
  }

  public String getOwnJid() {
    return ownJid;
  }

  public void setOwnJid(String ownJid) {
    this.ownJid = ownJid;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getThread() {
    return thread;
  }

  public void setThread(String thread) {
    this.thread = thread;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public DataExtensionType getMessageType() {
    return messageType;
  }

  public void setMessageType(DataExtensionType messageType) {
    this.messageType = messageType;
  }

  public MessageExtensionType getMessageExtensionType() {
    return messageExtensionType;
  }

  public void setMessageExtensionType(MessageExtensionType messageExtensionType) {
    this.messageExtensionType = messageExtensionType;
  }

  public boolean isReadStatus() {
    return readStatus;
  }

  public void setReadStatus(boolean readStatus) {
    this.readStatus = readStatus;
  }

  public MessageAuthor getMessageAuthor() {
    return messageAuthor;
  }

  public void setMessageAuthor(MessageAuthor messageAuthor) {
    this.messageAuthor = messageAuthor;
  }

  public RecieveLastMessage(Message.Type type, String userJID, String message ,DataExtensionType fileType) {
    this.type = type;
    this.userJID = userJID;
    this.message = message;
    this.messageType = fileType;
  }

  public RecieveLastMessage(String id, Message.Type type, String userJID, String ownJid, String message,
      DataExtensionType messageType, boolean readStatus) {
    this.id = id;
    this.type = type;
    this.userJID = userJID;
    this.ownJid = ownJid;
    this.message = message;
    this.messageType = messageType;
    this.readStatus = readStatus;
  }

  public RecieveLastMessage(String id, Message.Type type, String userJID, String ownJid, String thread,
      String message, DataExtensionType messageType, boolean readStatus) {
    this.id = id;
    this.type = type;
    this.userJID = userJID;
    this.ownJid = ownJid;
    this.thread = thread;
    this.message = message;
    this.messageType = messageType;
    this.readStatus = readStatus;
  }

  public RecieveLastMessage(String id, Message.Type type, String userJID, String ownJid, Date date,
      String thread, String message, DataExtensionType messageType, boolean readStatus) {
    this.id = id;
    this.type = type;
    this.userJID = userJID;
    this.ownJid = ownJid;
    this.date = date;
    this.thread = thread;
    this.message = message;
    this.messageType = messageType;
    this.readStatus = readStatus;
  }

  public RecieveLastMessage(String id, Message.Type type, String userJID, String ownJid, String thread,
      String message, DataExtensionType messageType,MessageExtensionType messageExtensionType, boolean readStatus,
      MessageAuthor messageAuthor,String groupId) {
    this.id = id;
    this.type = type;
    this.userJID = userJID;
    this.ownJid = ownJid;
    this.thread = thread;
    this.message = message;
    this.messageType = messageType;
    this.readStatus = readStatus;
    this.messageAuthor = messageAuthor;
    this.messageExtensionType = messageExtensionType;
    this.groupId = groupId;
  }

  public RecieveLastMessage() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    dest.writeString(this.userJID);
    dest.writeString(this.ownJid);
    dest.writeLong(this.date != null ? this.date.getTime() : -1);
    dest.writeString(this.thread);
    dest.writeString(this.message);
    dest.writeInt(this.messageType == null ? -1 : this.messageType.ordinal());
    dest.writeInt(this.messageExtensionType == null ? -1 : this.messageExtensionType.ordinal());
    dest.writeByte(this.readStatus ? (byte) 1 : (byte) 0);
    dest.writeInt(this.messageAuthor == null ? -1 : this.messageAuthor.ordinal());
  }

  protected RecieveLastMessage(Parcel in) {
    this.id = in.readString();
    int tmpType = in.readInt();
    this.type = tmpType == -1 ? null : Message.Type.values()[tmpType];
    this.userJID = in.readString();
    this.ownJid = in.readString();
    long tmpDate = in.readLong();
    this.date = tmpDate == -1 ? null : new Date(tmpDate);
    this.thread = in.readString();
    this.message = in.readString();
    int tmpMessageType = in.readInt();
    this.messageType = tmpMessageType == -1 ? null : DataExtensionType.values()[tmpMessageType];
    int tmpMessageExtensionType = in.readInt();
    this.messageExtensionType = tmpMessageExtensionType == -1 ? null
        : MessageExtensionType.values()[tmpMessageExtensionType];
    this.readStatus = in.readByte() != 0;
    int tmpMessageAuthor = in.readInt();
    this.messageAuthor = tmpMessageAuthor == -1 ? null : MessageAuthor.values()[tmpMessageAuthor];
  }

  public static final Creator<RecieveLastMessage> CREATOR = new Creator<RecieveLastMessage>() {
    @Override public RecieveLastMessage createFromParcel(Parcel source) {
      return new RecieveLastMessage(source);
    }

    @Override public RecieveLastMessage[] newArray(int size) {
      return new RecieveLastMessage[size];
    }
  };
}
