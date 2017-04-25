package tech.jiangtao.support.kit.model;

import android.os.Parcel;
import android.os.Parcelable;

import tech.jiangtao.support.kit.archive.type.FileType;
import tech.jiangtao.support.kit.model.type.MessageFlag;

/**
 * Class: Message </br>
 * Description: 用于service与fragment交互的model </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 09/04/2017 2:01 AM</br>
 * Update: 09/04/2017 2:01 AM </br>
 **/

public class Message implements Parcelable {

    public MessageFlag flag;
    public String paramTitle;
    public String paramContent;
    public String fileName;
    public FileType type;
    public String fimePath;
    public float time;

    public Message() {
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.flag == null ? -1 : this.flag.ordinal());
        dest.writeString(this.paramTitle);
        dest.writeString(this.paramContent);
        dest.writeString(this.fileName);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeString(this.fimePath);
        dest.writeFloat(this.time);
    }

    protected Message(Parcel in) {
        int tmpFlag = in.readInt();
        this.flag = tmpFlag == -1 ? null : MessageFlag.values()[tmpFlag];
        this.paramTitle = in.readString();
        this.paramContent = in.readString();
        this.fileName = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : FileType.values()[tmpType];
        this.fimePath = in.readString();
        this.time = in.readFloat();
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
