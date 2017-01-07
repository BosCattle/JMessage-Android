package com.china.epower.chat.ui.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;


import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;

import static xiaofei.library.hermes.Hermes.getContext;

public class LauncherActivity extends AppCompatActivity {

  private static final int SPLASH_DISPLAY_LENGTH = 3000;
  private AppPreferences appPreferences = new AppPreferences(getContext());

  @Override protected void onCreate(Bundle savedInstanceState) {
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
              String username = appPreferences.getString("userJid");
              String password = appPreferences.getString("password");
              if (username != null && password != null) {
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
