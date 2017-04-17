package com.china.epower.chat.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.china.epower.chat.R;
import tech.jiangtao.support.ui.fragment.GroupsFragment;

/**
 * Class: GroupListActivity </br>
 * Description: 所有群组页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 08/01/2017 2:23 PM</br>
 * Update: 08/01/2017 2:23 PM </br>
 **/
public class GroupListActivity extends BaseActivity {

  @BindView(R.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R.id.toolbar) Toolbar mToolbar;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_groups);
    ButterKnife.bind(this);
    setUpToolbar();
    buildFragment();
  }

  public void setUpToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText("群聊");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
    mToolbar.setNavigationOnClickListener(v -> this.finish());
  }

  public void buildFragment() {
    FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
    GroupsFragment fragment = GroupsFragment.newInstance();
    Bundle bundle = new Bundle();
    fragment.setArguments(bundle);
    fragmentTransaction.add(R.id.groupContainer, fragment);
    fragmentTransaction.commit();
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public static void startGroupList(Context context) {
    Intent intent = new Intent(context, GroupListActivity.class);
    context.startActivity(intent);
  }
}
