package tech.jiangtao.support.kit.eventbus;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import tech.jiangtao.support.kit.realm.ContactRealm;

/**
 * Class: IMContactRequestModel </br>
 * Description: 响应通讯录 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 05:21</br>
 * Update: 27/05/2017 05:21 </br>
 **/

public class IMContactResponseCollection implements Parcelable {

  public List<ContactRealm> models;

  public IMContactResponseCollection(List<ContactRealm> models) {
    this.models = models;
  }

  public List<ContactRealm> getModels() {
    return models;
  }

  public void setModels(List<ContactRealm> models) {
    this.models = models;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(this.models);
  }

  protected IMContactResponseCollection(Parcel in) {
    this.models = in.createTypedArrayList(ContactRealm.CREATOR);
  }

  public static final Creator<IMContactResponseCollection> CREATOR =
      new Creator<IMContactResponseCollection>() {
        @Override public IMContactResponseCollection createFromParcel(Parcel source) {
          return new IMContactResponseCollection(source);
        }

        @Override public IMContactResponseCollection[] newArray(int size) {
          return new IMContactResponseCollection[size];
        }
      };
}
