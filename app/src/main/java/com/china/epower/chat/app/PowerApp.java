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
 * localhost
 * http://192.168.43.164:8080/tigase/
 * http:// 192.168.3.4.164:8080/tigase/
 * server
 * http://139.162.78.252:8080/tigase/
 **/

public class PowerApp extends Application {

  private static final String TAG = PowerApp.class.getSimpleName();

  @Override public void onCreate() {
    super.onCreate();
    SimpleHUD.backgroundHexColor = "#FF4081";
    SupportUI.initialize(getApplicationContext());
  }
}
