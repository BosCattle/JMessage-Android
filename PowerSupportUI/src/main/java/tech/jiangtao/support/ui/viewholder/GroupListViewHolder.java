package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;
import tech.jiangtao.support.kit.realm.GroupRealm;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.model.group.Group;
import tech.jiangtao.support.ui.model.group.GroupData;

/**
 * Class:  </br>
 * Description: 所有群组ViewHolder </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 14/01/2017 8:26 PM</br>
 * Update: 14/01/2017 8:26 PM </br>
 **/

public class GroupListViewHolder extends EasyViewHolder<GroupRealm> {

  @BindView(R2.id.group_avatar) CircleImageView mGroupAvatar;
  @BindView(R2.id.group_name) TextView mGroupName;
  @BindView(R2.id.group_detail) TextView mGroupDetail;
  private Context mContext;

  public GroupListViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_group);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, GroupRealm groupData) {
    Glide.with(mContext)
        .load(groupData.getAvatar())
        .centerCrop()
        .placeholder(R.mipmap.ic_launcher)
        .crossFade()
        .into(mGroupAvatar);
    mGroupName.setText(groupData.getName());
    mGroupDetail.setText(groupData.getDescription());
  }
}
