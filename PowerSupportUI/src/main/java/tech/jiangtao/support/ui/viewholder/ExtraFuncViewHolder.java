package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.model.ChatExtraModel;

/**
 * Created by jiang on 2016/11/15.
 * 设置额外功能的viewholder
 */

public class ExtraFuncViewHolder extends EasyViewHolder<ChatExtraModel> {
  @BindView(R2.id.chat_func_extra) ImageView mChatFuncExtra;
  @BindView(R2.id.chat_func_extra_text) TextView mChatFuncExtraText;
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
