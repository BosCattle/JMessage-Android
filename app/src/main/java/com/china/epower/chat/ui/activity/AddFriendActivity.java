package com.china.epower.chat.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import tech.jiangtao.support.ui.fragment.SearchUserFragment;

/**
 * Class: AddFriendActivity </br>
 * Description: 添加好友 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 12/11/2016 11:29 PM</br>
 * Update: 12/11/2016 11:29 PM </br>
 **/
public class AddFriendActivity extends BaseActivity {

  public static final String TAG = AddFriendActivity.class.getSimpleName();
  @BindView(R.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.add_friend) FrameLayout mAddFriend;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_friend);
    getTitleTextView().setText("添加好友");
    FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
    mTransaction.replace(R.id.add_friend, SearchUserFragment.newInstance());
    mTransaction.commit();
  }

  @Override protected boolean preSetupToolbar() {
    return true;
  }

  public static void startAddFriend(Activity activity) {
    activity.startActivity(new Intent(activity, AddFriendActivity.class));
  }
}
