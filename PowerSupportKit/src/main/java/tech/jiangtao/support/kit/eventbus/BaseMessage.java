package tech.jiangtao.support.kit.eventbus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class: BaseMessage </br>
 * Description: 消息基类，鸡肋 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/12/2016 8:21 PM</br>
 * Update: 06/12/2016 8:21 PM </br>
 **/

public class BaseMessage  implements Parcelable{

  public Object message;

  public BaseMessage(Object message) {
    this.message = message;
  }

  public BaseMessage() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable((Parcelable) this.message, flags);
  }

  protected BaseMessage(Parcel in) {
    this.message = in.readParcelable(Object.class.getClassLoader());
  }

}
