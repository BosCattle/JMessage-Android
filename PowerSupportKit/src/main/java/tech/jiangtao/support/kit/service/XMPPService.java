package tech.jiangtao.support.kit.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.List;
import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.R;
import tech.jiangtao.support.kit.SupportAIDLConnection;
import tech.jiangtao.support.kit.archive.type.MessageAuthor;
import tech.jiangtao.support.kit.archive.type.DataExtensionType;
import tech.jiangtao.support.kit.archive.type.MessageExtensionType;
import tech.jiangtao.support.kit.eventbus.IMDeleteContactResponseModel;
import tech.jiangtao.support.kit.eventbus.FriendRequest;
import tech.jiangtao.support.kit.eventbus.ReceiveLastMessage;
import tech.jiangtao.support.kit.eventbus.RecieveMessage;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.realm.GroupRealm;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.realm.SessionRealm;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.util.ServiceUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.kit.api.ApiService;
import tech.jiangtao.support.kit.api.service.GroupServiceApi;
import tech.jiangtao.support.kit.reciever.TickBroadcastReceiver;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: XMPPService </br>
 * Description: 进行数据库操作和通知的服务 </br>
 * 简单的进行双进程守护
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 31/12/2016 1:54 AM</br>
 * Update: 31/12/2016 1:54 AM </br>
 * mRealm 有泄漏
 **/

public class XMPPService extends Service {

  public static final String TAG = XMPPService.class.getSimpleName();
  private static final int NOTIFICATION_ID = 1017;
  public static final String CHAT_CLASS = "chat_class";
  public static final String GROUP_CHAT_CLASS = "group_chat_class";
  public static final String INVITED_CLASS = "invited_class";
  private Realm mRealm;
  private XMPPServiceConnection mXMPPServiceConnection;
  private XMPPBinder mXMPPBinder;
  private PowerManager.WakeLock mWakelock;
  private GroupServiceApi mGroupServiceApi;
  private AppPreferences mAppPreferences;
  private Class mChatClass;
  private Class mGroupClass;
  private Class mInvitedClass;

  @Override public void onCreate() {
    super.onCreate();
    if (mXMPPBinder == null) {
      mXMPPBinder = new XMPPBinder();
    }
    PowerManager mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
    mWakelock = mPowerManager.newWakeLock(
        PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "target");
    mXMPPServiceConnection = new XMPPServiceConnection();
    if (!HermesEventBus.getDefault().isRegistered(this)) {
      HermesEventBus.getDefault().register(this);
    }
    mGroupServiceApi = ApiService.getInstance().createApiService(GroupServiceApi.class);
    mAppPreferences = new AppPreferences(this);
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    startForegroundCompat();
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
    mChatClass = (Class) intent.getSerializableExtra(CHAT_CLASS);
    mGroupClass = (Class) intent.getSerializableExtra(GROUP_CHAT_CLASS);
    mInvitedClass = (Class) intent.getSerializableExtra(INVITED_CLASS);
    IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
    TickBroadcastReceiver receiver = new TickBroadcastReceiver();
    registerReceiver(receiver, filter);
    Intent intent1 = new Intent(this, SupportService.class);
    this.bindService(intent1, mXMPPServiceConnection, Context.BIND_IMPORTANT);
    return START_STICKY;
  }



