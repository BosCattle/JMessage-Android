package tech.jiangtao.support.kit.eventbus;

import android.os.Parcel;
import android.os.Parcelable;
import org.jivesoftware.smack.roster.packet.RosterPacket;

/**
 * Class: IMContactRequestModel </br>
 * Description: 响应通讯录 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 05:21</br>
 * Update: 27/05/2017 05:21 </br>
 **/

public class IMContactResponseModel implements Parcelable {

  public String userName;
  public RosterPacket.ItemStatus status;
  public RosterPacket.ItemType type;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public RosterPacket.ItemStatus getStatus() {
    return status;
  }

  public void setStatus(RosterPacket.ItemStatus status) {
    this.status = status;
  }

  public RosterPacket.ItemType getType() {
    return type;
  }

  public void setType(RosterPacket.ItemType type) {
    this.type = type;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.userName);
    dest.writeInt(this.status == null ? -1 : this.status.ordinal());
    dest.writeInt(this.type == null ? -1 : this.type.ordinal());
  }

  public IMContactResponseModel() {
  }

  protected IMContactResponseModel(Parcel in) {
    this.userName = in.readString();
    int tmpStatus = in.readInt();
    this.status = tmpStatus == -1 ? null : RosterPacket.ItemStatus.values()[tmpStatus];
    int tmpType = in.readInt();
    this.type = tmpType == -1 ? null : RosterPacket.ItemType.values()[tmpType];
  }

  public static final Parcelable.Creator<IMContactResponseModel> CREATOR =
      new Parcelable.Creator<IMContactResponseModel>() {
        @Override public IMContactResponseModel createFromParcel(Parcel source) {
          return new IMContactResponseModel(source);
        }

        @Override public IMContactResponseModel[] newArray(int size) {
          return new IMContactResponseModel[size];
        }
      };
}
