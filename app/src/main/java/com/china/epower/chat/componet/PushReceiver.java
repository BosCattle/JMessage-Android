package com.china.epower.chat.componet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import tech.jiangtao.support.kit.util.LogUtils;

public class PushReceiver extends BroadcastReceiver {

  @Override public void onReceive(Context context, Intent intent) {
    LogUtils.e(this.getClass().getSimpleName(),"我收到消息啦!");
  }
}
