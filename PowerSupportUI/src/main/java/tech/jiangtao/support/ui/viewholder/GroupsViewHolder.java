package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.model.group.Groups;
import tech.jiangtao.support.ui.pattern.ConstrutContact;

/**
 * Class: GroupMemberViewHolder </br>
 * Description: 创建群时，人员选择 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 15/01/2017 11:14 PM</br>
 * Update: 15/01/2017 11:14 PM </br>
 **/
public class GroupsViewHolder extends tech.jiangtao.support.ui.adapter.ContactViewHolder {

  @BindView(R2.id.group_avatar) CircleImageView mGroupMemberAvatar;
  @BindView(R2.id.group_name) TextView mGroupMemberName;
  private Context mContext;

  public GroupsViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_group);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, ConstrutContact l) {
    Groups groups = (Groups) l.mObject;
    Glide.with(mContext)
        .load(groups.creator)
        .placeholder(R.mipmap.ic_launcher)
        .centerCrop()
        .into(mGroupMemberAvatar);
    mGroupMemberName.setText(groups.roomName);
  }
}
