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
import tech.jiangtao.support.ui.adapter.ChatBaseViewHolder;
import tech.jiangtao.support.ui.pattern.ConstructMessage;

/**
 * Created by jiang on 2016/11/14.
 */

public class TextMessageOtherViewHolder extends ChatBaseViewHolder {
    @BindView(R2.id.item_chat_avatar)
    ImageView mItemChatAvatar;
    @BindView(R2.id.item_chat_message)
    TextView mItemChatMessage;
    private Context mContext;

    public TextMessageOtherViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.list_item_message_other);
        ButterKnife.bind(this,itemView);
        mContext = context;
    }

    @Override
    public void bindTo(int position,ConstructMessage constructMessage) {
        Glide.with(mContext)
                .load(constructMessage.mAvatar)
                .asBitmap()
                .centerCrop()
                .error(R.mipmap.ic_chat_default)
                .placeholder(R.mipmap.ic_chat_default)
                .into(mItemChatAvatar);
        mItemChatMessage.setText(constructMessage.mMessage.paramContent);
    }
}
