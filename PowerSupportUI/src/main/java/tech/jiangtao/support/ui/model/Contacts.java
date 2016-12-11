package tech.jiangtao.support.ui.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.jivesoftware.smack.roster.packet.RosterPacket;

/**
 * Class: Contacts </br>
 * Description: 通讯录信息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 13/11/2016 5:10 PM</br>
 * Update: 13/11/2016 5:10 PM </br>
 **/

public class Contacts implements Parcelable {
  public String user;
  public String avatar;
  public String name;
  public RosterPacket.ItemType type;

  public Contacts() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.user);
    dest.writeString(this.avatar);
    dest.writeString(this.name);
    dest.writeInt(this.type == null ? -1 : this.type.ordinal());
  }

  protected Contacts(Parcel in) {
    this.user = in.readString();
    this.avatar = in.readString();
    this.name = in.readString();
    int tmpType = in.readInt();
    this.type = tmpType == -1 ? null : RosterPacket.ItemType.values()[tmpType];
  }

  public static final Creator<Contacts> CREATOR = new Creator<Contacts>() {
    @Override public Contacts createFromParcel(Parcel source) {
      return new Contacts(source);
    }

    @Override public Contacts[] newArray(int size) {
      return new Contacts[size];
    }
  };
}
