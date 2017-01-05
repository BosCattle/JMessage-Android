package com.china.epower.chat.ui.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.china.epower.chat.R;
import com.china.epower.chat.ui.adapter.PersonalBaseViewHolder;
import com.china.epower.chat.ui.pattern.ConstructListData;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.ByteArrayOutputStream;
import tech.jiangtao.support.kit.util.FileUtil;

/**
 * Class: PersonalImageViewHolder </br>
 * Description: 用于修改头像信息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 30/11/2016 11:16 PM</br>
 * Update: 30/11/2016 11:16 PM </br>
 **/

public class PersonalImageViewHolder extends PersonalBaseViewHolder {

  @BindView(R.id.item_title) TextView mItemTitle;
  @BindView(R.id.item_image) CircleImageView mItemImage;
  private Context mContext;

  public PersonalImageViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_update_image);
    ButterKnife.bind(this,itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, ConstructListData l) {
    if (l.getmImage()!=null) {
      Glide.with(mContext)
          .load(Uri.parse(l.getmImage()))
          .asBitmap()
          .centerCrop()
          .placeholder(R.mipmap.ic_launcher)
          .into(mItemImage);
      //mItemImage.setImageBitmap(FileUtil.drawableToBytes(l.getmImage()));
    }
    mItemTitle.setText(l.getmTitle());
  }
}
