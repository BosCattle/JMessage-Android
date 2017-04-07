package tech.jiangtao.support.ui.pattern;

import android.support.annotation.DrawableRes;

import java.util.List;

import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.ui.model.type.ContactType;

/**
 * Class: ConstrutContact </br>
 * Description: 构造通讯录界面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 04/12/2016 9:40 PM</br>
 * Update: 04/12/2016 9:40 PM </br>
 **/

public class ConstrutContact {

  public ContactType mType;
  public @DrawableRes int mId;
  public String mTitle;
  public VCardRealm mVCardRealm;
  public Object mObject;
  public List<Object> mDatas;
  public String mSubtitle;

  public static class Builder {
    public ContactType type;
    public @DrawableRes int id;
    public String title;
    public VCardRealm vCardRealm;
    public Object object;
    public List<Object> datas;
    public String subtitle;
    public ConstrutContact build(){
      ConstrutContact contact = new ConstrutContact();
      contact.mType = type;
      contact.mId = id;
      contact.mTitle = title;
      contact.mVCardRealm = vCardRealm;
      contact.mObject = object;
      contact.mDatas = datas;
      contact.mSubtitle = subtitle;
      return contact;
    }

    public Builder type(ContactType type1){
      this.type = type1;
      return this;
  }

    public Builder id(int id1){
      this.id = id1;
      return this;
    }

    public Builder title(String text){
      this.title = text;
      return this;
    }

    public Builder vCardRealm(VCardRealm realm){
      this.vCardRealm = realm;
      return this;
    }

    public Builder object(Object object){
      this.object = object;
      return this;
    }

    public Builder datas(List<Object> datas){
      this.datas = datas;
      return this;
    }

    public Builder subtitle(String title){
      this.subtitle = title;
      return this;
    }

  }
}
