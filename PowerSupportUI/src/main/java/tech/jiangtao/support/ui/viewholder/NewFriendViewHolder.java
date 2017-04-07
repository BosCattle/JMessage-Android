package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.jiangtao.support.kit.eventbus.FriendRequest;
import tech.jiangtao.support.kit.eventbus.RecieveFriend;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: NewFriendViewHolder </br>
 * Description: 好友页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 08/01/2017 6:13 PM</br>
 * Update: 08/01/2017 6:13 PM </br>
 **/

public class NewFriendViewHolder extends EasyViewHolder<FriendRequest> {
  @BindView(R2.id.new_friend_avatar) ImageView mNewFriendAvatar;
  @BindView(R2.id.new_friend_nickname) TextView mNewFriendNickname;
  @BindView(R2.id.new_friend_agree) TextView mNewFriendAgree;
  @BindView(R2.id.new_friend_refused) TextView mNewFriendRefused;
  private Context mContext;

  public NewFriendViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_new_friend);
    ButterKnife.bind(this,itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, FriendRequest request) {
    Glide.with(mContext)
        .load(request.avatar != null ? Uri.parse(request.avatar) : null)
        .centerCrop()
        .error(R.mipmap.ic_chat_default)
        .placeholder(R.mipmap.ic_chat_default)
        .into(mNewFriendAvatar);
    mNewFriendNickname.setText(request.username);
    mNewFriendAgree.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        HermesEventBus.getDefault().post(new RecieveFriend(true));
        mNewFriendAgree.setText("成功");
      }
    });
    mNewFriendRefused.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //TODO 拒绝对方申请
      }
    });
  }
}
