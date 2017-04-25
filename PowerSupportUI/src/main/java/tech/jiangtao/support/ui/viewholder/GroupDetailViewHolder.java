package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.kit.model.group.Groups;
import tech.jiangtao.support.ui.pattern.ConstrutContact;

/**
 * Class: GroupMemberViewHolder </br>
 * Description: 创建群时，人员选择 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 15/01/2017 11:14 PM</br>
 * Update: 15/01/2017 11:14 PM </br>
 **/
public class GroupDetailViewHolder extends tech.jiangtao.support.ui.adapter.ContactViewHolder {

  @BindView(R2.id.group_member_avatar) CircleImageView mGroupMemberAvatar;
  @BindView(R2.id.group_member_name) TextView mGroupMemberName;
  @BindView(R2.id.group_member_checkbox) AppCompatCheckBox mGroupMemberCheckbox;
  private Context mContext;

  public GroupDetailViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_group_member);
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
