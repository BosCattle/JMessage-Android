package tech.jiangtao.support.kit.reciever;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.util.ServiceUtils;

public class TickBroadcastReceiver extends BroadcastReceiver {

  @Override public void onReceive(Context context, Intent intent) {
    boolean isServiceRunning = false;
    if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
      ActivityManager manager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
      for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
        if (SupportService.class.getCanonicalName().equals(service.service.getClassName())) {
          isServiceRunning = true;
        }
      }
      if (!isServiceRunning) {
        Log.e("-------->", "onReceive: 启动service");
        Intent i = new Intent(context, SupportService.class);
        context.startService(i);
      }
    }
  }
}
