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
 * Class: TextMessageMineViewHolder </br>
 * Description: 自己发送的文字消息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 13/04/2017 1:51 PM</br>
 * Update: 13/04/2017 1:51 PM </br>
 **/

public class TextMessageMineViewHolder extends ChatBaseViewHolder {
  @BindView(R2.id.item_chat_avatar_w) CircleImageView mItemChatAvatarW;
  @BindView(R2.id.item_chat_message) TextView mItemChatMessage;
  private Context mContext;

  public TextMessageMineViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_message_mine);
    mContext = context;
    ButterKnife.bind(this, itemView);
  }

  @Override public void bindTo(int position, MessageRealm messageRealm) {
    Glide.with(mContext)
        .load(Uri.parse(messageRealm.getContactRealm() != null
            && messageRealm.getContactRealm().getAvatar() != null ? messageRealm.getContactRealm()
            .getAvatar() : ""))
        .centerCrop()
        .error(R.mipmap.ic_chat_default)
        .placeholder(R.mipmap.ic_chat_default)
        .into(mItemChatAvatarW);
    mItemChatMessage.setText(messageRealm.getTextMessage());
  }
}
