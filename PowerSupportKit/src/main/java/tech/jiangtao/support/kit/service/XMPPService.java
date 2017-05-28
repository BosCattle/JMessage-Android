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
import tech.jiangtao.support.kit.eventbus.IMContactRequestNotificationModel;
import tech.jiangtao.support.kit.eventbus.IMMessageResponseModel;
import tech.jiangtao.support.kit.eventbus.ReceiveLastMessage;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.manager.IMConversationManager;
import tech.jiangtao.support.kit.manager.IMNotificationManager;
import tech.jiangtao.support.kit.manager.IMSettingManager;
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
  private IMSettingManager mSettingManager;

  @Override public void onCreate() {
    super.onCreate();
    if (mXMPPBinder == null) {
      mXMPPBinder = new XMPPBinder();
    }
    mSettingManager = new IMSettingManager();
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

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onRecieveMessage(IMMessageResponseModel message) {
    // TODO: 28/05/2017  需要获取到是单聊还是群聊
    IMConversationManager.geInstance().storeConversation(message, this, mChatClass);
  }

  /**
   * 添加好友通知
   */
  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN) @Subscribe(threadMode = ThreadMode.MAIN)
  public void addFriendsNotification(IMContactRequestNotificationModel request) {
    mWakelock.acquire(10 * 60 * 1000L /*10 minutes*/);
    Intent i = new Intent(this, mInvitedClass);
    i.putExtra(SupportIM.NEW_FLAG, request);
    IMNotificationManager.geInstance()
        .showContactNotification(this, request.getContactRealm(), request.getType(), i);
    mWakelock.release();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mRealm.close();
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return mXMPPBinder;
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

  public static void login(String userName, String password) {

  }
}
