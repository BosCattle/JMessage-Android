package com.china.epower.chat.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.china.epower.chat.R;
import com.china.epower.chat.ui.adapter.ChatBaseViewHolder;
import com.china.epower.chat.ui.pattern.ConstructMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiang on 2016/11/14.
 * 文字消息
 * note: 自己发送的
 */

public class TextMessageMineViewHolder extends ChatBaseViewHolder {
    @BindView(R.id.item_chat_avatar)
    ImageView mItemChatAvatar;
    @BindView(R.id.item_chat_message)
    TextView mItemChatMessage;
    private Context mContext;

    public TextMessageMineViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.list_item_message_mine);
        ButterKnife.bind(this,itemView);
        mContext = context;
    }


    @Override
    public void bindTo(int position,ConstructMessage constructMessage) {
        Glide.with(mContext)
                .load("http://baidu.com")
                .centerCrop()
                .error(R.mipmap.ic_chat_default)
                .placeholder(R.mipmap.ic_chat_default)
                .crossFade()
                .into(mItemChatAvatar);
        mItemChatMessage.setText(constructMessage.mMessage.paramContent);
    }
}
