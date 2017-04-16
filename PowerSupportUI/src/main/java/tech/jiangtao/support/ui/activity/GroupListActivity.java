package tech.jiangtao.support.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.SupportIM;
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
 * Class: GroupListActivity </br>
 * Description: 所有群组页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 08/01/2017 2:23 PM</br>
 * Update: 08/01/2017 2:23 PM </br>
 **/
public class GroupListActivity extends BaseActivity
    implements SwipeRefreshLayout.OnRefreshListener, EasyViewHolder.OnItemClickListener {

  @BindView(R2.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R2.id.toolbar) Toolbar mToolbar;
  @BindView(R2.id.group_image) ImageView mGroupImage;
  @BindView(R2.id.group_list) RecyclerView mGroupList;
  @BindView(R2.id.group_swift_refresh) SwipeRefreshLayout mGroupSwiftRefresh;
  private BaseEasyAdapter mBaseEasyAdapter;
  private GroupServiceApi mGroupServiceApi;
  private AppPreferences mAppPreferences;
  private List<GroupRealm> mGroupRealms = new ArrayList<>();
  private Realm mRealm;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_list);
    ButterKnife.bind(this);
    setUpToolbar();
    setUpRefresh();
    setUpAdapter();
    getLocalGroupData();
  }

  private void getLocalGroupData() {
    mAppPreferences = new AppPreferences(this);
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
            SimpleHUD.showErrorMessage(GroupListActivity.this, throwable.getLocalizedMessage());
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
    mBaseEasyAdapter = new BaseEasyAdapter(this);
    mBaseEasyAdapter.bind(GroupRealm.class, GroupListViewHolder.class);
    mBaseEasyAdapter.setOnClickListener(this);
    mGroupList.setHasFixedSize(true);
    mGroupList.addItemDecoration(RecyclerViewUtils.buildItemDecoration(this));
    mGroupList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    mGroupList.setAdapter(mBaseEasyAdapter);
    mBaseEasyAdapter.notifyDataSetChanged();
  }

  public void setUpToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText("群聊");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
    mToolbar.setNavigationOnClickListener(v -> this.finish());
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

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public static void startGroupList(Context context) {
    Intent intent = new Intent(context, GroupListActivity.class);
    context.startActivity(intent);
  }

  @Override public void onRefresh() {
    new Handler().postDelayed(() -> mGroupSwiftRefresh.setRefreshing(false), 3000);
  }

  @Override public void onItemClick(int position, View view) {
    GroupChatActivity.startChat(this, mGroupRealms.get(position));
  }
}
