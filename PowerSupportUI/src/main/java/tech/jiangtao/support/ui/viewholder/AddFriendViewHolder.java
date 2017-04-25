package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.kit.model.User;
import tech.jiangtao.support.kit.model.type.TransportType;
import tech.jiangtao.support.ui.utils.ResourceAddress;

/**
 * Class: AddFriendViewHolder </br>
 * Description: 添加好友的显示 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 13/11/2016 3:36 PM</br>
 * Update: 13/11/2016 3:36 PM </br>
 **/

public class AddFriendViewHolder extends EasyViewHolder<User> {
  @BindView(R2.id.add_friend_img) ImageView mAddFriendImg;
  @BindView(R2.id.add_friend_username) TextView mAddFriendUsername;
  @BindView(R2.id.add_friend_email) TextView mAddFriendEmail;

  private Context mContext;

  public AddFriendViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_add_friend);
    ButterKnife.bind(this,itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, User user) {
    if (user != null) {
      Glide.with(mContext)
          .load(ResourceAddress.url(user.avatar, TransportType.AVATAR))
          .centerCrop()
          .placeholder(R.mipmap.ic_chat_default)
          .crossFade()
          .into(mAddFriendImg);
      mAddFriendUsername.setText(user.nickName);
      mAddFriendEmail.setText(user.signature);
    }
  }
}
