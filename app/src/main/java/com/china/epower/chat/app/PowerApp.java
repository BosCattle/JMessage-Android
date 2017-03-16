package com.china.epower.chat.app;

import android.app.Application;
import java.util.UUID;
import tech.jiangtao.support.ui.SupportUI;
import work.wanghao.simplehud.SimpleHUD;

/**
 * Class: PowerApp </br>
 * Description: whole application  </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 10/11/2016 1:06 AM</br>
 * Update: 10/11/2016 1:06 AM </br>
 **/

public class PowerApp extends Application {

  private static final String TAG = PowerApp.class.getSimpleName();

  @Override public void onCreate() {
    super.onCreate();
    SimpleHUD.backgroundHexColor = "#FF4081";
    SupportUI.initialize(this, "dc-a4b8eb92-xmpp.jiangtao.tech.", UUID.randomUUID().toString(),
        "192.168.43.164", 5222, "6e7ea2251ca5479d875916785c4418f1",
        "026eb8a2cb7b4ab18135a6a0454fd698", "http://ci.jiangtao.tech/fileUpload/");
  }
}
