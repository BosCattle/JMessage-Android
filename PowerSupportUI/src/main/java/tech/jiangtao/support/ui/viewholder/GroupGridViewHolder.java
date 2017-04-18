package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.model.User;
import tech.jiangtao.support.ui.model.group.Friends;
import tech.jiangtao.support.ui.model.type.TransportType;
import tech.jiangtao.support.ui.utils.ResourceAddress;

/**
 * Class: GroupGridViewHolder </br>
 * Description: 群组人员 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 2017/4/7 上午11:35</br>
 * Update: 2017/4/7 上午11:35 </br>
 **/

public class GroupGridViewHolder extends EasyViewHolder<ContactRealm> {
  @BindView(R2.id.group_grid) ImageView mGroupGrid;
  private Context mContext;

  public GroupGridViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_group_gridview);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, ContactRealm friends) {
    Glide.with(mContext)
        .load(ResourceAddress.url(friends.getAvatar(), TransportType.AVATAR))
        .centerCrop()
        .placeholder(R.mipmap.ic_group_add_member)
        .crossFade()
        .into(mGroupGrid);
  }
}
