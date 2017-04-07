package tech.jiangtao.support.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.ContactAdapter;
import tech.jiangtao.support.ui.adapter.EasyViewHolder;
import tech.jiangtao.support.ui.model.type.ContactType;
import tech.jiangtao.support.ui.pattern.ConstrutContact;
import tech.jiangtao.support.ui.utils.RecyclerViewUtils;

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
  private ContactAdapter mContactAdapter;
  private ArrayList<ConstrutContact> mConstrutContact;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_detail);
    ButterKnife.bind(this);
    setUpToolbar();
    setUpAdapter();
    updateGroupData();
  }

  private void updateGroupData() {
    mConstrutContact.clear();
    mConstrutContact.add(new ConstrutContact.Builder()
            .type(ContactType.TYPE_GROUP_DETAIL_HEAD)
            .title("群名").build());
    mConstrutContact.add(new ConstrutContact.Builder()
        .type(ContactType.TYPE_GROUP_MEMBER)
        .datas(null).build());
    mConstrutContact.add(new ConstrutContact.Builder()
        .type(ContactType.TYPE_GROUP_RADIO)
        .build());
    mConstrutContact.add(new ConstrutContact.Builder()
        .type(ContactType.TYPE_GROUP_VALUE)
        .title("清空历史记录").build());
    mConstrutContact.add(new ConstrutContact.Builder()
        .type(ContactType.TYPE_GROUP_VALUE)
        .title("昵称").build());
    mContactAdapter.notifyDataSetChanged();
  }

  /**
   * 更新和完善群信息的。
   */
  private void setUpAdapter() {
    mConstrutContact = new ArrayList<>();
    mContactAdapter = new ContactAdapter(this, mConstrutContact);
    mContactAdapter.setOnClickListener(this);
    mGroupDetailRecycle.addItemDecoration(RecyclerViewUtils.buildItemDecoration(this));
    mGroupDetailRecycle.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    mGroupDetailRecycle.setAdapter(mContactAdapter);
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public static void startGroupDetail(Activity activity) {
    Intent intent = new Intent(activity, GroupDetailActivity.class);
    activity.startActivity(intent);
  }

  public void setUpToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText("群详情");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
    mToolbar.setNavigationOnClickListener(v -> this.finish());
  }

  @Override public void onItemClick(int position, View view) {

  }
}
