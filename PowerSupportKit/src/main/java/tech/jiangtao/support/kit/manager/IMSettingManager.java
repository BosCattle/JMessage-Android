package tech.jiangtao.support.kit.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.UUID;
import net.grandcentrix.tray.AppPreferences;

/**
 * Class: IMSettingManager </br>
 * Description: 设置管理器 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 02:50</br>
 * Update: 27/05/2017 02:50 </br>
 **/

public class IMSettingManager {

  public static final String VOICE = "voice";
  public static final String VIBRATE = "vibrate";
  public static final String NOTIFICATION = "notification";

  /**
   * 修改声音控制
   */
  public void storeVoice(Context context, boolean value) {
    AppPreferences appPreferences = new AppPreferences(context);
    appPreferences.put(VOICE, value);
    // 写接口上传保存
  }

  /**
   * 修改震动控制
   */
  public void storeVibrate(Context context, boolean value) {
    AppPreferences appPreferences = new AppPreferences(context);
    appPreferences.put(VIBRATE, value);
    // 写接口上传保存信息
  }

  /**
   * 修改是否显示通知栏控制
   */
  public void storeNotification(Context context, boolean value) {
    AppPreferences appPreferences = new AppPreferences(context);
    appPreferences.put(NOTIFICATION, value);
    // 写接口上传保存信息
  }

  /**
   * 获取声音设置
   */
  public boolean getVoice(Context context) {
    AppPreferences preferences = new AppPreferences(context);
    return preferences.getBoolean(VOICE, false);
  }

  /**
   * 获取震动
   */
  public boolean getVibrate(Context context) {
    AppPreferences preferences = new AppPreferences(context);
    return preferences.getBoolean(VIBRATE, false);
  }

  /**
   * 获取是否显示通知栏
   */
  public boolean getNotification(Context context) {
    AppPreferences preferences = new AppPreferences(context);
    return preferences.getBoolean(NOTIFICATION, true);
  }

  // IMEI码
  public String getIMIEStatus(Context context) {
    TelephonyManager tm = (TelephonyManager) context
        .getSystemService(Context.TELEPHONY_SERVICE);
    String deviceId = tm.getDeviceId();
    return deviceId;
  }

  // Mac地址
  public String getLocalMac(Context context) {
    WifiManager wifi = (WifiManager) context
        .getSystemService(Context.WIFI_SERVICE);
    WifiInfo info = wifi.getConnectionInfo();
    return info.getMacAddress();
  }

  // Android Id
  public String getAndroidId(Context context) {
    @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(
        context.getContentResolver(), Settings.Secure.ANDROID_ID);
    return androidId;
  }
}
