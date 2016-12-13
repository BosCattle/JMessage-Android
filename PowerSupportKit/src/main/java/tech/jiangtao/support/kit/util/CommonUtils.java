package tech.jiangtao.support.kit.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import java.util.List;

/**
 * Class: CommonUtils </br>
 * Description: 公共工具类 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 13/12/2016 9:46 AM</br>
 * Update: 13/12/2016 9:46 AM </br>
 **/

public class CommonUtils {

  private static int screenWidth = 0;
  private static int screenHeight = 0;

  public static int dpToPx(int dp) {
    return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
  }

  public static int getScreenHeight(Context c) {
    if (screenHeight == 0) {
      WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
      Display display = wm.getDefaultDisplay();
      Point size = new Point();
      display.getSize(size);
      screenHeight = size.y;
    }

    return screenHeight;
  }

  public static int getScreenWidth(Context c) {
    if (screenWidth == 0) {
      WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
      Display display = wm.getDefaultDisplay();
      Point size = new Point();
      display.getSize(size);
      screenWidth = size.x;
    }

    return screenWidth;
  }

  /**
   * 用来判断服务是否运行.
   *
   * @param className 判断的服务名字
   * @return true 在运行 false 不在运行
   */
  public static boolean isServiceRunning(Context mContext, String className) {
    boolean isRunning = false;
    ActivityManager activityManager =
        (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(50);
    if (!(serviceList.size() > 0)) {
      return false;
    }
    for (int i = 0; i < serviceList.size(); i++) {
      if (serviceList.get(i).service.getClassName().equals(className)) {
        isRunning = true;
        break;
      }
    }
    return isRunning;
  }
}
