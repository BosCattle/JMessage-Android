package tech.jiangtao.support.kit.eventbus;

import android.os.Parcel;

import android.os.Parcelable;
import java.util.Date;
import org.jivesoftware.smack.packet.Message;

import tech.jiangtao.support.kit.archive.type.MessageExtensionType;

/**
 * Class: RecieveMessage </br>
 * Description: 接受到的消息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/12/2016 8:30 PM</br>
 * Update: 06/12/2016 8:30 PM </br>
 **/

public class RecieveMessage implements Parcelable {
  public String id;
  public Message.Type type;
  public String userJID;
  public String ownJid;
  public Date date;
  public String thread;
  public String message;
  // 消息类型，包括图片，语音，文字等等。
  public MessageExtensionType messageType;
  public boolean readStatus;


  public RecieveMessage(Message.Type type, String userJID, String message ,MessageExtensionType fileType) {
    this.type = type;
    this.userJID = userJID;
    this.message = message;
    this.messageType = fileType;
  }

  public RecieveMessage(String id, Message.Type type, String userJID, String ownJid, String message,
      MessageExtensionType messageType, boolean readStatus) {
    this.id = id;
    this.type = type;
    this.userJID = userJID;
    this.ownJid = ownJid;
    this.message = message;
    this.messageType = messageType;
    this.readStatus = readStatus;
  }

  public RecieveMessage(String id, Message.Type type, String userJID, String ownJid, String thread,
      String message, MessageExtensionType messageType, boolean readStatus) {
    this.id = id;
    this.type = type;
    this.userJID = userJID;
    this.ownJid = ownJid;
    this.thread = thread;
    this.message = message;
    this.messageType = messageType;
    this.readStatus = readStatus;
  }

  public RecieveMessage(String id, Message.Type type, String userJID, String ownJid, Date date,
      String thread, String message, MessageExtensionType messageType, boolean readStatus) {
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

  public RecieveMessage() {
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
    dest.writeByte(this.readStatus ? (byte) 1 : (byte) 0);
  }

  protected RecieveMessage(Parcel in) {
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
    this.messageType = tmpMessageType == -1 ? null : MessageExtensionType.values()[tmpMessageType];
    this.readStatus = in.readByte() != 0;
  }

  public static final Creator<RecieveMessage> CREATOR = new Creator<RecieveMessage>() {
    @Override public RecieveMessage createFromParcel(Parcel source) {
      return new RecieveMessage(source);
    }

    @Override public RecieveMessage[] newArray(int size) {
      return new RecieveMessage[size];
    }
  };
}
