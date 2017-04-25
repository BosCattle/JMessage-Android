package tech.jiangtao.support.kit.model;

import android.os.Parcel;
import android.os.Parcelable;
import tech.jiangtao.support.kit.model.type.TransportType;

/**
 * Class: FilePath </br>
 * Description: 拿到文件位置 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 28/12/2016 10:49 PM</br>
 * Update: 28/12/2016 10:49 PM </br>
 **/

public class FilePath implements Parcelable {

  public String resourceId;
  public boolean success;
  public TransportType type;
  public String errorMessage;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.resourceId);
    dest.writeByte(this.success ? (byte) 1 : (byte) 0);
    dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    dest.writeString(this.errorMessage);
  }

  public FilePath() {
  }

  protected FilePath(Parcel in) {
    this.resourceId = in.readString();
    this.success = in.readByte() != 0;
    int tmpType = in.readInt();
    this.type = tmpType == -1 ? null : TransportType.values()[tmpType];
    this.errorMessage = in.readString();
  }

  public static final Creator<FilePath> CREATOR = new Creator<FilePath>() {
    @Override public FilePath createFromParcel(Parcel source) {
      return new FilePath(source);
    }

    @Override public FilePath[] newArray(int size) {
      return new FilePath[size];
    }
  };
}
