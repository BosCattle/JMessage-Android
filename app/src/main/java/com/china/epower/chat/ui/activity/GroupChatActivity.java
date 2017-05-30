package com.china.epower.chat.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.china.epower.chat.R;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.realm.GroupRealm;
import tech.jiangtao.support.ui.fragment.GroupChatFragment;

public class GroupChatActivity extends tech.jiangtao.support.ui.activity.BaseActivity {

  public static final String TAG = GroupChatActivity.class.getSimpleName();
  @BindView(R.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  private GroupRealm mGroups;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);
    ButterKnife.bind(this);
    init();
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  private void init() {
    mGroups = getIntent().getParcelableExtra(SupportIM.GROUP);
    buildFragment();
    setUpToolbar();
  }

  public void buildFragment() {
    FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
    GroupChatFragment fragment = GroupChatFragment.newInstance();
    Bundle bundle = new Bundle();
    bundle.putSerializable(SupportIM.GROUP, mGroups);
    fragment.setArguments(bundle);
    fragmentTransaction.add(R.id.chat_func_detail, fragment);
    fragmentTransaction.commit();
  }

  public void setUpToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText(mGroups != null && mGroups.getName() != null ? mGroups.getName() : "");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
    mToolbar.setNavigationOnClickListener(
        v -> ActivityCompat.finishAfterTransition(GroupChatActivity.this));
  }

  public static void startChat(Activity activity, GroupRealm group) {
    Intent intent = new Intent(activity, GroupChatActivity.class);
    intent.putExtra(SupportIM.GROUP, group);
    activity.startActivity(intent);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_group_detail, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_group_detail) {
      GroupDetailActivity.startGroupsDetail(GroupChatActivity.this,mGroups);
    }
    return super.onOptionsItemSelected(item);
  }
}
