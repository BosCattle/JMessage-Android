package tech.jiangtao.support.ui.viewholder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import tech.jiangtao.support.kit.realm.SessionRealm;
import tech.jiangtao.support.kit.util.DateUtils;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseSessionListViewHolder;
import tech.jiangtao.support.ui.utils.ResourceAddress;

/**
 * Class: AllChatListViewHolder </br>
 * Description: 所有的聊天列表页 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 31/12/2016 12:20 PM</br>
 * Update: 31/12/2016 12:20 PM </br>
 **/

public abstract class AllChatListViewHolder extends BaseSessionListViewHolder {
  @BindView(R2.id.item_chat_avatar) ImageView mItemChatAvatar;
  @BindView(R2.id.session_nickname) TextView mSessionNickname;
  @BindView(R2.id.session_message) TextView mSessionMessage;
  @BindView(R2.id.session_time) TextView mSessionTime;
  @BindView(R2.id.session_unread_count) TextView mSessionUnreadCount;
  private Context mContext;

  public AllChatListViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_session_normal);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @SuppressLint("SetTextI18n") @Override public void bindTo(int position, SessionRealm session) {
    Glide.with(mContext)
        .load(Uri.parse(ResourceAddress.avatar(session)))
        .centerCrop()
        .error(R.mipmap.ic_chat_default)
        .placeholder(R.mipmap.ic_chat_default)
        .into(mItemChatAvatar);
    mSessionNickname.setText(ResourceAddress.nickName(session));
    mSessionMessage.setText(session.getMessageRealm().getTextMessage());
    mSessionTime.setText(DateUtils.format(session.getMessageRealm().getTime()));
    if (session.getUnReadCount() != 0) {
      mSessionUnreadCount.setVisibility(View.VISIBLE);
      mSessionUnreadCount.setText(session.getUnReadCount() + "");
    } else {
      mSessionUnreadCount.setVisibility(View.GONE);
    }
  }
}
