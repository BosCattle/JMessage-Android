package com.china.epower.chat.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.china.epower.chat.R;
import tech.jiangtao.support.kit.eventbus.IMRoomRequestModel;
import tech.jiangtao.support.kit.manager.IMAccountManager;
import tech.jiangtao.support.kit.realm.ContactRealm;
import xiaofei.library.hermeseventbus.HermesEventBus;

public class RoomInstanceActivity extends BaseActivity {

  @BindView(R.id.tv_toolbar) TextView mTvToolbar;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.roomName) AppCompatEditText mRoomName;
  @BindView(R.id.roomDesc) AppCompatEditText mRoomDesc;
  @BindView(R.id.push) Button mPush;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_room_instance);
    ButterKnife.bind(this);
    setUpToolbar();
    mPush.setOnClickListener(v -> {
      String roomName = mRoomName.getText().toString();
      String roomDesc = mRoomDesc.getText().toString();
      ContactRealm contactRealm = new IMAccountManager(RoomInstanceActivity.this).getAccount();
      HermesEventBus.getDefault()
          .postSticky(new IMRoomRequestModel(roomName, contactRealm.getNickName(), roomDesc));
    });
  }

  @Override protected boolean preSetupToolbar() {
    return false;
  }

  public void setUpToolbar() {
    mToolbar.setTitle("");
    mTvToolbar.setText("创建群组");
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
    mToolbar.setNavigationOnClickListener(
        v -> ActivityCompat.finishAfterTransition(RoomInstanceActivity.this));
  }

  public static void startRegister(Activity activity) {
    activity.startActivity(new Intent(activity, RoomInstanceActivity.class));
  }
}
