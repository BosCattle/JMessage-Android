package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import tech.jiangtao.support.kit.eventbus.QueryUserResult;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.model.User;

/**
 * Class: AddFriendViewHolder </br>
 * Description: 添加好友的显示 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 13/11/2016 3:36 PM</br>
 * Update: 13/11/2016 3:36 PM </br>
 **/

public class AddFriendViewHolder extends EasyViewHolder<QueryUserResult> {
  @BindView(R2.id.add_friend_img) ImageView mAddFriendImg;
  @BindView(R2.id.add_friend_username) TextView mAddFriendUsername;
  @BindView(R2.id.add_friend_email) TextView mAddFriendEmail;
  @BindView(R2.id.add_friend_submit) TextView mAddFriendSubmit;

  private Context mContext;

  public AddFriendViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_add_friend);
    ButterKnife.bind(this,itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, QueryUserResult user) {
    if (user != null) {
      Glide.with(mContext)
          .load(user.getAvatar())
          .centerCrop()
          .placeholder(R.mipmap.ic_chat_default)
          .crossFade()
          .into(mAddFriendImg);
      mAddFriendUsername.setText(user.getNickName());
      mAddFriendSubmit.setOnClickListener(v -> {
        //跳转页面

      });
    }
  }
}
