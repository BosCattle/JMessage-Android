package tech.jiangtao.support.kit.eventbus;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;
import org.jivesoftware.smack.packet.Message;
import tech.jiangtao.support.kit.archive.type.MessageAuthor;
import tech.jiangtao.support.kit.archive.type.DataExtensionType;

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
  public boolean readStatus;
  public MessageAuthor messageAuthor;

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
      String message, DataExtensionType messageType, boolean readStatus,
      MessageAuthor messageAuthor) {
    this.id = id;
    this.type = type;
    this.userJID = userJID;
    this.ownJid = ownJid;
    this.thread = thread;
    this.message = message;
    this.messageType = messageType;
    this.readStatus = readStatus;
    this.messageAuthor = messageAuthor;
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
