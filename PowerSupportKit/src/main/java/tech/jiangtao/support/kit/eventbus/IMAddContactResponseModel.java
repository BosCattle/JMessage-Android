package tech.jiangtao.support.kit.eventbus;

import android.os.Parcel;
import android.os.Parcelable;
import org.jivesoftware.smack.roster.packet.RosterPacket;
import tech.jiangtao.support.kit.model.Result;
import tech.jiangtao.support.kit.realm.ContactRealm;

/**
 * Class: IMContactRequestModel </br>
 * Description: 添加好友的回调 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 05:21</br>
 * Update: 27/05/2017 05:21 </br>
 **/

public class IMAddContactResponseModel implements Parcelable {

  public ContactRealm contactRealm;
  public Result result;

  public IMAddContactResponseModel(ContactRealm contactRealm, Result result) {
    this.contactRealm = contactRealm;
    this.result = result;
  }

  public ContactRealm getContactRealm() {
    return contactRealm;
  }

  public void setContactRealm(ContactRealm contactRealm) {
    this.contactRealm = contactRealm;
  }

  public Result getResult() {
    return result;
  }

  public void setResult(Result result) {
    this.result = result;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(this.contactRealm, flags);
    dest.writeParcelable(this.result, flags);
  }

  protected IMAddContactResponseModel(Parcel in) {
    this.contactRealm = in.readParcelable(ContactRealm.class.getClassLoader());
    this.result = in.readParcelable(Result.class.getClassLoader());
  }

  public static final Creator<IMAddContactResponseModel> CREATOR =
      new Creator<IMAddContactResponseModel>() {
        @Override public IMAddContactResponseModel createFromParcel(Parcel source) {
          return new IMAddContactResponseModel(source);
        }

        @Override public IMAddContactResponseModel[] newArray(int size) {
          return new IMAddContactResponseModel[size];
        }
      };
}
