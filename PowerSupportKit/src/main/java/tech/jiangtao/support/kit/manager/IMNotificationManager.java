package tech.jiangtao.support.kit.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.PowerManager;
import tech.jiangtao.support.kit.archive.type.IMFNotificationType;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.realm.SessionRealm;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.POWER_SERVICE;

/**
 * Class: IMNotificationManager </br>
 * Description: 通知管理器 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 28/05/2017 07:15</br>
 * Update: 28/05/2017 07:15 </br>
 **/

public class IMNotificationManager {
  private IMSettingManager mIMSettingManager;
  private PowerManager.WakeLock mWakelock;
  private Context mContext;

  private IMNotificationManager() {
    mIMSettingManager = new IMSettingManager();
  }

  public static IMNotificationManager geInstance() {

    return IMNotificationManagerHolder.sIMNotificationManager;
  }

  private static class IMNotificationManagerHolder {
    private static final IMNotificationManager sIMNotificationManager = new IMNotificationManager();
  }

  public PowerManager.WakeLock getmWakelock(Context context){
    if (mWakelock==null) {
      PowerManager mPowerManager = (PowerManager) context.getSystemService(POWER_SERVICE);
      mWakelock = mPowerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "target");
    }
    return mWakelock;
  }

  public void showMessageNotification(Context context,SessionRealm object,Intent intent){
    if (mIMSettingManager.getNotification(context)) {
      SessionRealm realm = (SessionRealm) object;
      getmWakelock(context).acquire(10 * 60 * 1000L /*10 minutes*/);
      NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
      Notification.Builder builder = new Notification.Builder(context);
      builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT))
          .setContentTitle(realm.getContactRealm().getNickName())
          .setContentText(realm.getMessageRealm().getTextMessage())
          .setSmallIcon(tech.jiangtao.support.kit.R.mipmap.ic_launcher)
          .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), tech.jiangtao.support.kit.R.mipmap.ic_launcher))
          .setWhen(System.currentTimeMillis())
          .setPriority(Notification.PRIORITY_HIGH)
          .setDefaults(Notification.DEFAULT_VIBRATE);
      Notification notification = builder.build();
      notification.flags = Notification.FLAG_AUTO_CANCEL;
      notification.defaults = Notification.DEFAULT_SOUND;
      mNotificationManager.notify(0, notification);
      mWakelock.release();
    }
  }

  /**
   * 与通讯录相关的操作
   * @param context
   * @param contactRealm
   * @param type
   * @param intent
   */
  public void showContactNotification(Context context,ContactRealm contactRealm,IMFNotificationType type,Intent intent){
    if (mIMSettingManager.getNotification(context)) {
      getmWakelock(context).acquire(10 * 60 * 1000L /*10 minutes*/);
      NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
      Notification.Builder builder = new Notification.Builder(context);
      builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT))
          .setContentTitle(contactRealm.getNickName())
          .setContentText(getMessagePro(contactRealm,type))
          .setSmallIcon(tech.jiangtao.support.kit.R.mipmap.ic_launcher)
          .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), tech.jiangtao.support.kit.R.mipmap.ic_launcher))
          .setWhen(System.currentTimeMillis())
          .setPriority(Notification.PRIORITY_HIGH)
          .setDefaults(Notification.DEFAULT_VIBRATE);
      Notification notification = builder.build();
      notification.flags = Notification.FLAG_AUTO_CANCEL;
      notification.defaults = Notification.DEFAULT_SOUND;
      mNotificationManager.notify(0, notification);
      mWakelock.release();
    }

  }

  public void showPushNotification(){

  }

  private String getMessagePro(ContactRealm contactRealm, IMFNotificationType type){
    String text = "";
    switch (type){
      case SUBSCRIBE:
        text = contactRealm.getNickName()+"请求加你为好友 *^*";
        break;
      case UNSUBSCRIBE:
        text = contactRealm.getNickName()+"拒绝了你的请求 *^*";
        break;
      case SUBSCRIBED:
        text = contactRealm.getNickName()+"同意了你的请求 *^*";
        break;
    }
   return text;
  }
}
