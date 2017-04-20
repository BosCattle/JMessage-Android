package tech.jiangtao.support.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import tech.jiangtao.support.ui.viewholder.GroupAdditionViewHolder;

/**
 * Class: FriendsFragment </br>
 * Description:  </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 20/04/2017 5:55 PM</br>
 * Update: 20/04/2017 5:55 PM </br>
 **/
public class FriendsFragment extends BaseFragment implements EasyViewHolder.OnItemClickListener {

  @BindView(R2.id.group_member_addition) RecyclerView mGroupMemberAddition;
  private List<ContactRealm> mContactRealms;
  private BaseEasyAdapter mBaseEasyAdapter;

  public static FriendsFragment newInstance() {
    return new FriendsFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, getView());
    setAdapter();
    updateItems();
    return getView();
  }

  private void setAdapter() {
    mContactRealms = new ArrayList<>();
    mBaseEasyAdapter = new BaseEasyAdapter(getContext());
    mBaseEasyAdapter.bind(ContactRealm.class, GroupAdditionViewHolder.class);
    mBaseEasyAdapter.setOnClickListener(this);
    mGroupMemberAddition.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mGroupMemberAddition.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
    mGroupMemberAddition.setAdapter(mBaseEasyAdapter);
  }

  public void updateItems(){
    Realm realm = Realm.getDefaultInstance();
    RealmResults<ContactRealm> result = realm.where(ContactRealm.class).findAll();
    mContactRealms.addAll(result);
    mBaseEasyAdapter.appendAll(mContactRealms);
    mBaseEasyAdapter.notifyDataSetChanged();
  }

  @Override public int layout() {
    return R.layout.fragment_friends;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }

  @Override public void onItemClick(int position, View view) {

  }
}
