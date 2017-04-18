package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.pattern.ConstrutContact;

/**
 * Class: GroupMemberViewHolder </br>
 * Description: 群人员选择 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 15/01/2017 11:14 PM</br>
 * Update: 15/01/2017 11:14 PM </br>
 **/
// TODO: 2017/4/7  type：邀请用户加入群
public class GroupNameViewHolder extends tech.jiangtao.support.ui.adapter.ContactViewHolder {

  @BindView(R2.id.items_group_avatar) CircleImageView mItemsGroupAvatar;
  @BindView(R2.id.items_group_name) TextView mItemsGroupName;
  @BindView(R2.id.items_group_tab) TextView mItemsGroupTab;
  @BindView(R2.id.items_group_arrow) ImageView mItemsGroupArrow;
  private Context mContext;

  public GroupNameViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_group_avatar);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, ConstrutContact l) {
    mItemsGroupName.setText(l.mTitle);
    mItemsGroupAvatar.setImageResource(R.mipmap.ic_mipmap_default_image);
    mItemsGroupTab.setText("群介绍...");
  }
}
