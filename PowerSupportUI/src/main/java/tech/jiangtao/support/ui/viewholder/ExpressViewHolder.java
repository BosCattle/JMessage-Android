package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.model.ChatExtraModel;

/**
 * Created by jiang on 2016/11/16.
 */

public class ExpressViewHolder extends EasyViewHolder<ChatExtraModel> {
    @BindView(R2.id.chat_express)
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
                .placeholder(0)
                .crossFade()
                .into(mChatExpress);
    }
}