  @Subscribe(threadMode = ThreadMode.MAIN) public void onRecieveMessage(RecieveMessage message) {
    //先保存会话表，然后保存到消息记录表
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
    mRealm.executeTransactionAsync(realm -> {
      RealmResults<SessionRealm> result = null;
      if (message.messageExtensionType.equals(MessageExtensionType.CHAT)) {
        // 根据senderFriendId，发送者的userId是否可数据库中存储的userId相同
        // 单聊是message.userJID
        // 自己发的消息，userId代表自己，ownJid代表别人
        // 别人发的消息,userId代表别人，ownJid代表自己
        if (message.messageAuthor.equals(MessageAuthor.OWN)) {
          result = realm.where(SessionRealm.class)
              .equalTo(SupportIM.SENDERFRIENDID, StringSplitUtil.splitDivider(message.ownJid))
              .findAll();
        } else {
          result = realm.where(SessionRealm.class)
              .equalTo(SupportIM.SENDERFRIENDID, StringSplitUtil.splitDivider(message.userJID))
              .findAll();
        }
      } else if (message.messageExtensionType.equals(MessageExtensionType.GROUP_CHAT)) {
        // 根据senderFriendId，发送者的userId是否可数据库中存储的userId相同
        // 群聊是message.groupId
        result = realm.where(SessionRealm.class)
            .equalTo(SupportIM.SENDERFRIENDID, StringSplitUtil.splitDivider(message.groupId))
            .findAll();
      }
      // 保存到SessionRealm
      SessionRealm sessionRealm = new SessionRealm();
      if (result != null && result.size() != 0) {
        sessionRealm = result.first();
        sessionRealm.setMessageId(message.id);
        sessionRealm.setUnReadCount(sessionRealm.getUnReadCount() + 1);
      } else {
        sessionRealm = new SessionRealm();
        sessionRealm.setSessionId(UUID.randomUUID().toString());
        sessionRealm.setMessageId(message.id);
        sessionRealm.setUnReadCount(1);
      }
      // 应该使用int值，后期会拓展推送消息
      // 单聊为0，群聊为1
      if (message.messageExtensionType.equals(MessageExtensionType.CHAT)) {
        sessionRealm.setMessageType(0);
        if (message.messageAuthor.equals(MessageAuthor.OWN)) {
          sessionRealm.setSenderFriendId(StringSplitUtil.splitDivider(message.ownJid));
        } else {
          sessionRealm.setSenderFriendId(StringSplitUtil.splitDivider(message.userJID));
        }
      } else if (message.messageExtensionType.equals(MessageExtensionType.GROUP_CHAT)) {
        sessionRealm.setMessageType(1);
        sessionRealm.setSenderFriendId(StringSplitUtil.splitDivider(message.groupId));
      }
      // ---> 保存到消息表
      MessageRealm messageRealm = new MessageRealm();
      messageRealm.setId(message.id);
      messageRealm.setSender(StringSplitUtil.splitDivider(message.userJID));
      messageRealm.setReceiver(StringSplitUtil.splitDivider(message.ownJid));
      messageRealm.setTextMessage(message.message);
      messageRealm.setTime(null);
      messageRealm.setThread(message.thread);
      messageRealm.setType(message.type.toString());
      messageRealm.setMessageType(message.messageType.toString());
      messageRealm.setMessageStatus(false);
      if (message.messageExtensionType.equals(MessageExtensionType.CHAT)) {
        messageRealm.setMessageExtensionType(0);
      } else if (message.messageExtensionType.equals(MessageExtensionType.GROUP_CHAT)) {
        messageRealm.setMessageExtensionType(1);
        messageRealm.setGroupId(message.groupId);
        // 检查是否有当前群组的信息
        String userId = null;
        try {
          userId = mAppPreferences.getString(SupportIM.USER_ID);
        } catch (ItemNotFoundException e) {
          e.printStackTrace();
        }
        RealmResults<GroupRealm> groups = realm.where(GroupRealm.class)
            .equalTo(SupportIM.GROUPID, StringSplitUtil.splitDivider(message.groupId))
            .findAll();
        if (groups.size() == 0) {
          mGroupServiceApi.groups(userId)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(this::writeGroupRealmData);
        }
        // 检查本地是否有该群用户的资料
        RealmResults<ContactRealm> contactRealms =
            realm.where(ContactRealm.class).equalTo(SupportIM.USER_ID, message.userJID).findAll();
        // --------没有
        if (contactRealms.size() == 0) {
          // 获取用户信息
          mGroupServiceApi.selectGroupMembers(StringSplitUtil.splitDivider(message.groupId),
              StringSplitUtil.splitDivider(message.userJID))
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(contactRealms1 -> {
                if (contactRealms1 != null && contactRealms1.size() != 0) {
                  writeToRealm(contactRealms1);
                }
              }, new ErrorAction() {
                @Override public void call(Throwable throwable) {
                  super.call(throwable);
                  Log.e(TAG, "call: 获取群用户错误");
                }
              });
        }
      }
      realm.copyToRealmOrUpdate(sessionRealm);
      realm.copyToRealm(messageRealm);
    }, () -> {
      LogUtils.d(TAG, "onSuccess: 保存消息成功");
      HermesEventBus.getDefault()
          .post(new ReceiveLastMessage(message.id, message.type, message.userJID, message.ownJid,
              message.thread, message.message, message.messageType, message.messageExtensionType,
              false, message.messageAuthor, message.groupId));
      // 根据单聊还是群聊来进行数据库检查
      Intent intent = null;
      if (message.messageExtensionType.equals(MessageExtensionType.CHAT)) {
        RealmResults<ContactRealm> results = mRealm.where(ContactRealm.class)
            .equalTo(SupportIM.USER_ID, StringSplitUtil.splitDivider(message.userJID))
            .findAll();
        if (results.size() != 0) {
          intent = new Intent(XMPPService.this, mChatClass);
          intent.putExtra(SupportIM.VCARD, results.first());
        }
        LogUtils.d(TAG, "当前应用是否处于前台" + ServiceUtils.isApplicationBroughtToBackground(
            this.getApplicationContext()));
        if (message.messageAuthor == MessageAuthor.FRIEND && intent != null) {
          if (message.messageType == DataExtensionType.TEXT) {
            showOnesNotification(StringSplitUtil.splitPrefix(message.userJID), message.message,
                intent);
          }
          if (message.messageType == DataExtensionType.IMAGE) {
            showOnesNotification(StringSplitUtil.splitPrefix(message.userJID), "[图片]", intent);
          }
          if (message.messageType == DataExtensionType.AUDIO) {
            showOnesNotification(StringSplitUtil.splitPrefix(message.userJID), "[音频]", intent);
          }
          if (message.messageType == DataExtensionType.VIDEO) {
            showOnesNotification(StringSplitUtil.splitPrefix(message.userJID), "[视频]", intent);
          }
        }
      } else if (message.messageExtensionType.equals(MessageExtensionType.GROUP_CHAT)) {

      }
    }, error -> LogUtils.d(TAG, "onError: 保存消息失败" + error.getMessage()));
  }

