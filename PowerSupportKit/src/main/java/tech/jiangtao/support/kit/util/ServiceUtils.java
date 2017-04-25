package tech.jiangtao.support.kit.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import java.util.List;
import tech.jiangtao.support.kit.service.SupportService;

/**
 * Class: ServiceUtils </br>
 * Description: 服务工具类 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 17/12/2016 2:18 PM</br>
 * Update: 17/12/2016 2:18 PM </br>
 **/

public class ServiceUtils {

  //判断服务是否在运行
  public static boolean isServiceRunning(Context context) {
    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
        Integer.MAX_VALUE)) {
      if (SupportService.class.getName().equals(service.service.getClassName())
          && service.service.getPackageName().equalsIgnoreCase("com.example.app2")) {
        return true;
      }
    }
    return false;
  }

  /**
   *判断当前应用程序处于前台还是后台
   *
   * @param context
   * @return
   */
  public static boolean isApplicationBroughtToBackground(final Context context) {
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
    // 当前栈不为空
    if (!tasks.isEmpty()) {
      ComponentName topActivity = tasks.get(0).topActivity;
      // 当前栈顶的应用不是当前应用执行
      if (!topActivity.getPackageName().equals(context.getPackageName())) {
        return true;
      }
    }
    return false;
  }
}
