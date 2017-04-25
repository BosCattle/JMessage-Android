package tech.jiangtao.support.ui.pattern;

import android.support.annotation.DrawableRes;
import tech.jiangtao.support.kit.model.type.ListDataType;

/**
 * Class: ConstructListData </br>
 * Description: 构建个人页面数据 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 30/11/2016 3:37 PM</br>
 * Update: 30/11/2016 3:37 PM </br>
 **/

public class ConstructListData {
  private ListDataType mType;
  private int mTag;
  private String mImage;
  private String mUsername;
  private String mNickname;
  private int mArrowIcon;
  private String mTitle;
  private String mSubtitle;

  public static class Builder {
    public ListDataType type;
    public int tag;
    public String image;
    public String username;
    public String nickname;
    public int arrowIcon;
    public String title;
    public String subtitle;

    public ConstructListData build() {
      ConstructListData data = new ConstructListData();
      data.mImage = image;
      data.mType = type;
      data.mTag = tag;
      data.mUsername = username;
      data.mNickname = nickname;
      data.mArrowIcon = arrowIcon;
      data.mTitle = title;
      data.mSubtitle = subtitle;
      return data;
    }

    public Builder type(ListDataType type1) {
      type = type1;
      return this;
    }

    public Builder tag(int tag) {
      this.tag = tag;
      return this;
    }

    public Builder image(String image1) {
      image = image1;
      return this;
    }

    public Builder username(String username1) {
      username = username1;
      return this;
    }

    public Builder nickname(String nickname) {
      this.nickname = nickname;
      return this;
    }

    public Builder arrowIcon(@DrawableRes int icon) {
      this.arrowIcon = icon;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder subtitle(String subtitle) {
      this.subtitle = subtitle;
      return this;
    }
  }

  public String getmSubtitle() {
    return mSubtitle;
  }

  public ListDataType getmType() {
    return mType;
  }

  public int getmTag() {
    return mTag;
  }

  public String getmImage() {
    return mImage;
  }

  public String getmUsername() {
    return mUsername;
  }

  public String getmNickname() {
    return mNickname;
  }

  public int getmArrowIcon() {
    return mArrowIcon;
  }

  public String getmTitle() {
    return mTitle;
  }
}
