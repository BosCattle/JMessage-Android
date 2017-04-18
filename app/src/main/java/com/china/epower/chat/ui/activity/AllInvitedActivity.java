package com.china.epower.chat.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.china.epower.chat.R;
import tech.jiangtao.support.ui.fragment.InvitedFragment;

/**
 * Class: NewFriendActivity </br>
 * Description: 有新的好友需求 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 08/01/2017 2:42 PM</br>
 * Update: 08/01/2017 2:42 PM </br>
 **/
public class AllInvitedActivity extends BaseActivity {

  private static final String TAG = AllInvitedActivity.class.getName();
  @BindView(R.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.friendContainer) FrameLayout mFriendContainer;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_friends);
    ButterKnife.bind(this);
    setUpToolbar();
    buildFragment();
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

  public void buildFragment() {
    FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
    InvitedFragment fragment = InvitedFragment.newInstance();
    Bundle bundle = new Bundle();
    fragment.setArguments(bundle);
    fragmentTransaction.add(R.id.friendContainer, fragment);
    fragmentTransaction.commit();
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public static void startAllInviteInfo(Context context) {
    Intent intent = new Intent(context, AllInvitedActivity.class);
    context.startActivity(intent);
  }
}
