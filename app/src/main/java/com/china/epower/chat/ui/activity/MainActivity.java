package com.china.epower.chat.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.epower.chat.R;
import com.china.epower.chat.ui.fragment.PersonalFragment;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.roughike.bottombar.BottomBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.List;
import tech.jiangtao.support.ui.fragment.ChatListFragment;
import tech.jiangtao.support.ui.linstener.ContactItemCallback;
import tech.jiangtao.support.ui.pattern.ConstrutContact;

/**
 * Class: MainActivity </br>
 * Description: 主页 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 10/11/2016 3:08 PM</br>
 * Update: 10/11/2016 3:08 PM </br>
 **/
public class MainActivity extends BaseActivity implements ContactItemCallback {

  @BindView(R.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.contentContainer) FrameLayout mContentContainer;
  @BindView(R.id.bottomBar) BottomBar mBottomBar;
  @BindView(R.id.image) ImageView mImage;

  private ArrayList<Fragment> mFragments;
  private PopupMenu mPopupMenu;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    init();
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  private void init() {
    initToolbar();
    initFragments();
    tabListen();
    updateCheck();
  }

  public void tabListen() {
    mBottomBar.setOnTabSelectListener(tabId -> {
      switch (tabId) {
        case R.id.tab_home:
          naviFragment(mFragments.get(0));
          break;
        case R.id.tab_contact:
          naviFragment(mFragments.get(1));
          break;
        case R.id.tab_personal:
          naviFragment(mFragments.get(2));
          break;
      }
    });
  }

  public void naviFragment(Fragment fragment) {
    FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
    mTransaction.replace(R.id.contentContainer, fragment);
    mTransaction.commit();
  }

  public void initFragments() {
    mFragments = new ArrayList<>();
    mFragments.add(ChatListFragment.newInstance());
    mFragments.add(tech.jiangtao.support.ui.fragment.ContactFragment.newInstance());
    mFragments.add(PersonalFragment.newInstance());
  }

  public void initToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText("融合通信");
    setSupportActionBar(mToolbar);
  }

  public static void startMain(Activity activity) {
    Intent intent = new Intent(activity, MainActivity.class);
    activity.startActivity(intent);
  }

  public void showPopupMenu() {
    mPopupMenu = new PopupMenu(this, mImage);
    mPopupMenu.getMenuInflater().inflate(R.menu.menu_popup, mPopupMenu.getMenu());
    mPopupMenu.setOnMenuItemClickListener(item -> {
      switch (item.getItemId()) {
        case R.id.add_friend:
          AddFriendActivity.startAddFriend(MainActivity.this);
          break;
        case R.id.add_group:
          AddGroupActivity.startAddGroup(MainActivity.this);
          break;
        case R.id.free_call:

          break;
        case R.id.audio_meet:

          break;
      }
      return false;
    });
    mPopupMenu.show();
  }

  @OnClick(R.id.image) public void onClick(View v) {
    showPopupMenu();
  }

  @Override public void onItemClick(int position, View view, Object object) {
    if (object instanceof ConstrutContact) {
      if (position == 0) {

      } else if (position == 1) {

      } else {
        ConstrutContact data = (ConstrutContact) object;
        ChatActivity.startChat(this, data.mVCardRealm);
      }
    }
  }

  public void updateCheck() {
    PgyUpdateManager.register(this, new UpdateManagerListener() {

      @Override public void onUpdateAvailable(final String result) {

        // 将新版本信息封装到AppBean中
        final AppBean appBean = getAppBeanFromString(result);
        new AlertDialog.Builder(MainActivity.this).setTitle("更新")
            .setMessage("统一通信有更新啦。版本号"+appBean.getReleaseNote()+appBean.getVersionCode())
            .setNegativeButton("确定",
                (dialog, which) -> startDownloadTask(MainActivity.this, appBean.getDownloadURL()))
            .show();
      }

      @Override public void onNoUpdateAvailable() {
      }
    });
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    PgyUpdateManager.unregister();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.d("MainActivity", "onActivityResult: ");
    List<Fragment> fragments = getSupportFragmentManager().getFragments();
    if (fragments != null) {
      for (Fragment fragment : fragments) {
        fragment.onActivityResult(requestCode, resultCode, data);
      }
    }
  }
}
