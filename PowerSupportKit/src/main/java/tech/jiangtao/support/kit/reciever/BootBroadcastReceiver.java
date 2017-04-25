package tech.jiangtao.support.kit.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.service.XMPPService;

public class BootBroadcastReceiver extends BroadcastReceiver {

  private static final String TAG = "BootBroadcastReceiver";
  private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

  public BootBroadcastReceiver(){
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    LogUtils.i(TAG, "Boot this system , BootBroadcastReceiver onReceive()");

    if (intent.getAction().equals(ACTION_BOOT)) {
      LogUtils.i(TAG, "BootBroadcastReceiver onReceive(), Do thing!");
      Intent intent1 = new Intent(context, SupportService.class);
      context.startService(intent1);
      Intent intent2 = new Intent(context, XMPPService.class);
      context.startService(intent2);
    }
  }
}
