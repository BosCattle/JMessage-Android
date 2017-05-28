package tech.jiangtao.support.kit.manager;

import android.content.Context;
import android.content.Intent;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.UUID;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.archive.type.MessageAuthor;
import tech.jiangtao.support.kit.archive.type.MessageExtensionType;
import tech.jiangtao.support.kit.callback.IMListenerCollection;
import tech.jiangtao.support.kit.eventbus.IMMessageResponseModel;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.realm.SessionRealm;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: IMConversationManager </br>
 * Description: 会话管理器 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 02:55</br>
 * Update: 27/05/2017 02:55 </br>
 **/

public class IMConversationManager {

  private Realm mRealm;
  private SessionRealm mSessionRealm;
  private IMSettingManager mIMSettingManager;
  private IMListenerCollection.IMConversationChangeListener mIMConversationChangeListener;

  private IMConversationManager() {
    connectRealm();
    mIMSettingManager = new IMSettingManager();
  }

  public static IMConversationManager geInstance() {
    return IMConversationManagerHolder.sIMConversationManager;
  }

  private static class IMConversationManagerHolder {
    private static final IMConversationManager sIMConversationManager = new IMConversationManager();
  }

  public void setmIMConversationChangeListener(
      IMListenerCollection.IMConversationChangeListener mIMConversationChangeListener) {
    this.mIMConversationChangeListener = mIMConversationChangeListener;
  }

  public void storeConversation(IMMessageResponseModel message, Context context, Class clazz) {
    connectRealm();
    MessageRealm messageRealm = IMMessageManager.geInstance().storeMessage(message);
    IMContactManager.geInstance().readSingleContact(message, contactRealm -> {
      // 查询会话管理器
      mSessionRealm = querySession(message);
      if (mSessionRealm != null) {
        mSessionRealm.setMessageId(message.id);
        mSessionRealm.setUnReadCount(mSessionRealm.getUnReadCount() + 1);
      } else {
        mSessionRealm = new SessionRealm();
        mSessionRealm.setSessionId(UUID.randomUUID().toString());
        mSessionRealm.setMessageId(message.id);
        mSessionRealm.setUnReadCount(1);
      }
      mSessionRealm.setContactRealm(contactRealm);
      mSessionRealm.setMessageRealm(messageRealm);
      if (message.getMessage().getChatType().equals(MessageExtensionType.GROUP_CHAT.toString())) {
        IMGroupManager.geInstance()
            .readSingleGroupRealm(message, group -> mSessionRealm.setGroupRealm(group));
      }
      mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(mSessionRealm));
      if (mIMSettingManager.getNotification(context) && message.getAuthor()
          .equals(MessageAuthor.FRIEND)) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(SupportIM.VCARD, contactRealm);
        IMNotificationManager.geInstance().showMessageNotification(context, mSessionRealm, intent);
      }
      if (mIMConversationChangeListener != null) {
        queryConversations(mIMConversationChangeListener);
      }
    });
  }

  /**
   * 查询所有的会话
   */
  public void queryConversations(IMListenerCollection.IMConversationChangeListener listener) {
    connectRealm();
    setmIMConversationChangeListener(listener);
    RealmResults<SessionRealm> realmResult = mRealm.where(SessionRealm.class).findAll();
    listener.change(realmResult);
  }

  private SessionRealm querySession(IMMessageResponseModel message) {
    connectRealm();
    SessionRealm result;
    String index = null;
    if (message.getMessage().getChatType().equals(MessageExtensionType.CHAT.toString())) {
      if (message.author.equals(MessageAuthor.OWN)) {
        index = StringSplitUtil.splitDivider(message.getMessage().getMsgReceived());
      } else {
        index = StringSplitUtil.splitDivider(message.getMessage().getMsgSender());
      }
    } else if (message.getMessage()
        .getChatType()
        .equals(MessageExtensionType.GROUP_CHAT.toString())) {
      // 根据senderFriendId，发送者的userId是否可数据库中存储的userId相同
      // 群聊是message.groupId
      index = StringSplitUtil.splitDivider(message.getMessage().getGroup());
    }
    // 保存到SessionRealm
    result = mRealm.where(SessionRealm.class).equalTo(SupportIM.SENDERFRIENDID, index).findFirst();
    return result;
  }

  public void deleteConversation(SessionRealm sessionRealm,
      IMListenerCollection.IMConversationDeleteListener listener) {
    connectRealm();
    mRealm.executeTransaction(new Realm.Transaction() {
      @Override public void execute(Realm realm) {
        sessionRealm.deleteFromRealm();
        listener.success();
        if (mIMConversationChangeListener != null) {
          queryConversations(mIMConversationChangeListener);
        }
      }
    });
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
