package tech.jiangtao.support.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.realm.GroupRealm;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ContactAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.model.User;
import tech.jiangtao.support.ui.model.type.ContactType;
import tech.jiangtao.support.ui.pattern.ConstrutContact;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;

/**
 * Class: GroupDetailFragment </br>
 * Description: 群组详情页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 18/04/2017 3:15 PM</br>
 * Update: 18/04/2017 3:15 PM </br>
 **/
public class GroupDetailFragment extends BaseFragment
    implements EasyViewHolder.OnItemClickListener {

  public static final String TAG = GroupDetailFragment.class.getSimpleName();
  @BindView(R2.id.group_detail_recycle) RecyclerView mGroupDetailRecycle;
  @BindView(R2.id.delete_group_button) AppCompatButton mDeleteGroupButton;
  private ContactAdapter mContactAdapter;
  private ArrayList<ConstrutContact> mConstrutContact;
  private List<User> mUsers = new ArrayList<>();
  private GroupRealm mGroupRealm;

  public static GroupDetailFragment newInstance() {
    return new GroupDetailFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, getView());
    setUpAdapter();
    updateGroupData();
    return getView();
  }

  @Override public int layout() {
    return R.layout.fragment_group_detail;
  }

  private void updateGroupData() {
    mGroupRealm  = getArguments().getParcelable(SupportIM.GROUP);
    mConstrutContact.clear();
    mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_GROUP_DETAIL_HEAD)
        .title(mGroupRealm.getName())
        .build());
    mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_GROUP_MEMBER)
        .title(mGroupRealm.getGroupId())
        .datas(null)
        .build());
    mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_GROUP_RADIO).build());
    mConstrutContact.add(
        new ConstrutContact.Builder().type(ContactType.TYPE_GROUP_VALUE).title("清空历史记录").build());
    mConstrutContact.add(new ConstrutContact.Builder().type(ContactType.TYPE_GROUP_VALUE)
        .title("昵称")
        .subtitle(mGroupRealm.getDescription() != null ? mGroupRealm.getDescription() : "")
        .build());
    mContactAdapter.notifyDataSetChanged();
  }

  /**
   * 更新和完善群信息的。
   */
  private void setUpAdapter() {
    mConstrutContact = new ArrayList<>();
    mContactAdapter = new ContactAdapter(getContext(), mConstrutContact);
    mContactAdapter.setOnClickListener(this);
    mGroupDetailRecycle.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
    mGroupDetailRecycle.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mGroupDetailRecycle.setAdapter(mContactAdapter);
  }

  @Override public void onItemClick(int position, View view) {

  }
}
