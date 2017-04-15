package tech.jiangtao.support.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.BaseEasyViewHolderFactory;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.api.ApiService;
import tech.jiangtao.support.ui.api.service.UserServiceApi;
import tech.jiangtao.support.ui.model.User;
import tech.jiangtao.support.ui.model.group.Groups;
import tech.jiangtao.support.ui.model.group.InvitedInfo;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import tech.jiangtao.support.ui.viewholder.NewFriendViewHolder;

/**
 * Class: NewFriendActivity </br>
 * Description: 有新的好友需求 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 08/01/2017 2:42 PM</br>
 * Update: 08/01/2017 2:42 PM </br>
 **/
public class AllInvitedActivity extends BaseActivity implements EasyViewHolder.OnItemClickListener {

  public static final String NEW_FLAG = "add_friend";
  private static final String TAG = AllInvitedActivity.class.getName();
  @BindView(R2.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R2.id.toolbar) Toolbar mToolbar;
  @BindView(R2.id.new_friend_page) RecyclerView mNewFriendPage;
  private BaseEasyAdapter mBaseEasyAdapter;
  private UserServiceApi mUserServiceApi;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_friend);
    ButterKnife.bind(this);
    setUpToolbar();
    setUpAdapter();
    updateRequest();
  }

  public void updateRequest(){
    AppPreferences mAppPreferences = new AppPreferences(this);
    String userId = null;
    try {
      userId = mAppPreferences.getString(SupportIM.USER_ID);
    } catch (ItemNotFoundException e) {
      e.printStackTrace();
    }
    mUserServiceApi.getAllInvite(StringSplitUtil.splitDivider(userId))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(list -> {
          mBaseEasyAdapter.addAll(list);
          mBaseEasyAdapter.notifyDataSetChanged();
        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
            LogUtils.d(TAG, throwable.getLocalizedMessage());
          }
        });
  }

  private void setUpAdapter() {
    mUserServiceApi = ApiService.getInstance().createApiService(UserServiceApi.class);
    mBaseEasyAdapter = new BaseEasyAdapter(this);
    mBaseEasyAdapter.viewHolderFactory(new BaseEasyViewHolderFactory(this));
    mBaseEasyAdapter.bind(InvitedInfo.class, NewFriendViewHolder.class);
    mBaseEasyAdapter.setOnClickListener(this);
    mNewFriendPage.setHasFixedSize(true);
    mNewFriendPage.addItemDecoration(RecyclerViewUtils.buildItemDecoration(this));
    mNewFriendPage.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    mNewFriendPage.setAdapter(mBaseEasyAdapter);
    mBaseEasyAdapter.add(new InvitedInfo(new User(),new Groups()));
    mBaseEasyAdapter.notifyDataSetChanged();
  }

  public void setUpToolbar() {
    if (mToolbar != null) {
      mToolbar.setTitle("");
      mTvToolbar.setText("好友请求");
      setSupportActionBar(mToolbar);
      mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
      mToolbar.setNavigationOnClickListener(
          v -> ActivityCompat.finishAfterTransition(AllInvitedActivity.this));
    }
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public static void startAllInviteInfo(Context context) {
    Intent intent = new Intent(context, AllInvitedActivity.class);
    context.startActivity(intent);
  }

  @Override public void onItemClick(int position, View view) {

  }
}
