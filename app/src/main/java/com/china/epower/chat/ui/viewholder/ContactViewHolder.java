package com.china.epower.chat.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.china.epower.chat.R;
import com.china.epower.chat.model.ChatItems;
import com.china.epower.chat.ui.adapter.EasyViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kevin on 13/11/2016.
 */

public class ContactViewHolder extends EasyViewHolder<ChatItems> {
  @BindView(R.id.item_chat_img) CircleImageView mItemChatImg;
  @BindView(R.id.item_chat_username) TextView mItemChatUsername;
  @BindView(R.id.item_chat_message) TextView mItemChatMessage;
  @BindView(R.id.item_chat_time) TextView mItemChatTime;
  private Context mContext;

  public ContactViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_chat);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, ChatItems chatItems) {
    if (chatItems != null) {
      Glide.with(mContext)
          .load(chatItems.avatar)
          .centerCrop()
          .placeholder(R.mipmap.ic_launcher)
          .crossFade()
          .into(mItemChatImg);
      mItemChatUsername.setText(chatItems.username);
      mItemChatMessage.setText(chatItems.message);
      mItemChatTime.setText(chatItems.time);
    }
  }
}
