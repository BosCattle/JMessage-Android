package tech.jiangtao.support.kit.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class: Result </br>
 * Description: 响应结果 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 26/05/2017 23:44</br>
 * Update: 26/05/2017 23:44 </br>
 **/

public class Result implements Parcelable {

  public int code;
  public String msg;

  public Result(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.code);
    dest.writeString(this.msg);
  }

  protected Result(Parcel in) {
    this.code = in.readInt();
    this.msg = in.readString();
  }

  public static final Creator<Result> CREATOR = new Creator<Result>() {
    @Override public Result createFromParcel(Parcel source) {
      return new Result(source);
    }

    @Override public Result[] newArray(int size) {
      return new Result[size];
    }
  };
}
