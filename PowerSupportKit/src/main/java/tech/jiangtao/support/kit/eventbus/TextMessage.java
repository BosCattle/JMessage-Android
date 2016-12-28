package tech.jiangtao.support.kit.eventbus;

import android.os.Parcel;
import android.os.Parcelable;
import org.jivesoftware.smack.packet.Message;
import tech.jiangtao.support.kit.archive.type.FileType;

/**
 * Class: TextMessage </br>
 * Description: 发送出去的文本消息，待重构 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 04/12/2016 3:21 AM</br>
 * Update: 04/12/2016 3:21 AM </br>
 **/

public class TextMessage extends BaseMessage implements Parcelable {
  private Message.Type type;
  public String userJID;
  public String message;
  // 消息类型，包括图片，语音，文字等等。
  public FileType messageType;

  public TextMessage(Message.Type type, String userJID, String message) {
    super(message);
    this.type = type;
    this.userJID = userJID;
    this.message = message;
  }

  public TextMessage(Message.Type type, String userJID, String message ,FileType fileType) {
    super(message);
    this.type = type;
    this.userJID = userJID;
    this.message = message;
    this.messageType = fileType;
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

  protected TextMessage(Parcel in) {
    int tmpType = in.readInt();
    this.type = tmpType == -1 ? null : Message.Type.values()[tmpType];
    this.userJID = in.readString();
    this.message = in.readString();
    int tmpMessageType = in.readInt();
    this.messageType = tmpMessageType == -1 ? null : FileType.values()[tmpMessageType];
  }

  public static final Creator<TextMessage> CREATOR = new Creator<TextMessage>() {
    @Override public TextMessage createFromParcel(Parcel source) {
      return new TextMessage(source);
    }

    @Override public TextMessage[] newArray(int size) {
      return new TextMessage[size];
    }
  };
}
