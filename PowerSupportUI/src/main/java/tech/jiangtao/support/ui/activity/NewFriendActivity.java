package tech.jiangtao.support.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import tech.jiangtao.support.kit.eventbus.FriendRequest;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.R2;
import tech.jiangtao.support.ui.adapter.BaseEasyAdapter;
import tech.jiangtao.support.ui.adapter.BaseEasyViewHolderFactory;
import tech.jiangtao.support.ui.model.ChatExtraModel;
import tech.jiangtao.support.ui.viewholder.ExpressViewHolder;
import tech.jiangtao.support.ui.viewholder.NewFriendViewHolder;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: NewFriendActivity </br>
 * Description: 有新的好友需求 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 08/01/2017 2:42 PM</br>
 * Update: 08/01/2017 2:42 PM </br>
 **/
public class NewFriendActivity extends BaseActivity {

  public static final String NEW_FLAG = "add_friend";
  @BindView(R2.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R2.id.toolbar) Toolbar mToolbar;
  @BindView(R2.id.new_friend_notice) TextView mNewFriendNotice;
  @BindView(R2.id.new_friend_page) RecyclerView mNewFriendPage;
  private BaseEasyAdapter mBaseEasyAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_friend);
    ButterKnife.bind(this);
    if (!HermesEventBus.getDefault().isRegistered(this)){
      HermesEventBus.getDefault().register(this);
    }
    setUpToolbar();
    setUpAdapter();
  }

  private void setUpAdapter() {
    mBaseEasyAdapter = new BaseEasyAdapter(this);
    mBaseEasyAdapter.viewHolderFactory(new BaseEasyViewHolderFactory(this));
    mBaseEasyAdapter.bind(FriendRequest.class, NewFriendViewHolder.class);
    mNewFriendPage.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    mNewFriendPage.setAdapter(mBaseEasyAdapter);
    FriendRequest request = getIntent().getParcelableExtra(NEW_FLAG);
    if (request!=null) {
      mBaseEasyAdapter.add(request);
    }
    mBaseEasyAdapter.notifyDataSetChanged();
  }

  public void setUpToolbar() {
    if (mToolbar != null) {
      mToolbar.setTitle("");
      mTvToolbar.setText("新朋友");
      setSupportActionBar(mToolbar);
      mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
      mToolbar.setNavigationOnClickListener(
          v -> ActivityCompat.finishAfterTransition(NewFriendActivity.this));
    }
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public static void startNewFriend(Context context) {
    Intent intent = new Intent(context, NewFriendActivity.class);
    context.startActivity(intent);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onFriendRequest(FriendRequest request){
    Log.d("----------", "onFriendRequest: "+request.username+"    "+request.fullUserJid);
    mBaseEasyAdapter.add(request);
    mBaseEasyAdapter.notifyDataSetChanged();
  }
}
