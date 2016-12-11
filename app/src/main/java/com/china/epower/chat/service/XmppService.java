package com.china.epower.chat.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;

import android.support.annotation.Nullable;
import android.util.Log;
import com.china.epower.chat.R;
import com.china.epower.chat.ui.activity.ChatActivity;
import com.china.epower.chat.ui.activity.MainActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import tech.jiangtao.support.kit.callback.VCardCallback;
import tech.jiangtao.support.kit.eventbus.RecieveMessage;
import tech.jiangtao.support.kit.init.SupportIM;
import tech.jiangtao.support.kit.userdata.SimpleVCard;

public class XmppService extends Service {

  public static final String TAG = XmppService.class.getSimpleName();

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    showNotification("统一通信", "统一通信");
    if (!EventBus.getDefault().isRegistered(this)) {
      EventBus.getDefault().register(this);
    }
    return super.onStartCommand(intent, flags, startId);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onMessage(RecieveMessage event) {
    // 根据消息类型，作出调转服务
    //先写死
    SimpleVCard vCard = new SimpleVCard(event.userJid+"@"+ SupportIM.mResource);
    vCard.setmVCardCallback(new VCardCallback() {
      @Override public void recieveVCard(VCard vCard, String userJid) {
        Intent intent = new Intent(XmppService.this, ChatActivity.class);
        if (vCard!=null){
          if (vCard.getNickName()!=null){
            showOnesNotification(vCard.getNickName(), event.message.toString(), intent);
          }else {
            showOnesNotification(event.userJid, event.message.toString(), intent);
          }
        }
      }

      @Override public void settingVCard(String message) {
        Log.e(TAG, "settingVCard: " + message);
      }
    });
    vCard.getVCard();
  }

  /**
   * 显示通知
   */
  public void showNotification(String name, String info) {
    Notification.Builder builder = new Notification.Builder(this);
    Intent intent = new Intent(this, MainActivity.class);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      builder.setContentIntent(
          PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT))
          .setContentTitle(name)
          .setContentText(info)
          .setSmallIcon(R.mipmap.ic_launcher)
          .setWhen(System.currentTimeMillis())
          .setPriority(Notification.PRIORITY_HIGH)
          .setDefaults(Notification.DEFAULT_VIBRATE);
      Notification notification = builder.build();
      notification.flags = Notification.FLAG_ONGOING_EVENT;
      notification.defaults = Notification.DEFAULT_SOUND;
      startForeground(110, notification);
    }
  }

  public void showOnesNotification(String name, String info, Intent intent) {
    NotificationManager mNotificationManager =
        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    Notification.Builder builder = new Notification.Builder(this);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      builder.setContentIntent(
          PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT))
          .setContentTitle(name)
          .setContentText(info)
          .setSmallIcon(R.mipmap.ic_launcher)
          .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
          .setWhen(System.currentTimeMillis())
          .setPriority(Notification.PRIORITY_HIGH)
          .setDefaults(Notification.DEFAULT_VIBRATE);
      Notification notification = builder.build();
      notification.flags = Notification.FLAG_AUTO_CANCEL;
      notification.defaults = Notification.DEFAULT_SOUND;
      mNotificationManager.notify(0, notification);
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }
}
