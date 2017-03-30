package tech.jiangtao.support.ui.reciever;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import tech.jiangtao.support.kit.eventbus.NotificationConnection;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.ui.service.SupportService;
import tech.jiangtao.support.ui.service.XMPPService;
import xiaofei.library.hermeseventbus.HermesEventBus;

public class TickBroadcastReceiver extends BroadcastReceiver {
  public static final String TAG = TickBroadcastReceiver.class.getSimpleName();

  @Override public IBinder peekService(Context myContext, Intent service) {
    return super.peekService(myContext, service);
  }

  @Override public void onReceive(Context context, Intent intent) {
    LogUtils.d(TAG, "onReceive: 检测服务是否在运行");
    HermesEventBus.getDefault().post(new NotificationConnection(true));
    boolean isServiceRunning = false;
    if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
      ActivityManager manager = (ActivityManager) context.getApplicationContext()
          .getSystemService(Context.ACTIVITY_SERVICE);
      for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
          Integer.MAX_VALUE)) {
        if (SupportService.class.getCanonicalName().equals(service.service.getClassName())
            || XMPPService.class.getCanonicalName().equals(service.service.getClassName())) {
          isServiceRunning = true;
          LogUtils.d(TAG, "onReceive: 服务正在运行");
        }
      }
      if (!isServiceRunning) {
        LogUtils.e("-------->", "onReceive: 启动service");
        Intent intent1 = new Intent(context, XMPPService.class);
        context.startService(intent1);
        Intent intent2 = new Intent(context, SupportService.class);
        context.startService(intent2);
      }
    }
  }
}
