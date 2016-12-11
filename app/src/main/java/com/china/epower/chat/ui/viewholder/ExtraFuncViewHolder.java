package com.china.epower.chat.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.china.epower.chat.R;
import com.china.epower.chat.model.ChatExtraModel;
import com.china.epower.chat.ui.adapter.EasyViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiang on 2016/11/15.
 * 设置额外功能的viewholder
 */

public class ExtraFuncViewHolder extends EasyViewHolder<ChatExtraModel> {
  @BindView(R.id.chat_func_extra) ImageView mChatFuncExtra;
  @BindView(R.id.chat_func_extra_text) TextView mChatFuncExtraText;
  private Context mContext;

  public ExtraFuncViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_func_extra);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, ChatExtraModel s) {
    Glide.with(mContext)
        .load(s.id)
        .centerCrop()
        .placeholder(R.mipmap.ic_launcher)
        .crossFade()
        .into(mChatFuncExtra);
    mChatFuncExtraText.setText(s.name);
  }
}
