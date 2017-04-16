package tech.jiangtao.support.ui.activity;

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
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.fragment.ChatFragment;
import tech.jiangtao.support.ui.fragment.GroupChatFragment;
import tech.jiangtao.support.ui.model.group.Friends;
import tech.jiangtao.support.ui.model.group.Group;
import tech.jiangtao.support.ui.model.group.Groups;

public class GroupChatActivity extends BaseActivity {

  public static final String TAG = GroupChatActivity.class.getSimpleName();
  @BindView(R2.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R2.id.toolbar) Toolbar mToolbar;
  private Group mGroups;

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
    mGroups = getIntent().getParcelableExtra(GroupChatFragment.USER_FRIEND);
    buildFragment();
    setUpToolbar();
  }

  public void buildFragment() {
    FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
    GroupChatFragment fragment = GroupChatFragment.newInstance();
    Bundle bundle = new Bundle();
    bundle.putParcelable(GroupChatFragment.USER_FRIEND, mGroups);
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

  public static void startChat(Activity activity, Group group) {
    Intent intent = new Intent(activity, GroupChatActivity.class);
    intent.putExtra(GroupChatFragment.USER_FRIEND, group);
    activity.startActivity(intent);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_group_detail, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_group_detail) {
      GroupDetailActivity.startGroupDetail(GroupChatActivity.this,mGroups);
    }
    return super.onOptionsItemSelected(item);
  }
}
