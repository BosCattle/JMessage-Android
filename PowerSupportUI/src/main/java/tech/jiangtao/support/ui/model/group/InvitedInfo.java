package tech.jiangtao.support.ui.model.group;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vurtex on 2017/4/10.
 */

public class InvitedInfo implements Parcelable {
    public String avatar;
    public String inviteType;
    public String nickName;
    public String relative;
    public String userId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatar);
        dest.writeString(this.inviteType);
        dest.writeString(this.nickName);
        dest.writeString(this.relative);
        dest.writeString(this.userId);
    }

    public InvitedInfo() {
    }

    protected InvitedInfo(Parcel in) {
        this.avatar = in.readString();
        this.inviteType = in.readString();
        this.nickName = in.readString();
        this.relative = in.readString();
        this.userId = in.readString();
    }

    public static final Parcelable.Creator<InvitedInfo> CREATOR = new Parcelable.Creator<InvitedInfo>() {
        @Override
        public InvitedInfo createFromParcel(Parcel source) {
            return new InvitedInfo(source);
        }

        @Override
        public InvitedInfo[] newArray(int size) {
            return new InvitedInfo[size];
        }
    };
}
