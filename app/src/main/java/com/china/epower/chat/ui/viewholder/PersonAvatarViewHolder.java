package com.china.epower.chat.ui.viewholder;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.china.epower.chat.R;
import com.china.epower.chat.model.ChatExtraModel;
import com.china.epower.chat.ui.adapter.EasyViewHolder;

import butterknife.BindView;
import com.china.epower.chat.ui.adapter.PersonalBaseViewHolder;
import com.china.epower.chat.ui.pattern.ConstructListData;
import java.io.ByteArrayOutputStream;
import org.jivesoftware.smack.util.FileUtils;
import tech.jiangtao.support.kit.util.FileUtil;

/**
 * Class: PersonAvatarViewHolder </br>
 * Description: 个人界面头像信息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 30/11/2016 4:36 PM</br>
 * Update: 30/11/2016 4:36 PM </br>
 **/

public class PersonAvatarViewHolder extends PersonalBaseViewHolder{
  @BindView(R.id.item_person_avatar) ImageView mItemPersonAvatar;
  @BindView(R.id.item_person_username) TextView mItemPersonUsername;
  @BindView(R.id.item_person_style) TextView mItemPersonStyle;
  @BindView(R.id.item_person_arrow) ImageView mItemPersonArrow;
  private Context mContext;

  public PersonAvatarViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_avatar);
    ButterKnife.bind(this,itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, ConstructListData listData) {
    if (listData.getmImage()!=null) {
      Glide.with(mContext)
          .load(Uri.parse(listData.getmImage()))
          .centerCrop()
          .placeholder(R.mipmap.ic_launcher)
          .into(mItemPersonAvatar);
    }
    mItemPersonUsername.setText(listData.getmUsername());
    mItemPersonStyle.setText(listData.getmNickname());
    mItemPersonArrow.setImageResource(listData.getmArrowIcon());
  }
}
