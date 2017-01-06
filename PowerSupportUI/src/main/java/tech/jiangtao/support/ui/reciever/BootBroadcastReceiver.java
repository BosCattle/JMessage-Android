package tech.jiangtao.support.ui.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import tech.jiangtao.support.ui.service.SupportService;
import xiaofei.library.hermeseventbus.HermesEventBus;

public class BootBroadcastReceiver extends BroadcastReceiver {

  private static final String TAG = "BootBroadcastReceiver";
  private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

  public BootBroadcastReceiver(){
    if (!HermesEventBus.getDefault().isRegistered(this)){
      HermesEventBus.getDefault().register(this);
    }
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.i(TAG, "Boot this system , BootBroadcastReceiver onReceive()");

    if (intent.getAction().equals(ACTION_BOOT)) {
      Log.i(TAG, "BootBroadcastReceiver onReceive(), Do thing!");
      Intent intent1 = new Intent(context, SupportService.class);
      context.startService(intent);
    }
  }
}
