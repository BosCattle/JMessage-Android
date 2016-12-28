package tech.jiangtao.support.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class: FilePath </br>
 * Description: 拿到文件位置 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 28/12/2016 10:49 PM</br>
 * Update: 28/12/2016 10:49 PM </br>
 **/

public class FilePath implements Parcelable {

  public String filePath;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.filePath);
  }

  public FilePath() {
  }

  protected FilePath(Parcel in) {
    this.filePath = in.readString();
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
