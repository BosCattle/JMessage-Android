package tech.jiangtao.support.kit.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiang on 2016/11/15.
 * 聊天界面中的一些拓展
 */

public class ChatExtraModel implements Parcelable {
    public int id;
    public String name;

    public ChatExtraModel(int id){
        this.id = id;
    }


    public ChatExtraModel(int id,String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    public ChatExtraModel() {
    }

    protected ChatExtraModel(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<ChatExtraModel> CREATOR = new Creator<ChatExtraModel>() {
        @Override
        public ChatExtraModel createFromParcel(Parcel source) {
            return new ChatExtraModel(source);
        }

        @Override
        public ChatExtraModel[] newArray(int size) {
            return new ChatExtraModel[size];
        }
    };
}
