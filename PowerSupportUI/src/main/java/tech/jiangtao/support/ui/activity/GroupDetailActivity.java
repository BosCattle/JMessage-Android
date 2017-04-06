package tech.jiangtao.support.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;

/**
 * Class: GroupDetailActivity </br>
 * Description: 查看群详细页 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 2017/4/6 下午2:27</br>
 * Update: 2017/4/6 下午2:27 </br>
 **/
public class GroupDetailActivity extends BaseActivity
    implements EasyViewHolder.OnItemClickListener {

  @BindView(R2.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R2.id.toolbar) Toolbar mToolbar;
  @BindView(R2.id.group_detail_recycle) RecyclerView mGroupDetailRecycle;
  @BindView(R2.id.delete_group_button) AppCompatButton mDeleteGroupButton;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_detail);
    ButterKnife.bind(this);
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public static void startGroupDetail(Activity activity) {
    Intent intent = new Intent(activity, GroupDetailActivity.class);
    activity.startActivity(intent);
  }

  @Override public void onItemClick(int position, View view) {

  }
}
