package tech.jiangtao.support.kit.eventbus;

import android.os.Parcel;

import android.os.Parcelable;
import java.util.Date;
import tech.jiangtao.support.kit.archive.type.MessageAuthor;
import tech.jiangtao.support.kit.model.jackson.Message;

/**
 * Class: IMMessageResponseModel </br>
 * Description: 接受到的消息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/12/2016 8:30 PM</br>
 * Update: 06/12/2016 8:30 PM </br>
 **/

public class IMMessageResponseModel implements Parcelable {
  public String id;
  public Message message;
  public Date date;
  public String thread;
  public boolean readStatus;
  public MessageAuthor author;

  public IMMessageResponseModel(String id, Message message, Date date, String thread,
      boolean readStatus,MessageAuthor author) {
    this.id = id;
    this.message = message;
    this.date = date;
    this.thread = thread;
    this.readStatus = readStatus;
    this.author = author;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
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

  public boolean isReadStatus() {
    return readStatus;
  }

  public void setReadStatus(boolean readStatus) {
    this.readStatus = readStatus;
  }

  public MessageAuthor getAuthor() {
    return author;
  }

  public void setAuthor(MessageAuthor author) {
    this.author = author;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeParcelable(this.message, flags);
    dest.writeLong(this.date != null ? this.date.getTime() : -1);
    dest.writeString(this.thread);
    dest.writeByte(this.readStatus ? (byte) 1 : (byte) 0);
    dest.writeInt(this.author == null ? -1 : this.author.ordinal());
  }

  protected IMMessageResponseModel(Parcel in) {
    this.id = in.readString();
    this.message = in.readParcelable(Message.class.getClassLoader());
    long tmpDate = in.readLong();
    this.date = tmpDate == -1 ? null : new Date(tmpDate);
    this.thread = in.readString();
    this.readStatus = in.readByte() != 0;
    int tmpAuthor = in.readInt();
    this.author = tmpAuthor == -1 ? null : MessageAuthor.values()[tmpAuthor];
  }

  public static final Creator<IMMessageResponseModel> CREATOR =
      new Creator<IMMessageResponseModel>() {
        @Override public IMMessageResponseModel createFromParcel(Parcel source) {
          return new IMMessageResponseModel(source);
        }

        @Override public IMMessageResponseModel[] newArray(int size) {
          return new IMMessageResponseModel[size];
        }
      };
}
