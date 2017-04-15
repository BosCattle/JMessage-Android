package com.china.epower.chat.ui.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;
import tech.jiangtao.support.kit.SupportIM;

public class LauncherActivity extends AppCompatActivity {

  private static final int SPLASH_DISPLAY_LENGTH = 3000;
  private AppPreferences appPreferences;

  @Override protected void onCreate(Bundle savedInstanceState) {
    appPreferences = new AppPreferences(this);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
  }

  @Override protected void onResume() {
    super.onResume();
    //检查是否登录
    new Handler().postDelayed(() -> {

      try {
        if (appPreferences.getBoolean("enter")) {
          String nickName = appPreferences.getString(SupportIM.USER_NAME);
          String userId = appPreferences.getString(SupportIM.USER_ID);
          if (userId != null && nickName != null) {
            MainActivity.startMain(LauncherActivity.this);
          } else {
            LoginActivity.startLogin(LauncherActivity.this);
          }
        } else {
          IndexActivity.startIndex(this);
        }
      } catch (ItemNotFoundException e) {
        e.printStackTrace();
        IndexActivity.startIndex(this);
      }
    }, SPLASH_DISPLAY_LENGTH);
  }
}
