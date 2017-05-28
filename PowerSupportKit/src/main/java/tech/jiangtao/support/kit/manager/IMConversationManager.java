package tech.jiangtao.support.kit.manager;

import android.content.Context;
import android.content.Intent;
import io.realm.Realm;
import io.realm.RealmResults;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.archive.type.MessageAuthor;
import tech.jiangtao.support.kit.archive.type.MessageExtensionType;
import tech.jiangtao.support.kit.callback.IMListenerCollection;
import tech.jiangtao.support.kit.eventbus.IMMessageResponseModel;
import tech.jiangtao.support.kit.realm.SessionRealm;
import tech.jiangtao.support.kit.util.LogUtils;
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

  private String mIndex;
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
    IMContactManager.geInstance().readSingleContact(message, contactRealm -> {
      // 查询会话管理器
      mSessionRealm = querySession(message);
      mRealm.executeTransaction(new Realm.Transaction() {
        @Override public void execute(Realm realm) {
          if (mSessionRealm != null) {
            mSessionRealm.setMessageId(message.id);
            mSessionRealm.setUnReadCount(mSessionRealm.getUnReadCount() + 1);
            LogUtils.d("----->未读消息数据:", mSessionRealm.getUnReadCount() + "条");
          } else {
            mSessionRealm = new SessionRealm();
            if (message.getAuthor().equals(MessageAuthor.OWN)) {
              mSessionRealm.setSessionId(
                  StringSplitUtil.splitDivider(message.getMessage().getMsgReceived()));
            } else if (message.getAuthor().equals(MessageAuthor.FRIEND)) {
              mSessionRealm.setSessionId(
                  StringSplitUtil.splitDivider(message.getMessage().getMsgSender()));
            }
            mSessionRealm.setMessageId(message.id);
            mSessionRealm.setUnReadCount(1);
          }
          mSessionRealm.setMessageType(
              message.getMessage().getChatType().equals(MessageExtensionType.GROUP_CHAT.toString())
                  ? 1 : 0);
          mSessionRealm.setContactRealm(contactRealm);
          mSessionRealm.setMessageRealm(IMMessageManager.geInstance().storeMessage(message));
          if (message.getMessage()
              .getChatType()
              .equals(MessageExtensionType.GROUP_CHAT.toString())) {
            IMGroupManager.geInstance()
                .readSingleGroupRealm(message, group -> mSessionRealm.setGroupRealm(group));
          }
          realm.copyToRealmOrUpdate(mSessionRealm);
          if (mIMSettingManager.getNotification(context) && message.getAuthor()
              .equals(MessageAuthor.FRIEND)) {
            Intent intent = new Intent(context, clazz);
            intent.putExtra(SupportIM.VCARD, contactRealm);
            IMNotificationManager.geInstance()
                .showMessageNotification(context, mSessionRealm, intent);
          }
          if (mIMConversationChangeListener != null) {
            queryConversations(mIMConversationChangeListener);
          }
        }
      });
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

  /**
   * 根据消息查询model
   */
  private SessionRealm querySession(IMMessageResponseModel message) {
    connectRealm();
    if (message.getMessage().getChatType().equals(MessageExtensionType.CHAT.toString())) {
      if (message.author.equals(MessageAuthor.OWN)) {
        LogUtils.d("---->", "自己发的消息");
        mIndex = StringSplitUtil.splitDivider(message.getMessage().getMsgReceived());
      } else {
        mIndex = StringSplitUtil.splitDivider(message.getMessage().getMsgSender());
      }
    } else if (message.getMessage()
        .getChatType()
        .equals(MessageExtensionType.GROUP_CHAT.toString())) {
      mIndex = StringSplitUtil.splitDivider(message.getMessage().getGroup());
    }
    return mRealm.where(SessionRealm.class).equalTo(SupportIM.SENDERFRIENDID, mIndex).findFirst();
  }

  public void deleteConversation(SessionRealm sessionRealm,
      IMListenerCollection.IMConversationDeleteListener listener) {
    connectRealm();
    mRealm.executeTransaction(realm -> {
      sessionRealm.deleteFromRealm();
      listener.success();
      if (mIMConversationChangeListener != null) {
        queryConversations(mIMConversationChangeListener);
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
