package tech.jiangtao.support.ui.viewholder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;

import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ChatBaseViewHolder;
import tech.jiangtao.support.ui.pattern.ConstructMessage;
import tech.jiangtao.support.ui.view.MediaManager;

/**
 * Class: PlayerOtherViewHolder </br>
 * Description: 播放别人录制的语音 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 24/12/2016 5:16 PM</br>
 * Update: 24/12/2016 5:16 PM </br>
 **/

public class PlayerOtherViewHolder extends ChatBaseViewHolder {

    @BindView(R2.id.item_chat_avatar)
    ImageView mItemChatAvatar;
    @BindView(R2.id.view_player_style)
    View mViewPlayerStyle;
    @BindView(R2.id.view_player_container)
    FrameLayout mViewPlayerContainer;
    @BindView(R2.id.item_chat_message)
    TextView mItemChatMessage;
    private Context mContext;

    public PlayerOtherViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.list_item_player_other);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bindTo(int position, ConstructMessage constructMessage) {
        Glide.with(mContext)
                .load(Uri.parse(constructMessage.mAvatar))
                .centerCrop()
                .error(R.mipmap.ic_chat_default)
                .placeholder(R.mipmap.ic_chat_default)
                .into(mItemChatAvatar);
        mItemChatMessage.setText("" + constructMessage.mMessage.time);
        mViewPlayerStyle.setOnClickListener(v -> {
            mViewPlayerStyle.setBackgroundResource(R.drawable.anim_player_animation);
            AnimationDrawable background = (AnimationDrawable) mViewPlayerStyle.getBackground();
            background.start();
            MediaManager.playSound(constructMessage.mMessage.fimePath, mp -> {
                MediaManager.release();
                mViewPlayerStyle.setBackgroundResource(R.mipmap.ic_voice_right);
            });
        });
    }
}
