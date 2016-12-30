package tech.jiangtao.support.kit.eventbus;

import android.os.Parcel;

import android.os.Parcelable;
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

  public Message.Type type;
  public String userJID;
  public String message;
  // 消息类型，包括图片，语音，文字等等。
  public MessageExtensionType messageType;


  public RecieveMessage(Message.Type type, String userJID, String message ,MessageExtensionType fileType) {
    this.type = type;
    this.userJID = userJID;
    this.message = message;
    this.messageType = fileType;
  }

  public RecieveMessage() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    dest.writeString(this.userJID);
    dest.writeString(this.message);
    dest.writeInt(this.messageType == null ? -1 : this.messageType.ordinal());
  }

  protected RecieveMessage(Parcel in) {
    int tmpType = in.readInt();
    this.type = tmpType == -1 ? null : Message.Type.values()[tmpType];
    this.userJID = in.readString();
    this.message = in.readString();
    int tmpMessageType = in.readInt();
    this.messageType = tmpMessageType == -1 ? null : MessageExtensionType.values()[tmpMessageType];
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
