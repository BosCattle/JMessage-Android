package com.china.epower.chat.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.china.epower.chat.R;
import com.china.epower.chat.model.ChatExtraModel;
import com.china.epower.chat.ui.adapter.EasyViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiang on 2016/11/16.
 */

public class ExpressViewHolder extends EasyViewHolder<ChatExtraModel> {
    @BindView(R.id.chat_express)
    ImageView mChatExpress;
    private Context mContext;

    public ExpressViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.list_item_express);
        ButterKnife.bind(this,itemView);
        mContext  = context;
    }

    @Override
    public void bindTo(int position, ChatExtraModel chatExtraModel) {
        Glide.with(mContext)
                .load(chatExtraModel.id)
                .centerCrop()
                .placeholder(R.mipmap.ex_childrien_a)
                .crossFade()
                .into(mChatExpress);
    }
}
