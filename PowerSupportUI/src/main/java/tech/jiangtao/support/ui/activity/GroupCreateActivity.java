package tech.jiangtao.support.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;

public class GroupCreateActivity extends BaseActivity implements SearchView.OnQueryTextListener {

  @BindView(R2.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R2.id.toolbar) Toolbar mToolbar;
  @BindView(R2.id.group_add) RecyclerView mGroupAdd;
  @BindView(R2.id.frame) FrameLayout mFrame;
  @BindView(R2.id.activity_group_create) RelativeLayout mActivityGroupCreate;
  @BindView(R2.id.group_edit) SearchView mSearchView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_create);
    ButterKnife.bind(this);
    setUpToolbar();
    setUpEditText();
  }

  private void setUpEditText() {
    mSearchView.setIconifiedByDefault(false);
    //为该SearchView组件设置事件监听器
    mSearchView.setOnQueryTextListener(this);
    //设置该SearchView显示搜索按钮
    mSearchView.setSubmitButtonEnabled(false);
    //设置该SearchView内默认显示的提示文本
    mSearchView.setQueryHint("查找");
  }

  public void setUpToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText("创建群");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
    mToolbar.setNavigationOnClickListener(v -> this.finish());
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public static void startGroupCreate(Context context) {
    Intent intent = new Intent(context, GroupCreateActivity.class);
    context.startActivity(intent);
  }

  @Override public boolean onQueryTextSubmit(String query) {
    return false;
  }

  @Override public boolean onQueryTextChange(String newText) {
    return false;
  }
}
