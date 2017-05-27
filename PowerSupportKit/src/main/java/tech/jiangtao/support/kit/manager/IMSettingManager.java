package tech.jiangtao.support.kit.manager;

import android.content.Context;
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

  /**
   * 修改声音控制
   * @param context
   * @param value
   */
  public void storeVoice(Context context,boolean value){
    AppPreferences appPreferences  = new AppPreferences(context);
    appPreferences.put(VOICE,value);
    // 写接口上传保存
  }

  /**
   * 修改震动控制
   * @param context
   * @param value
   */
  public void storeVibrate(Context context,boolean value){
    AppPreferences appPreferences  = new AppPreferences(context);
    appPreferences.put(VIBRATE,value);
    // 写接口上传保存信息
  }

  /**
   * 获取声音设置
   * @param context
   * @return
   */
  public boolean getVoice(Context context){
    AppPreferences preferences = new AppPreferences(context);
    return preferences.getBoolean(VOICE,false);
  }

  /**
   * 获取震动
   * @param context
   * @return
   */
  public boolean getVibrate(Context context){
    AppPreferences preferences = new AppPreferences(context);
    return preferences.getBoolean(VIBRATE,false);
  }

}
