package com.china.epower.chat.ui.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.cocosw.favor.FavorAdapter;

import tech.jiangtao.support.kit.realm.sharepreference.Account;
import tech.jiangtao.support.kit.realm.sharepreference.FirstEnter;

public class LauncherActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //检查是否登录
        new Handler().postDelayed(() -> {
                    Account account =
                            new FavorAdapter.Builder(LauncherActivity.this).build().create(Account.class);
                    FirstEnter enter = new FavorAdapter.Builder(this).build().create(FirstEnter.class);
                    if (enter.getEntered()) {
                        if (account.getUserName() != null) {
                            MainActivity.startMain(LauncherActivity.this);
                        } else {
                            LoginActivity.startLogin(LauncherActivity.this);
                        }
                    } else {
                        IndexActivity.startIndex(this);
                    }
                }

                , SPLASH_DISPLAY_LENGTH);
    }
}
