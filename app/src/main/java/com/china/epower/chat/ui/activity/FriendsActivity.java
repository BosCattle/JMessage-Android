package com.china.epower.chat.ui.activity;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.china.epower.chat.R;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.ui.fragment.FriendsFragment;
import tech.jiangtao.support.ui.fragment.InvitedFragment;

/**
 * Class: FriendsActivity </br>
 * Description: 添加群组成员 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 20/04/2017 6:49 PM</br>
 * Update: 20/04/2017 6:49 PM </br>
 **/
public class FriendsActivity extends BaseActivity {

  @BindView(R.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R.id.toolbar) Toolbar mToolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_friends);
    ButterKnife.bind(this);
    setUpToolbar();
    buildFragment();
  }

  public void setUpToolbar() {
    if (mToolbar != null) {
      mToolbar.setTitle("");
      mTvToolbar.setText("添加成员");
      setSupportActionBar(mToolbar);
      mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
      mToolbar.setNavigationOnClickListener(
          v -> ActivityCompat.finishAfterTransition(FriendsActivity.this));
    }
  }

  public void buildFragment() {
    FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
    FriendsFragment fragment = FriendsFragment.newInstance();
    Bundle bundle = new Bundle();
    bundle.putString(SupportIM.GROUPID,getIntent().getStringExtra(SupportIM.GROUPID));
    fragment.setArguments(bundle);
    fragmentTransaction.add(R.id.groupMemberContainer, fragment);
    fragmentTransaction.commit();
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }
}
