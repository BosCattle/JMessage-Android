package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ChatBaseViewHolder;
import tech.jiangtao.support.ui.pattern.ConstructMessage;
import tech.jiangtao.support.ui.view.ImageDialogManager;

/**
 * Class: ChatImageOtherViewHolder </br>
 * Description: 别人发送图片过来 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 24/12/2016 6:56 PM</br>
 * Update: 24/12/2016 6:56 PM </br>
 **/

public class ChatImageOtherViewHolder extends ChatBaseViewHolder {
  @BindView(R2.id.item_chat_avatar) ImageView mItemChatAvatar;
  @BindView(R2.id.item_chat_img) ImageView mItemChatImg;
  private Context mContext;

  public ChatImageOtherViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_chat_image_other);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, ConstructMessage constructMessage) {
    Glide.with(mContext)
        .load(Uri.parse(constructMessage.mAvatar!=null?constructMessage.mAvatar:""))
        .centerCrop()
        .error(R.mipmap.ic_chat_default)
        .placeholder(R.mipmap.ic_chat_default)
        .into(mItemChatAvatar);

    Glide.with(mContext)
        .load(Uri.parse(constructMessage.mMessage.fimePath))
        .error(R.mipmap.ic_mipmap_default_image)
        .placeholder(R.mipmap.ic_mipmap_default_image)
        .into(mItemChatImg);

    mItemChatImg.setOnClickListener(v -> {
      ImageDialogManager manager =
          new ImageDialogManager(mContext, constructMessage.mMessage.fimePath);
      manager.showDialog();
    });
  }
}
