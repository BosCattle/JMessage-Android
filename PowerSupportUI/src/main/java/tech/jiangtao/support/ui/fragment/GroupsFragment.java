package tech.jiangtao.support.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.annotation.GroupChatRouter;
import tech.jiangtao.support.kit.realm.GroupRealm;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.api.ApiService;
import tech.jiangtao.support.ui.api.service.GroupServiceApi;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import tech.jiangtao.support.ui.viewholder.GroupListViewHolder;
import work.wanghao.simplehud.SimpleHUD;

/**
 * Class: GroupsFragment </br>
 * Description: 群组列表 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 17/04/2017 10:15 PM</br>
 * Update: 17/04/2017 10:15 PM </br>
 **/
public class GroupsFragment extends BaseFragment
    implements SwipeRefreshLayout.OnRefreshListener, EasyViewHolder.OnItemClickListener {

  @BindView(R2.id.group_image) ImageView mGroupImage;
  @BindView(R2.id.group_list) RecyclerView mGroupList;
  @BindView(R2.id.group_swift_refresh) SwipeRefreshLayout mGroupSwiftRefresh;
  private BaseEasyAdapter mBaseEasyAdapter;
  private GroupServiceApi mGroupServiceApi;
  private AppPreferences mAppPreferences;
  private List<GroupRealm> mGroupRealms = new ArrayList<>();
  private Realm mRealm;

  public static GroupsFragment newInstance() {
    return new GroupsFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, getView());
    setUpRefresh();
    setUpAdapter();
    getLocalGroupData();
    return getView();
  }

  @Override public int layout() {
    return R.layout.fragment_group_list;
  }

  private void getLocalGroupData() {
    mAppPreferences = new AppPreferences(getContext());
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
    mRealm.executeTransaction(realm -> {
      RealmResults<GroupRealm> realmResult = realm.where(GroupRealm.class).findAll();
      for (int i = 0; i < realmResult.size(); i++) {
        mGroupRealms.add(realmResult.get(i));
      }
    });
    mBaseEasyAdapter.notifyDataSetChanged();
    loadGroupData();
  }

  private void loadGroupData() {
    mGroupServiceApi = ApiService.getInstance().createApiService(GroupServiceApi.class);
    String name = null;
    try {
      name = StringSplitUtil.splitDivider(mAppPreferences.getString(SupportIM.USER_ID));
    } catch (ItemNotFoundException e) {
      e.printStackTrace();
    }
    mGroupServiceApi.groups(name)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(list -> {
          mBaseEasyAdapter.clear();
          mGroupRealms.clear();
          mGroupRealms.addAll(list);
          mBaseEasyAdapter.appendAll(list);
          mBaseEasyAdapter.notifyDataSetChanged();
          writeGroupRealm(list);
        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
            SimpleHUD.showErrorMessage(getContext(), throwable.getLocalizedMessage());
          }
        });
  }

  private void writeGroupRealm(List<GroupRealm> realms) {
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
    mRealm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(realms));
  }

  private void setUpAdapter() {
    mBaseEasyAdapter = new BaseEasyAdapter(getContext());
    mBaseEasyAdapter.bind(GroupRealm.class, GroupListViewHolder.class);
    mBaseEasyAdapter.setOnClickListener(this);
    mGroupList.setHasFixedSize(true);
    mGroupList.addItemDecoration(RecyclerViewUtils.buildItemDecoration(getContext()));
    mGroupList.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    mGroupList.setAdapter(mBaseEasyAdapter);
    mBaseEasyAdapter.notifyDataSetChanged();
  }

  public void setUpRefresh() {
    mGroupSwiftRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
        android.R.color.holo_green_light, android.R.color.holo_orange_light,
        android.R.color.holo_red_light);
    mGroupSwiftRefresh.setDistanceToTriggerSync(300);
    mGroupSwiftRefresh.setProgressBackgroundColorSchemeColor(Color.WHITE);
    mGroupSwiftRefresh.setSize(SwipeRefreshLayout.LARGE);
    mGroupSwiftRefresh.setOnRefreshListener(this);
  }

  @Override public void onRefresh() {
    new Handler().postDelayed(() -> mGroupSwiftRefresh.setRefreshing(false), 3000);
  }

  @Override public void onItemClick(int position, View view) {
    Class clazz = getActivity().getClass();
    Annotation[] annotations = clazz.getAnnotations();
    for (int i = 0; i < annotations.length; i++) {
      if (annotations[i] instanceof GroupChatRouter) {
        GroupChatRouter groupChatRouter = (GroupChatRouter) annotations[i];
        Intent intent = new Intent(getActivity(), groupChatRouter.router());
        intent.putExtra(SupportIM.GROUP, mGroupRealms.get(position));
        startActivity(intent);
      }
    }
  }
}
