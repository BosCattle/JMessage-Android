package tech.jiangtao.support.kit.model.jackson;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class: Message </br>
 * Description: 发送到消息的消息内容 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 11/05/2017 18:43</br>
 * Update: 11/05/2017 18:43 </br>
 **/
public class Message implements Parcelable {

  public String message;
  // 数据类型
  public String type;
  public String url;
  public int time;
  public String latitude;
  public String longitude;
  public String msgSender;
  public String msgReceived;
  public String group;

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  // 聊天类型，单聊，群聊
  public String chatType;

  public String getMsgReceived() {
    return msgReceived;
  }

  public void setMsgReceived(String msgReceived) {
    this.msgReceived = msgReceived;
  }

  public String getChatType() {
    return chatType;
  }

  public void setChatType(String chatType) {
    this.chatType = chatType;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getMsgSender() {
    return msgSender;
  }

  public void setMsgSender(String msgSender) {
    this.msgSender = msgSender;
  }

  public Message() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.message);
    dest.writeString(this.type);
    dest.writeString(this.url);
    dest.writeInt(this.time);
    dest.writeString(this.latitude);
    dest.writeString(this.longitude);
    dest.writeString(this.msgSender);
    dest.writeString(this.msgReceived);
    dest.writeString(this.group);
    dest.writeString(this.chatType);
  }

  protected Message(Parcel in) {
    this.message = in.readString();
    this.type = in.readString();
    this.url = in.readString();
    this.time = in.readInt();
    this.latitude = in.readString();
    this.longitude = in.readString();
    this.msgSender = in.readString();
    this.msgReceived = in.readString();
    this.group = in.readString();
    this.chatType = in.readString();
  }

  public static final Creator<Message> CREATOR = new Creator<Message>() {
    @Override public Message createFromParcel(Parcel source) {
      return new Message(source);
    }

    @Override public Message[] newArray(int size) {
      return new Message[size];
    }
  };
}
