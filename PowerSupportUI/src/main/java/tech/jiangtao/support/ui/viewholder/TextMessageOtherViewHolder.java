package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ChatBaseViewHolder;
import tech.jiangtao.support.ui.pattern.ConstructMessage;

/**
 * Class: TextMessageOtherViewHolder </br>
 * Description: 来自别人的消息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 24/12/2016 6:28 PM</br>
 * Update: 24/12/2016 6:28 PM </br>
 **/

public class TextMessageOtherViewHolder extends ChatBaseViewHolder {
  @BindView(R2.id.item_chat_avatar_other) CircleImageView mItemChatAvatar;
  @BindView(R2.id.item_chat_message) TextView mItemChatMessage;
  private Context mContext;

  public TextMessageOtherViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_message_other);
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
    mItemChatMessage.setText(messageRealm.getTextMessage());
  }
}
