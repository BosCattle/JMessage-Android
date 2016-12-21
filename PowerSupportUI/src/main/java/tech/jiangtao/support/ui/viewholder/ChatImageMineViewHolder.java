package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ChatBaseViewHolder;
import tech.jiangtao.support.ui.pattern.ConstructMessage;

/**
 * Created by jiang on 2016/12/21.
 */

public class ChatImageMineViewHolder extends ChatBaseViewHolder {
    @BindView(R2.id.item_chat_avatar)
    ImageView mItemChatAvatar;
    @BindView(R2.id.item_chat_message)
    ImageView mItemChatImage;
    private Context mContext;

    public ChatImageMineViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.list_item_chat_image_mine);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    @Override
    public void bindTo(int position, ConstructMessage constructMessage) {
        Glide.with(mContext)
                .load(constructMessage.mAvatar)
                .asBitmap()
                .centerCrop()
                .error(R.mipmap.ic_chat_default)
                .placeholder(R.mipmap.ic_chat_default)
                .into(mItemChatAvatar);

        Glide.with(mContext)
                .load(Uri.parse(constructMessage.mMessage.fimePath))
                .error(R.mipmap.ic_mipmap_default_image)
                .placeholder(R.mipmap.ic_mipmap_default_image)
                .into(mItemChatImage);
    }
}
