package com.china.epower.chat.ui.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.china.epower.chat.R;
import com.cocosw.favor.FavorAdapter;

import org.jivesoftware.smack.XMPPConnection;

import tech.jiangtao.support.kit.callback.ConnectionCallback;
import tech.jiangtao.support.kit.realm.sharepreference.Account;
import tech.jiangtao.support.kit.service.SupportService;
import work.wanghao.simplehud.SimpleHUD;

public class LauncherActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        //检查是否登录
        new Handler().postDelayed(() -> {
            Account account =
                    new FavorAdapter.Builder(LauncherActivity.this).build().create(Account.class);
            if (account.getUserName() != null && SupportService.getmXMPPConnection() != null) {
                if (SupportService.getmXMPPConnection().getUser() != null && !SupportService.getmXMPPConnection().getUser().equals("")) {
                    MainActivity.startMain(LauncherActivity.this);
                } else {
                    SupportService.login(account.getUserName(), account.getPassword(), new ConnectionCallback() {
                        @Override
                        public void connection(XMPPConnection connection) {
                            MainActivity.startMain(LauncherActivity.this);
                        }

                        @Override
                        public void connectionFailed(Exception e) {
                            SimpleHUD.showErrorMessage(LauncherActivity.this, "登录失败" + e);
                        }
                    });
                }
            }else {
                LoginActivity.startLogin(LauncherActivity.this);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
