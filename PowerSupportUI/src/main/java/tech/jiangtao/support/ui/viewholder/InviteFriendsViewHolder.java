package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;
import tech.jiangtao.support.kit.eventbus.InviteFriend;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.activity.InviteFriendsActivity;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.model.group.Friends;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: InviteFriendsViewHolder </br>
 * Description: 邀请好友入群 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 2017/4/7 下午9:16</br>
 * Update: 2017/4/7 下午9:16 </br>
 **/

public class InviteFriendsViewHolder extends EasyViewHolder<Friends> {
  @BindView(R2.id.friend_member_avatar) CircleImageView mFriendMemberAvatar;
  @BindView(R2.id.friend_member_name) TextView mFriendMemberName;
  @BindView(R2.id.friend_member_checkbox) AppCompatCheckBox mFriendMemberCheckbox;

  private Context mContext;

  public InviteFriendsViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_group_invite);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, Friends friends) {
    if (friends != null) {
      Glide.with(mContext)
          .load(friends.avatar)
          .placeholder(R.mipmap.ic_launcher)
          .centerCrop()
          .into(mFriendMemberAvatar);
      mFriendMemberName.setText(friends.nickName);
    }
    mFriendMemberCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        if (checked){
          InviteFriendsActivity.mFriends.add(friends);
        }else {
          InviteFriendsActivity.mFriends.remove(friends);
        }
      }
    });
  }
}
