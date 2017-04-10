package tech.jiangtao.support.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.eventbus.InviteFriend;
import tech.jiangtao.support.kit.eventbus.InvitedFriendToGroup;
import tech.jiangtao.support.kit.userdata.SimpleCGroup;
import tech.jiangtao.support.kit.userdata.SimpleGroupInvited;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.BaseEasyViewHolderFactory;
import tech.jiangtao.support.ui.adapter.ContactAdapter;
import tech.jiangtao.support.ui.api.ApiService;
import tech.jiangtao.support.ui.api.service.UserServiceApi;
import tech.jiangtao.support.ui.model.User;
import tech.jiangtao.support.ui.model.group.Friends;
import tech.jiangtao.support.ui.model.type.ContactType;
import tech.jiangtao.support.ui.pattern.ConstrutContact;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import tech.jiangtao.support.ui.viewholder.GroupGridViewHolder;
import tech.jiangtao.support.ui.viewholder.InviteFriendsViewHolder;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: InviteFriendsActivity </br>
 * Description: 邀请好友入群页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 2017/4/7 下午9:32</br>
 * Update: 2017/4/7 下午9:32 </br>
 **/
public class InviteFriendsActivity extends BaseActivity {

  public static final String TAG = InviteFriendsActivity.class.getSimpleName();
  public static final String MUCJID = "mucJid";
  @BindView(R2.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R2.id.toolbar) Toolbar mToolbar;
  @BindView(R2.id.invite_group_recycle) RecyclerView mInviteGroupRecycle;
  private BaseEasyAdapter mBaseEasyAdapter;
  // TODO: 2017/4/7 泄漏
  public static List<String> mFriends = new ArrayList<>();
  private SimpleGroupInvited mSimpleGroupInvited;
  private String mucJid;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_invite_friends);
    ButterKnife.bind(this);
    init();
  }

  public void init() {
    //mSimpleGroupInvited = new SimpleGroupInvited();
    mucJid = getIntent().getStringExtra(MUCJID);
    AppPreferences mAppPreferences = new AppPreferences(this);
    String name = null;
    try {
      name = mAppPreferences.getString("userJid");
      name = StringSplitUtil.splitDivider(name);
    } catch (ItemNotFoundException e) {
      e.printStackTrace();
    }
    LogUtils.d(TAG, name);
    UserServiceApi mUserServiceApi =
        ApiService.getInstance().createApiService(UserServiceApi.class);
    setUpToolbar();
    setUpAdapter();
    mUserServiceApi.queryUserList(name)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(list -> {
          mBaseEasyAdapter.clear();
          mBaseEasyAdapter.appendAll(list);
          mBaseEasyAdapter.notifyDataSetChanged();
        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
            LogUtils.d(TAG, throwable.getLocalizedMessage());
          }
        });
  }

  private void setUpAdapter() {
    mBaseEasyAdapter = new BaseEasyAdapter(this);
    mBaseEasyAdapter.viewHolderFactory(new BaseEasyViewHolderFactory(this));
    mBaseEasyAdapter.bind(Friends.class, InviteFriendsViewHolder.class);
    mInviteGroupRecycle.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    mInviteGroupRecycle.addItemDecoration(RecyclerViewUtils.buildItemDecoration(this));
    mInviteGroupRecycle.setAdapter(mBaseEasyAdapter);
  }

  public void setUpToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText("邀请好友");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
    mToolbar.setNavigationOnClickListener(v -> this.finish());
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public static void startInviteFriends(Activity activity, String mucJid) {
    Intent intent = new Intent(activity, InviteFriendsActivity.class);
    intent.putExtra(MUCJID, mucJid);
    activity.startActivity(intent);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_group_invite, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_group_invite) {
      // 发送邀请请求
      ArrayList<String> userjid = new ArrayList<>();
      if (mFriends.size() != 0) {
        for (String friend : mFriends) {
          userjid.add(friend);
        }
        String reason = "妈的智障";
        InvitedFriendToGroup ift = new InvitedFriendToGroup(mucJid, userjid, reason);
        HermesEventBus.getDefault().post(ift);
      }
    }
    return super.onOptionsItemSelected(item);
  }
}
