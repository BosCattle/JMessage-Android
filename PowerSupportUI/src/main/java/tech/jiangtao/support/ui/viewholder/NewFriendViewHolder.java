package tech.jiangtao.support.ui.viewholder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.jiangtao.support.kit.eventbus.RecieveFriend;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.kit.model.group.InvitedInfo;
import tech.jiangtao.support.kit.model.type.TransportType;
import tech.jiangtao.support.ui.utils.ResourceAddress;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: NewFriendViewHolder </br>
 * Description: 好友页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 08/01/2017 6:13 PM</br>
 * Update: 08/01/2017 6:13 PM </br>
 **/

public class NewFriendViewHolder extends EasyViewHolder<InvitedInfo> {
  @BindView(R2.id.new_friend_avatar) ImageView mNewFriendAvatar;
  @BindView(R2.id.new_friend_nickname) TextView mNewFriendNickname;
  @BindView(R2.id.new_friend_agree) TextView mNewFriendAgree;
  @BindView(R2.id.new_friend_refused) TextView mNewFriendRefused;
  private Context mContext;

  public NewFriendViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_new_friend);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @SuppressLint("SetTextI18n") @Override public void bindTo(int position, InvitedInfo info) {
    Glide.with(mContext)
        .load(info.account.avatar != null ? ResourceAddress.url(info.account.avatar,
            TransportType.AVATAR) : null)
        .centerCrop()
        .error(R.mipmap.ic_chat_default)
        .placeholder(R.mipmap.ic_chat_default)
        .into(mNewFriendAvatar);
    mNewFriendNickname.setText(info.account.nickName + "请求添加您为好友。");
    //TODO 缺少邀请要加入的群名
    mNewFriendAgree.setOnClickListener(v -> {
      HermesEventBus.getDefault().post(new RecieveFriend(true,info.getAccount().getUserId()));
      mNewFriendAgree.setText("成功");
      mNewFriendAgree.setEnabled(false);
    });
    mNewFriendRefused.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        HermesEventBus.getDefault().post(new RecieveFriend(false,info.getAccount().getUserId()));
        mNewFriendRefused.setText("拒绝成功");
        mNewFriendRefused.setEnabled(false);
      }
    });
  }
}
