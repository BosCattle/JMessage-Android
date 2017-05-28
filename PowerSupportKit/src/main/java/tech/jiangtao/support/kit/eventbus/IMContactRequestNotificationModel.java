package tech.jiangtao.support.kit.eventbus;

import android.os.Parcel;
import android.os.Parcelable;
import tech.jiangtao.support.kit.archive.type.IMFNotificationType;
import tech.jiangtao.support.kit.realm.ContactRealm;

/**
 * Class: IMContactRequestNotificationModel </br>
 * Description: 收到好友请求 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 08/01/2017 2:35 PM</br>
 * Update: 08/01/2017 2:35 PM </br>
 **/

public class IMContactRequestNotificationModel implements Parcelable {
  private ContactRealm contactRealm;
  private IMFNotificationType type;

  public IMFNotificationType getType() {
    return type;
  }

  public void setType(IMFNotificationType type) {
    this.type = type;
  }

  public IMContactRequestNotificationModel(ContactRealm contactRealm,IMFNotificationType type) {
    this.contactRealm = contactRealm;
    this.type = type;
  }

  public ContactRealm getContactRealm() {
    return contactRealm;
  }

  public void setContactRealm(ContactRealm contactRealm) {
    this.contactRealm = contactRealm;
  }

  public IMContactRequestNotificationModel() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(this.contactRealm, flags);
    dest.writeInt(this.type == null ? -1 : this.type.ordinal());
  }

  protected IMContactRequestNotificationModel(Parcel in) {
    this.contactRealm = in.readParcelable(ContactRealm.class.getClassLoader());
    int tmpType = in.readInt();
    this.type = tmpType == -1 ? null : IMFNotificationType.values()[tmpType];
  }

  public static final Creator<IMContactRequestNotificationModel> CREATOR =
      new Creator<IMContactRequestNotificationModel>() {
        @Override public IMContactRequestNotificationModel createFromParcel(Parcel source) {
          return new IMContactRequestNotificationModel(source);
        }

        @Override public IMContactRequestNotificationModel[] newArray(int size) {
          return new IMContactRequestNotificationModel[size];
        }
      };
}
