package tech.jiangtao.support.ui.view;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import tech.jiangtao.support.kit.eventbus.ReceiveLastMessage;

public interface MessageNotification {

  @Subscribe(threadMode = ThreadMode.MAIN)
  void onMessage(ReceiveLastMessage message);
}
