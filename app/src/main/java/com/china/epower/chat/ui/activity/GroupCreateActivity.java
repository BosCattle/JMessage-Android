package com.china.epower.chat.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.china.epower.chat.R;
import tech.jiangtao.support.kit.annotation.GroupChatRouter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.fragment.GroupCreateFragment;

/**
 * Class: GroupCreateActivity </br>
 * Description: 创建群组 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 2017/3/28 下午3:41</br>
 * Update: 2017/3/28 下午3:41 </br>
 **/
@GroupChatRouter(router = GroupChatActivity.class)
public class GroupCreateActivity extends tech.jiangtao.support.ui.activity.BaseActivity
    implements EasyViewHolder.OnItemClickListener {
  @BindView(R.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  public static final String TAG = GroupCreateActivity.class.getSimpleName();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_create);
    ButterKnife.bind(this);
    setUpToolbar();
    buildFragment();
  }

  public void setUpToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText("创建群");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
    mToolbar.setNavigationOnClickListener(v -> this.finish());
  }


  public void buildFragment() {
    FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
    GroupCreateFragment fragment = GroupCreateFragment.newInstance();
    Bundle bundle = new Bundle();
    fragment.setArguments(bundle);
    fragmentTransaction.add(R.id.groupCreateContainer, fragment);
    fragmentTransaction.commit();
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public static void startGroupCreate(Context context) {
    Intent intent = new Intent(context, GroupCreateActivity.class);
    context.startActivity(intent);
  }

  @Override public void onItemClick(int position, View view) {

  }
}