  private void writeGroupRealmData(List<GroupRealm> groupRealms) {
    mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(groupRealms));
  }

  public void writeToRealm(List<ContactRealm> list) {
    mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(list));
  }

  /**
   * 添加好友通知
   */
  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN) @Subscribe(threadMode = ThreadMode.MAIN)
  public void addFriendsNotification(FriendRequest request) {
    mWakelock.acquire(10*60*1000L /*10 minutes*/);
    Intent i = new Intent(this, mInvitedClass);
    i.putExtra(SupportIM.NEW_FLAG, request);
    showOnesNotification(request.username, request.username + "请求添加你为好友.", i);
    mWakelock.release();
  }

  /**
   * 显示通知
   */
  public void showNotification(String name, String info) {
    Notification.Builder builder = new Notification.Builder(this);
    Intent intent = new Intent(this, null);
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

  @Override public void onDestroy() {
    super.onDestroy();
    mRealm.close();
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return mXMPPBinder;
  }

  public void showOnesNotification(String name, String info, Intent intent) {
    mWakelock.acquire(10*60*1000L /*10 minutes*/);
    NotificationManager mNotificationManager =
        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    Notification.Builder builder = new Notification.Builder(this);
    builder.setContentIntent(
        PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT))
        .setContentTitle(name)
        .setContentText(info)
        .setSmallIcon(tech.jiangtao.support.kit.R.mipmap.ic_launcher)
        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
            tech.jiangtao.support.kit.R.mipmap.ic_launcher))
        .setWhen(System.currentTimeMillis())
        .setPriority(Notification.PRIORITY_HIGH)
        .setDefaults(Notification.DEFAULT_VIBRATE);
    Notification notification = builder.build();
    notification.flags = Notification.FLAG_AUTO_CANCEL;
    notification.defaults = Notification.DEFAULT_SOUND;
    mNotificationManager.notify(0, notification);
    mWakelock.release();
  }

  /**
   * 保证连接的代码
   */
  private class XMPPServiceConnection implements ServiceConnection {

    @Override public void onServiceConnected(ComponentName name, IBinder service) {
      LogUtils.d(TAG, "onServiceConnected: 建立连接");
      SupportAIDLConnection connection = SupportAIDLConnection.Stub.asInterface(service);
    }

    @Override public void onServiceDisconnected(ComponentName name) {
      LogUtils.d(TAG, "onServiceDisconnected: 服务被杀");
      XMPPService.this.startService(new Intent(XMPPService.this, SupportService.class));
      Intent intent = new Intent(XMPPService.this, SupportService.class);
      XMPPService.this.bindService(intent, mXMPPServiceConnection, Context.BIND_IMPORTANT);
    }
  }

  private class XMPPBinder extends SupportAIDLConnection.Stub {

    @Override public String getServiceName() throws RemoteException {
      return "XMPPService的服务";
    }
  }
  
  /**
   * Class: XMPPService </br>
   * Description: 开启前台服务，关闭view，不可动 </br>
   * Creator: kevin </br>
   * Email: jiangtao103cp@gmail.com </br>
   * Date: 26/05/2017 23:09</br>
   * Update: 26/05/2017 23:09 </br>
   **/
  public static class InnerService extends Service {

    @Override public void onCreate() {
      super.onCreate();
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
      startForeground(NOTIFICATION_ID, fadeNotification(this));
      stopForeground(true);
      stopSelf();
      return super.onStartCommand(intent, flags, startId);
    }

    @Nullable @Override public IBinder onBind(Intent intent) {
      return null;
    }
  }

  public static Notification fadeNotification(Context context) {
    Notification notification = new Notification();
    notification.icon = R.drawable.abc_ab_share_pack_mtrl_alpha;
    notification.priority = Notification.PRIORITY_MIN;
    notification.contentView =
        new RemoteViews(context.getPackageName(), R.layout.notification_view);
    return notification;
  }

  private void startForegroundCompat() {
    startService(new Intent(this, InnerService.class));
    startForeground(NOTIFICATION_ID, fadeNotification(this));
  }

  public static void login(String userName,String password){

  }
}
