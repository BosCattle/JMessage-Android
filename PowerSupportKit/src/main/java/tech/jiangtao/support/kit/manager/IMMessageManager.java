package tech.jiangtao.support.kit.manager;

import io.realm.Realm;
import tech.jiangtao.support.kit.archive.type.MessageExtensionType;
import tech.jiangtao.support.kit.callback.IMListenerCollection;
import tech.jiangtao.support.kit.eventbus.IMMessageResponseModel;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: IMMessageManager </br>
 * Description: 消息管理器 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 02:46</br>
 * Update: 27/05/2017 02:46 </br>
 **/

public class IMMessageManager {

  private static final String TAG = IMMessageManager.class.getSimpleName();
  private Realm mRealm;
  private IMListenerCollection.IMMessageChangeListener mIMMessageChangeListener;

  private IMMessageManager() {
    connectRealm();
  }

  public static IMMessageManager geInstance() {
    return IMMessageManagerHolder.sIMMessageManager;
  }

  private static class IMMessageManagerHolder {
    private static final IMMessageManager sIMMessageManager = new IMMessageManager();
  }

  public void setmIMMessageChangeListener(
      IMListenerCollection.IMMessageChangeListener mIMMessageChangeListener) {
    this.mIMMessageChangeListener = mIMMessageChangeListener;
  }

  /**
   * 存储消息
   */
  public MessageRealm storeMessage(IMMessageResponseModel model) {
    // ---> 保存到消息表
    connectRealm();
    MessageRealm messageRealm = mRealm.createObject(MessageRealm.class, model.getId());
    LogUtils.d(TAG, "消息id" + model.getId());
    messageRealm.setSender(StringSplitUtil.splitDivider(model.getMessage().getMsgSender()));
    messageRealm.setReceiver(StringSplitUtil.splitDivider(model.getMessage().getMsgReceived()));
    messageRealm.setTextMessage(model.getMessage().getMessage());
    messageRealm.setTime(model.getDate());
    messageRealm.setThread(model.getThread());
    messageRealm.setType(model.getMessage().toString());
    messageRealm.setMessageType(model.getMessage().getType());
    messageRealm.setMessageStatus(false);
    if (model.getMessage().getChatType().equals(MessageExtensionType.CHAT.toString())) {
      messageRealm.setMessageExtensionType(0);
    } else if (model.getMessage()
        .getChatType()
        .equals(MessageExtensionType.GROUP_CHAT.toString())) {
      messageRealm.setMessageExtensionType(1);
      messageRealm.setGroupId(model.getMessage().getGroup());
    }
    mRealm.executeTransaction(realm -> {
      realm.copyToRealm(messageRealm);
      if (mIMMessageChangeListener != null) {
        mIMMessageChangeListener.message(messageRealm);
      }
    });
    return messageRealm;
  }

  /**
   * 判断服务是否连接
   */
  public void connectHermes() {
    if (!HermesEventBus.getDefault().isRegistered(this)) {
      HermesEventBus.getDefault().register(this);
    }
  }

  /**
   * 判断数据库是否连接
   */
  public void connectRealm() {
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
  }
}
