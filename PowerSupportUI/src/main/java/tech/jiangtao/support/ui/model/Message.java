package tech.jiangtao.support.ui.model;

import android.os.Parcel;
import android.os.Parcelable;
import tech.jiangtao.support.ui.model.type.MessageFlag;

/**
 * Created by jiang on 2016/11/12.
 * 用于service与fragment交互的model
 */

public class Message implements Parcelable {

    public MessageFlag flag;
    public String paramTitle;
    public String paramContent;
    public String[] other;


    public Message() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.flag == null ? -1 : this.flag.ordinal());
        dest.writeString(this.paramTitle);
        dest.writeString(this.paramContent);
        dest.writeStringArray(this.other);
    }

    protected Message(Parcel in) {
        int tmpFlag = in.readInt();
        this.flag = tmpFlag == -1 ? null : MessageFlag.values()[tmpFlag];
        this.paramTitle = in.readString();
        this.paramContent = in.readString();
        this.other = in.createStringArray();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
