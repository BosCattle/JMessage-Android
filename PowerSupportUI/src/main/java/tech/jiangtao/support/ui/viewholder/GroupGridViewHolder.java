package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.model.ChatExtraModel;
import tech.jiangtao.support.ui.model.group.Friends;

/**
 * Class: GroupGridViewHolder </br>
 * Description: 群组人员 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 2017/4/7 上午11:35</br>
 * Update: 2017/4/7 上午11:35 </br>
 **/

public class GroupGridViewHolder extends EasyViewHolder<Friends> {
  @BindView(R2.id.group_grid) ImageView mGroupGrid;
  private Context mContext;

  public GroupGridViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_group_gridview);
    ButterKnife.bind(this, itemView);
    mContext = context;
  }

  @Override public void bindTo(int position, Friends friends) {
    Glide.with(mContext)
        .load(friends.avatar)
        .centerCrop()
        .placeholder(R.mipmap.ic_launcher)
        .crossFade()
        .into(mGroupGrid);
  }
}
