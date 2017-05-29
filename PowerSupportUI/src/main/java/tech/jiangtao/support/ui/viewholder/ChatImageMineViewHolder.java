package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ChatBaseViewHolder;
import tech.jiangtao.support.ui.pattern.ConstructMessage;
import tech.jiangtao.support.ui.view.ImageDialogManager;

/**
 * Class: ChatImageMineViewHolder </br>
 * Description: 自己发表图片 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 24/12/2016 6:56 PM</br>
 * Update: 24/12/2016 6:56 PM </br>
 **/

public class ChatImageMineViewHolder extends ChatBaseViewHolder {
  @BindView(R2.id.item_chat_avatar_circle) CircleImageView mItemChatAvatar;
  @BindView(R2.id.item_chat_message) ImageView mItemChatImage;
  private Context mContext;

  public ChatImageMineViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_chat_image_mine);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, MessageRealm messageRealm) {
    Glide.with(mContext)
        .load(Uri.parse(messageRealm.getContactRealm() != null
            && messageRealm.getContactRealm().getAvatar() != null ? messageRealm.getContactRealm()
            .getAvatar() : ""))
        .centerCrop()
        .error(R.mipmap.ic_chat_default)
        .placeholder(R.mipmap.ic_chat_default)
        .into(mItemChatAvatar);

    Glide.with(mContext)
        .load(Uri.parse(messageRealm.getTextMessage()))
        .error(R.mipmap.ic_mipmap_default_image)
        .placeholder(R.mipmap.ic_mipmap_default_image)
        .into(mItemChatImage);

    mItemChatImage.setOnClickListener(v -> {
      ImageDialogManager manager = new ImageDialogManager(mContext, messageRealm.getTextMessage());
      manager.showDialog();
    });
  }
}
