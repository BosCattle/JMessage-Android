package tech.jiangtao.support.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.*;
import tech.jiangtao.support.ui.model.User;
import tech.jiangtao.support.ui.model.group.Friends;
import tech.jiangtao.support.ui.pattern.ConstrutContact;


/**
 * Class: GroupDetailMemberViewHolder </br>
 * Description: 群详情中的群成员信息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 2017/4/7 上午10:55</br>
 * Update: 2017/4/7 上午10:55 </br>
 **/

public class GroupDetailMemberViewHolder
    extends tech.jiangtao.support.ui.adapter.ContactViewHolder {

  @BindView(R2.id.group_detail_member) RecyclerView mGroupDetailMember;
  @BindView(R2.id.group_detail_arrow) ImageView mGroupDetailArrow;
  private Context mContext;
  private BaseEasyAdapter mBaseEasyAdapter;
  private ArrayList<User> mFriends;

  public GroupDetailMemberViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_group_detail_member);
    ButterKnife.bind(this, itemView);
    mContext = context;
    mBaseEasyAdapter = new BaseEasyAdapter(mContext);
    mBaseEasyAdapter.viewHolderFactory(new BaseEasyViewHolderFactory(mContext));
    mBaseEasyAdapter.bind(Friends.class, GroupGridViewHolder.class);
    mGroupDetailMember.setLayoutManager(new GridLayoutManager(mContext, 4));
    mGroupDetailMember.setAdapter(mBaseEasyAdapter);
    mFriends = new ArrayList<>();
  }

  @Override public void bindTo(int position, ConstrutContact l) {
    if (l.mDatas!=null) {
      for (Object object : l.mDatas) {
        User user = (User) object;
        mFriends.add(user);
      }
      mBaseEasyAdapter.clear();
      mBaseEasyAdapter.addAll(mFriends);
    }
  }
}
