package com.china.epower.chat.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.china.epower.chat.R;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.annotation.FriendsRouter;
import tech.jiangtao.support.kit.realm.GroupRealm;
import tech.jiangtao.support.ui.fragment.GroupDetailFragment;

/**
 * Class: GroupDetailActivity </br>
 * Description: 查看群详细页 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 2017/4/6 下午2:27</br>
 * Update: 2017/4/6 下午2:27 </br>
 **/
@FriendsRouter(router = FriendsActivity.class)
public class GroupDetailActivity extends BaseActivity {

  public static final String TAG = GroupDetailActivity.class.getSimpleName();
  @BindView(R.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R.id.toolbar) Toolbar mToolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_detail);
    ButterKnife.bind(this);
    setUpToolbar();
    buildFragment();
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public void setUpToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText("群详情");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
    mToolbar.setNavigationOnClickListener(v -> this.finish());
  }

  public void buildFragment() {
    FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
    GroupDetailFragment fragment = GroupDetailFragment.newInstance();
    Bundle bundle = new Bundle();
    GroupRealm groupRealm = getIntent().getParcelableExtra(SupportIM.GROUP);
    bundle.putSerializable(SupportIM.GROUP, groupRealm);
    fragment.setArguments(bundle);
    fragmentTransaction.add(R.id.groupDetailContainer, fragment);
    fragmentTransaction.commit();
  }

  public static void startGroupsDetail(Activity act, GroupRealm mGroups) {
    Intent intent = new Intent(act, GroupDetailActivity.class);
    intent.putExtra(SupportIM.GROUP, mGroups);
    act.startActivity(intent);
  }
}
