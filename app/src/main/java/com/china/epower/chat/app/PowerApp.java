package com.china.epower.chat.app;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import tech.jiangtao.support.kit.annotation.ChatRouter;
import tech.jiangtao.support.kit.annotation.GroupChatRouter;
import tech.jiangtao.support.kit.annotation.InvitedRouter;
import tech.jiangtao.support.ui.SupportUI;
import com.china.epower.chat.ui.activity.AllInvitedActivity;
import com.china.epower.chat.ui.activity.ChatActivity;
import com.china.epower.chat.ui.activity.GroupChatActivity;
import work.wanghao.simplehud.SimpleHUD;

/**
 * Class: PowerApp </br>
 * Description: whole application  </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 10/11/2016 1:06 AM</br>
 * Update: 10/11/2016 1:06 AM </br>
 **/
@ChatRouter(router = ChatActivity.class)
@GroupChatRouter(router = GroupChatActivity.class)
@InvitedRouter(router = AllInvitedActivity.class)
public class PowerApp extends Application {

  private static final String TAG = PowerApp.class.getSimpleName();

  @Override public void onCreate() {
    super.onCreate();
    SimpleHUD.backgroundHexColor = "#FF4081";
    SupportUI.initialize(getApplicationContext(), "resource.properties");
    Stetho.initialize(Stetho.newInitializerBuilder(this)
        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
        .build());
  }
}
