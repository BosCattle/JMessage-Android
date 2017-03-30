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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.model.group.GroupData;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;
import tech.jiangtao.support.ui.view.RecyclerViewScrollview;
import tech.jiangtao.support.ui.viewholder.GroupListViewHolder;

/**
 * Class: GroupListActivity </br>
 * Description: 所有群组页面 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 08/01/2017 2:23 PM</br>
 * Update: 08/01/2017 2:23 PM </br>
 **/
public class GroupListActivity extends BaseActivity implements
    SwipeRefreshLayout.OnRefreshListener,EasyViewHolder.OnItemClickListener {

  @BindView(R2.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R2.id.toolbar) Toolbar mToolbar;
  @BindView(R2.id.group_image) ImageView mGroupImage;
  @BindView(R2.id.group_list) RecyclerView mGroupList;
  @BindView(R2.id.group_swift_refresh) SwipeRefreshLayout mGroupSwiftRefresh;
  private BaseEasyAdapter mBaseEasyAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_list);
    ButterKnife.bind(this);
    setUpToolbar();
    setUpRefresh();
    setUpAdapter();
  }

  private void setUpAdapter() {
    mBaseEasyAdapter = new BaseEasyAdapter(this);
    mBaseEasyAdapter.bind(GroupData.class,GroupListViewHolder.class);
    mBaseEasyAdapter.setOnClickListener(this);
    mGroupList.setHasFixedSize(true);
    mGroupList.addItemDecoration(RecyclerViewUtils.buildItemDecoration(this));
    mGroupList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    mGroupList.setAdapter(mBaseEasyAdapter);
    for (int i = 0;i<10;i++){
      GroupData data = new GroupData();
      data.groupAvatar = "";
      data.groupName = "群聊"+i;
      mBaseEasyAdapter.add(data);
    }
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
    mGroupSwiftRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light,
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
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        mGroupSwiftRefresh.setRefreshing(false);
      }
    },3000);
  }

  @Override public void onItemClick(int position, View view) {

  }
}
