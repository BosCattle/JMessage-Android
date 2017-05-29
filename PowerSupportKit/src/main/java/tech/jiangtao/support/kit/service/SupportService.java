package tech.jiangtao.support.kit.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.offline.OfflineMessageManager;
import org.jivesoftware.smackx.ping.PingManager;
import org.jivesoftware.smackx.ping.android.ServerPingWithAlarmManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.SupportAIDLConnection;
import tech.jiangtao.support.kit.api.ApiService;
import tech.jiangtao.support.kit.api.service.AccountServiceApi;
import tech.jiangtao.support.kit.api.service.UserServiceApi;
import tech.jiangtao.support.kit.archive.type.IMFNotificationType;
import tech.jiangtao.support.kit.archive.type.MessageAuthor;
import tech.jiangtao.support.kit.archive.type.DataExtensionType;
import tech.jiangtao.support.kit.archive.type.MessageExtensionType;
import tech.jiangtao.support.kit.eventbus.IMAccountExitRequestModel;
import tech.jiangtao.support.kit.eventbus.IMAddContactRequestModel;
import tech.jiangtao.support.kit.eventbus.IMAddContactResponseModel;
import tech.jiangtao.support.kit.eventbus.IMContactDealResponseModel;
import tech.jiangtao.support.kit.eventbus.IMDeleteContactResponseModel;
import tech.jiangtao.support.kit.eventbus.IMContactRequestNotificationModel;
import tech.jiangtao.support.kit.eventbus.IMContactRequestModel;
import tech.jiangtao.support.kit.eventbus.IMContactResponseCollection;
import tech.jiangtao.support.kit.eventbus.IMLoginRequestModel;
import tech.jiangtao.support.kit.eventbus.IMLoginResponseModel;
import tech.jiangtao.support.kit.eventbus.IMDeleteContactRequestModel;
import tech.jiangtao.support.kit.eventbus.IMNotificationConnection;
import tech.jiangtao.support.kit.eventbus.IMMessageResponseModel;
import tech.jiangtao.support.kit.eventbus.QueryUser;
import tech.jiangtao.support.kit.eventbus.QueryUserResult;
import tech.jiangtao.support.kit.eventbus.IMContactDealModel;
import tech.jiangtao.support.kit.eventbus.RegisterAccount;
import tech.jiangtao.support.kit.eventbus.RegisterResult;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.model.Result;
import tech.jiangtao.support.kit.model.User;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.kit.reciever.TickBroadcastReceiver;
import xiaofei.library.hermes.Hermes;
import xiaofei.library.hermes.HermesListener;
import xiaofei.library.hermes.HermesService;
import xiaofei.library.hermeseventbus.HermesEventBus;

import static xiaofei.library.hermes.Hermes.getContext;

public class SupportService extends Service
    implements ChatManagerListener, ConnectionListener, RosterListener,InvitationListener {

  private static final String TAG = SupportService.class.getSimpleName();
  private XMPPTCPConnection mXMPPConnection;
  private AccountManager mAccountManager;
  private Roster mRoster;
  private VCardManager mVCardManager;
  private AppPreferences mAppPreferences;
  private SupportServiceConnection mSupportServiceConnection;
  private SupportBinder mSupportBinder;
  private Presence mFriendsPresence;
  private MultiUserChatManager mMultiUserChatManager;
  private ChatManager mChatManager;
  private AccountServiceApi mAccountServiceApi;
  private UserServiceApi mUserServiceApi;
  private OfflineMessageManager mOfflineMessageManager;
  private PingManager mPingManager;

  @Override public void onCreate() {
    super.onCreate();
    mAppPreferences = new AppPreferences(this);
    mAccountServiceApi = ApiService.getInstance().createApiService(AccountServiceApi.class);
    if (mSupportBinder == null) {
      mSupportBinder = new SupportBinder();
    }
    mSupportServiceConnection = new SupportServiceConnection();
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    if (!HermesEventBus.getDefault().isRegistered(this)) {
      HermesEventBus.getDefault().register(this);
    }
    IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
    TickBroadcastReceiver receiver = new TickBroadcastReceiver();
    registerReceiver(receiver, filter);
    this.bindService(new Intent(this, XMPPService.class), mSupportServiceConnection,
        Context.BIND_IMPORTANT);
    connect(true);
    return START_STICKY;
  }

  @Subscribe(threadMode = ThreadMode.BACKGROUND)
  public void onMessage(IMNotificationConnection connection) {
    LogUtils.i(TAG, "是否连接" + mXMPPConnection.isConnected());
    LogUtils.i(TAG, "是否认证" + mXMPPConnection.isAuthenticated());
    if (connection.connectChoice && !mXMPPConnection.isConnected()) {
      //收到广播，开始连接
      LogUtils.i(TAG, "onMessage: 开始连接");
      connect(true);
    }
  }

  @Override public IBinder onBind(Intent intent) {
    return mSupportBinder;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    LogUtils.d(TAG, "onDestroy: 检测到SupportService被销毁");
    HermesEventBus.getDefault().unregister(this);
  }

  @Override public void chatCreated(Chat chat, boolean createdLocally) {
    Gson gson = new Gson();
    chat.addMessageListener((chat1, message) -> {
      tech.jiangtao.support.kit.model.jackson.Message messageBody = null;
      try {
        messageBody =
            gson.fromJson(message.getBody(), tech.jiangtao.support.kit.model.jackson.Message.class);
      } catch (JsonSyntaxException e) {
        LogUtils.e(TAG, "你根本不是司机，请发送json格式的数据---->" + message.getBody());
      }
      if (messageBody != null) {
        if (messageBody.getChatType().equals(MessageExtensionType.CHAT.toString())) {
          HermesEventBus.getDefault()
              .post(new IMMessageResponseModel(UUID.randomUUID().toString(), messageBody, new Date(),
                  chat1.getThreadID(), false, MessageAuthor.FRIEND));
        } else if (messageBody.getChatType().equals(MessageExtensionType.GROUP_CHAT.toString())) {
          HermesEventBus.getDefault()
              .post(new IMMessageResponseModel(UUID.randomUUID().toString(), messageBody, new Date(),
                  chat1.getThreadID(), false, MessageAuthor.FRIEND));
        } else if (messageBody.getChatType().equals(MessageExtensionType.PUSH.toString())) {
          // 发送广播
          Intent intent = new Intent(this.getClass().getCanonicalName());
          LogUtils.d(TAG, this.getClass().getCanonicalName());
          sendBroadcast(intent);
          tech.jiangtao.support.kit.model.jackson.Message message1 =
              new tech.jiangtao.support.kit.model.jackson.Message();
          message1.setMsgSender(message.getTo());
          message1.setMessage("1");
          sendMessage(message1);
          // 发送消息到服务求确定已经收到消息
        }
      }
    });
  }

  /**
   * 发送消息
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void sendMessage(
      tech.jiangtao.support.kit.model.jackson.Message message) {
    Chat chat = ChatManager.getInstanceFor(mXMPPConnection).createChat(message.getMsgReceived());
    Observable.create((Observable.OnSubscribe<Message>) subscriber -> {
      try {
        Message message1 = new Message();
        String msg = new Gson().toJson(message);
        message1.setBody(msg);
        chat.sendMessage(message1);
        subscriber.onNext(message1);
      } catch (SmackException.NotConnectedException e) {
        connect(true);
        subscriber.onError(e);
        e.printStackTrace();
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
      LogUtils.d(TAG, "sendMessage: 发送成功");
      LogUtils.d(TAG, "sendMessage: 打印出别人的jid为:" + s.getTo());
      if (s.getBody() != null) {
        if (message.getChatType().equals(MessageExtensionType.CHAT.toString())) {
          HermesEventBus.getDefault()
              .postSticky(
                  new IMMessageResponseModel(UUID.randomUUID().toString(), message, new Date(), s.getThread(),
                      false, MessageAuthor.OWN));
        } else if (message.getChatType().equals(MessageExtensionType.GROUP_CHAT.toString())) {
          HermesEventBus.getDefault()
              .postSticky(
                  new IMMessageResponseModel(UUID.randomUUID().toString(), message, new Date(), s.getThread(),
                      false, MessageAuthor.OWN));
        }
      }
    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
        LogUtils.e(TAG, "消息发送失败" + throwable.getMessage());
      }
    });
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void loginEvent(IMLoginRequestModel param) {
    LogUtils.d(TAG, "loginEvent: 进入登录");
    login(param.username, param.password);
  }

  public void connect(boolean needAutoLogin) {
    if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
      init();
      Observable.create((Observable.OnSubscribe<AbstractXMPPConnection>) subscriber -> {
        try {
          subscriber.onNext(mXMPPConnection.connect());
        } catch (SmackException | IOException | XMPPException e) {
          e.printStackTrace();
          subscriber.onError(new Throwable(e.toString()));
        }
      })
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(abstractXMPPConnection -> {
            LogUtils.d(TAG, "connect: 连接成功");
            mXMPPConnection = (XMPPTCPConnection) abstractXMPPConnection;
            try {
              String username = mAppPreferences.getString(SupportIM.USER_REAL_NAME);
              String password = mAppPreferences.getString(SupportIM.USER_PASSWORD);
              LogUtils.d(TAG, username);
              LogUtils.d(TAG, password);
              if (username != null
                  && password != null
                  && needAutoLogin
                  && !mXMPPConnection.isAuthenticated()) {
                login(username, password);
              }
            } catch (ItemNotFoundException e) {
              e.printStackTrace();
            }
          }, new ErrorAction() {
            @Override public void call(Throwable throwable) {
              super.call(throwable);
              LogUtils.d(TAG, "call: 连接失败");
            }
          });
    }
  }

  public void login(String username, String password) {
    Observable.create(subscriber2 -> {
      try {
        if (mXMPPConnection.isConnected()) {
          mXMPPConnection.login(username, password);
        } else {
          connect(true);
          mXMPPConnection.login(username, password);
        }
        subscriber2.onNext(null);
      } catch (XMPPException | SmackException | IOException e) {
        subscriber2.onError(e);
        e.printStackTrace();
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {
      LogUtils.d(TAG, "login: 登录成功");
      // 保存用户jid,获取用户数据，保存到mAppPreferences
      mUserServiceApi = ApiService.getInstance().createApiService(UserServiceApi.class);
      mAppPreferences.put(SupportIM.USER_ID, username + "@" + SupportIM.mDomain);
      mAppPreferences.put(SupportIM.USER_NAME, username);
      mAppPreferences.put(SupportIM.USER_REAL_NAME, username);
      mAppPreferences.put(SupportIM.USER_PASSWORD, password);
      Gson gsonNormal = new Gson();
      User userNormal = new User();
      userNormal.setNickName(username);
      userNormal.setUserId(username + "@" + SupportIM.mDomain);
      String valueNormal = gsonNormal.toJson(userNormal);
      mAppPreferences.put(SupportIM.USER, valueNormal);
      mUserServiceApi.selfAccount(StringSplitUtil.userJid(username))
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
              user -> HermesEventBus.getDefault().postSticky(new IMLoginResponseModel(user, null)),
              new ErrorAction() {
                @Override public void call(Throwable throwable) {
                  super.call(throwable);
                  LogUtils.d(TAG, "call: 登录失败" + throwable);
                  Result result = new Result(401, throwable.getMessage());
                  HermesEventBus.getDefault().postSticky(new IMLoginResponseModel(null, result));
                }
              });
    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
        LogUtils.d(TAG, "call: 登录失败" + throwable);
        Result result = new Result(401, throwable.getMessage());
        HermesEventBus.getDefault().postSticky(new IMLoginResponseModel(null, result));
      }
    });
  }

  public void init() {
    XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
    configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
    configBuilder.setDebuggerEnabled(true);
    configBuilder.setResource(SupportIM.mResource);
    configBuilder.setServiceName(SupportIM.mDomain);
    configBuilder.setHost(SupportIM.mHost);
    configBuilder.setPort(SupportIM.mPort);
    mXMPPConnection = new XMPPTCPConnection(configBuilder.build());
    mXMPPConnection.addConnectionListener(this);
  }

  @Override public void connected(XMPPConnection connection) {
    mXMPPConnection = (XMPPTCPConnection) connection;
  }

  @Override public void authenticated(XMPPConnection connection, boolean resumed) {
    mXMPPConnection = (XMPPTCPConnection) connection;
    connectSuccessPerform();
  }

  @Override public void connectionClosed() {
    LogUtils.d(TAG, "connectionClosed: 连接被关闭");
  }

  @Override public void connectionClosedOnError(Exception e) {
    LogUtils.d(TAG, "connectionClosedOnError: 连接因为错误被关闭" + e.getMessage());
  }

  @Override public void reconnectionSuccessful() {
    LogUtils.d(TAG, "reconnectionSuccessful: 重连成功");
    connectSuccessPerform();
  }

  @Override public void reconnectingIn(int seconds) {
    LogUtils.d(TAG, "reconnectingIn: 正在重连" + seconds);
  }

  @Override public void reconnectionFailed(Exception e) {
    LogUtils.d(TAG, "reconnectionFailed: 重连失败，失败原因" + e.getMessage());
  }

  public void connectSuccessPerform() {
    Presence presence = new Presence(Presence.Type.available);
    presence.setStatus("在线");
    try {
      mXMPPConnection.sendStanza(presence);
    } catch (SmackException.NotConnectedException e) {
      e.printStackTrace();
    }
    mPingManager = PingManager.getInstanceFor(mXMPPConnection);
    mPingManager.setPingInterval(30);
    ServerPingWithAlarmManager.getInstanceFor(mXMPPConnection).isEnabled();
    mChatManager = ChatManager.getInstanceFor(mXMPPConnection);
    mChatManager.addChatListener(this);
    mMultiUserChatManager = MultiUserChatManager.getInstanceFor(mXMPPConnection);
    mMultiUserChatManager.addInvitationListener(this);
    mOfflineMessageManager = new OfflineMessageManager(mXMPPConnection);
    pullOfflineMessage();
    rosterPresence();
  }

  /**
   * 获取离线消息
   */
  private void pullOfflineMessage() {
    Observable.create((Observable.OnSubscribe<List<Message>>) subscriber -> {
      try {
        subscriber.onNext(mOfflineMessageManager.getMessages());
      } catch (SmackException.NoResponseException | SmackException.NotConnectedException | XMPPException.XMPPErrorException e) {
        e.printStackTrace();
        subscriber.onError(e);
      }
    })
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(Schedulers.io())
        .subscribe(messages -> {
          // 解析消息，然后推到前台
          Gson gson = new Gson();
          for (Message message : messages) {
            // 发消息到XMPPService
            LogUtils.d(TAG, "获取到离线消息:" + message.getBody());
            tech.jiangtao.support.kit.model.jackson.Message messageBody = null;
            try {
              messageBody =
                  gson.fromJson(message.getBody(), tech.jiangtao.support.kit.model.jackson.Message.class);
            } catch (JsonSyntaxException e) {
              LogUtils.e(TAG, "你根本不是司机，请发送json格式的数据---->" + message.getBody());
            }
            if (messageBody != null) {
              if (messageBody.getChatType().equals(MessageExtensionType.CHAT.toString())) {
                HermesEventBus.getDefault()
                    .post(new IMMessageResponseModel(UUID.randomUUID().toString(), messageBody, new Date(),
                        UUID.randomUUID().toString(), false, MessageAuthor.FRIEND));
              } else if (messageBody.getChatType().equals(MessageExtensionType.GROUP_CHAT.toString())) {
                HermesEventBus.getDefault()
                    .post(new IMMessageResponseModel(UUID.randomUUID().toString(), messageBody, new Date(),
                        UUID.randomUUID().toString(), false, MessageAuthor.FRIEND));
              } else if (messageBody.getChatType().equals(MessageExtensionType.PUSH.toString())) {
                // 发送广播
                Intent intent = new Intent(this.getClass().getCanonicalName());
                LogUtils.d(TAG, this.getClass().getCanonicalName());
                sendBroadcast(intent);
                tech.jiangtao.support.kit.model.jackson.Message message1 =
                    new tech.jiangtao.support.kit.model.jackson.Message();
                message1.setMsgSender(message.getTo());
                message1.setMessage("1");
                sendMessage(message1);
                // 发送消息到服务求确定已经收到消息
              }
            }
          }
        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
          }
        });
  }

  /**
   * 添加好友
   */
  public void rosterPresence() {
    Roster roster = Roster.getInstanceFor(mXMPPConnection);
    // 获取用户最近联系人
    roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
    roster.addRosterListener(this);
    StanzaFilter filter = new StanzaTypeFilter(Presence.class);
    StanzaListener listener = new StanzaListener() {
      @Override public void processPacket(Stanza packet)
          throws SmackException.NotConnectedException {
        String from = StringSplitUtil.splitDivider(packet.getFrom());
        mUserServiceApi.selfAccount(from)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(user -> {
              ContactRealm realm = new ContactRealm();
              realm.setUid(user.getUid());
              realm.setAvatar(user.getAvatar());
              realm.setSignature(user.getSignature());
              realm.setSex(user.isSex());
              realm.setNickName(user.nickName);
              realm.setUserId(user.getUserId());
              realm.setRelative(user.relative);
              realm.setNid(user.nid);
              if (((Presence) packet).getType().equals(Presence.Type.subscribe)) {
                // 收到好友请求
                LogUtils.d(TAG, "----> : 接收到好友请求");
                HermesEventBus.getDefault()
                    .postSticky(new IMContactRequestNotificationModel(realm,
                        IMFNotificationType.SUBSCRIBE));
              } else if (((Presence) packet).getType().equals(Presence.Type.unsubscribe)) {
                // 不同意添加好友
                LogUtils.d(TAG, "----> : 对方不同意好友请求");
                HermesEventBus.getDefault()
                    .postSticky(new IMContactRequestNotificationModel(realm,
                        IMFNotificationType.UNSUBSCRIBE));
              } else if (((Presence) packet).getType().equals(Presence.Type.subscribed)) {
                LogUtils.d(TAG, "----> : 对方同意添加好友。");
                Presence pres = new Presence(Presence.Type.subscribed);
                pres.setTo(packet.getFrom());
                try {
                  mXMPPConnection.sendStanza(pres);
                } catch (SmackException.NotConnectedException e) {
                  e.printStackTrace();
                }
                HermesEventBus.getDefault()
                    .postSticky(new IMContactRequestNotificationModel(realm,
                        IMFNotificationType.SUBSCRIBED));
              }
            }, new ErrorAction() {
              @Override public void call(Throwable throwable) {
                super.call(throwable);
                LogUtils.e(TAG, "----> : 从网络中获取数据失败");
              }
            });
      }
    };
    mXMPPConnection.addSyncStanzaListener(listener, filter);
  }

  /**
   * 处理好友请求
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void onDealFriendInvited(
      IMContactDealModel request) {
    if (request.value) {
      Observable.create((Observable.OnSubscribe<Presence>) subscriber -> {
        Presence subscription = new Presence(Presence.Type.subscribed);
        subscription.setMode(Presence.Mode.available);
        subscription.setTo(request.userId);
        try {
          mXMPPConnection.sendStanza(subscription);
          subscriber.onNext(subscription);
        } catch (SmackException.NotConnectedException e) {
          e.printStackTrace();
          subscriber.onError(e);
        }
      })
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(presence -> {
            HermesEventBus.getDefault().postSticky(new IMContactDealResponseModel(true));
          }, new ErrorAction() {
            @Override public void call(Throwable throwable) {
              super.call(throwable);
              LogUtils.d(TAG, throwable.getMessage());
              HermesEventBus.getDefault().postSticky(new IMContactDealResponseModel(false));
            }
          });
    } else {
      Observable.create((Observable.OnSubscribe<Presence>) subscriber -> {
        Presence subscription = new Presence(Presence.Type.unsubscribe);
        subscription.setTo(request.userId);
        try {
          mXMPPConnection.sendStanza(subscription);
          subscriber.onNext(subscription);
        } catch (SmackException.NotConnectedException e) {
          e.printStackTrace();
          subscriber.onError(e);
        }
      })
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(presence -> {
            HermesEventBus.getDefault().postSticky(new IMContactDealResponseModel(true));
          }, new ErrorAction() {
            @Override public void call(Throwable throwable) {
              super.call(throwable);
              LogUtils.d(TAG, throwable.getMessage());
              HermesEventBus.getDefault().postSticky(new IMContactDealResponseModel(false));
            }
          });
    }
  }

  /**
   * roster添加了用户
   */
  @Override public void entriesAdded(Collection<String> addresses) {
    LogUtils.d(TAG, "entriesAdded: 好友添加成功");
    printRoster(addresses);
  }

  /**
   * roster中用户更新信息
   */
  @Override public void entriesUpdated(Collection<String> addresses) {
    LogUtils.d(TAG, "entriesUpdated: 好友更新成功");
    printRoster(addresses);
  }

  /**
   * roster中有好友被删除
   */
  @Override public void entriesDeleted(Collection<String> addresses) {
    LogUtils.d(TAG, "entriesDeleted: 好友删除成功");
    printRoster(addresses);
  }

  public void printRoster(Collection<String> collection) {
    for (String s : collection) {
      LogUtils.d(TAG, s);
    }
  }

  /**
   * 状态改变
   */
  @Override public void presenceChanged(Presence presence) {
    if (presence.getType().equals(Presence.Type.available)) {
      LogUtils.d(TAG, "在线");
    } else if (presence.getType().equals(Presence.Type.unavailable)) {
      LogUtils.d(TAG, "离线");
    } else if (presence.getType().equals(Presence.Type.subscribe)) {
      LogUtils.d(TAG, "请求订阅");
    } else if (presence.getType().equals(Presence.Type.subscribed)) {
      LogUtils.d(TAG, "订阅成功");
    } else if (presence.getType().equals(Presence.Type.unsubscribe)) {
      LogUtils.d(TAG, "请求取消订阅");
    } else if (presence.getType().equals(Presence.Type.unsubscribed)) {
      LogUtils.d(TAG, "取消订阅成功");
    } else if (presence.getType().equals(Presence.Type.error)) {
      LogUtils.d(TAG, "发生错误");
    } else if (presence.getType().equals(Presence.Type.probe)) {
      LogUtils.d(TAG, "未知状态");
    }
  }

  /**
   * 注册账户
   * account {@link RegisterAccount}
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void createAccount(RegisterAccount account) {
    mAccountManager = AccountManager.getInstance(mXMPPConnection);
    Observable.create(subscriber -> {
      try {
        LogUtils.d(TAG, "createAccount: " + account.username + "        " + account.password);
        if (mAccountManager.supportsAccountCreation()) {
          mAccountManager.createAccount(account.username, account.password);
        }
        subscriber.onNext(null);
      } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
        e.printStackTrace();
        connect(false);
        subscriber.onError(e);
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {
      login(account.username, account.password);
      HermesEventBus.getDefault().post(new RegisterResult(account, null));
    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
        LogUtils.d(TAG, "call: 创建账户失败");
        HermesEventBus.getDefault().post(new RegisterResult(null, throwable.getMessage()));
      }
    });
  }

  @Deprecated @Subscribe(threadMode = ThreadMode.MAIN) public void queryUser(QueryUser user) {
    mVCardManager = VCardManager.getInstanceFor(mXMPPConnection);
    Observable.create((Observable.OnSubscribe<VCard>) subscriber -> {
      try {
        subscriber.onNext(mVCardManager.loadVCard(user.username + "@" + SupportIM.mDomain));
      } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
        e.printStackTrace();
        subscriber.onError(e);
        connect(true);
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(vCard -> {
      LogUtils.d(TAG, "queryUser: " + vCard.toXML());
      LogUtils.d(TAG, "queryUser: " + vCard.toString());
      // 需要使用回调
      HermesEventBus.getDefault()
          .post(new QueryUserResult(StringSplitUtil.splitDivider(vCard.getFrom()),
              vCard.getNickName(), vCard.getField("avatar"), true));
    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
        LogUtils.d(TAG, "call: 搜索用户的vcard失败    " + throwable.getMessage());
      }
    });
  }

  /**
   * 删除好友
   *
   * @param user {@link IMDeleteContactRequestModel}
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void deleteFriends(
      IMDeleteContactRequestModel user) {
    mRoster = Roster.getInstanceFor(mXMPPConnection);
    RosterEntry entry = mRoster.getEntry(user.userId);
    print(mRoster);
    if (entry != null) {
      Observable.create((Observable.OnSubscribe<RosterEntry>) subscriber -> {
        try {
          mRoster.removeEntry(entry);
          subscriber.onNext(null);
        } catch (SmackException.NotLoggedInException | SmackException.NoResponseException | SmackException.NotConnectedException | XMPPException.XMPPErrorException e) {
          e.printStackTrace();
          subscriber.onError(e);
          connect(true);
        }
      })
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(rosterEntry -> {
            //删除成功
            HermesEventBus.getDefault().post(new IMDeleteContactResponseModel(user.userId, null));
          }, new ErrorAction() {
            @Override public void call(Throwable throwable) {
              super.call(throwable);
              LogUtils.d(TAG, "删除contact失败");
              HermesEventBus.getDefault()
                  .post(new IMDeleteContactResponseModel(null,
                      new Result(400, throwable.getMessage())));
            }
          });
    } else {
      HermesEventBus.getDefault()
          .post(
              new IMDeleteContactResponseModel(null, new Result(404, "You have not this friends")));
    }
  }

  public void print(Roster roster) {
    for (RosterGroup group : mRoster.getGroups()) {
      LogUtils.d(TAG, "组名:" + group.getName());
      LogUtils.d(TAG, group.toString());
    }
    for (RosterEntry entry1 : mRoster.getEntries()) {
      LogUtils.d(TAG, entry1.toString());
      LogUtils.d(TAG, entry1.getName());
      LogUtils.d(TAG, entry1.getUser());
    }
  }

  /**
   * 获取所有的好友,有点唐突
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void contactCollection(
      IMContactRequestModel model) {
    mRoster = Roster.getInstanceFor(mXMPPConnection);
    print(mRoster);
    Observable.create((Observable.OnSubscribe<Set<RosterEntry>>) subscriber -> {
      subscriber.onNext(mRoster.getEntries());
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(rosterEntries -> {
          for (RosterEntry entry : rosterEntries) {

            Observable.create((Observable.OnSubscribe<VCard>) subscriber -> {
              if (mVCardManager == null) {
                mVCardManager = VCardManager.getInstanceFor(mXMPPConnection);
              }
              try {
                subscriber.onNext(mVCardManager.loadVCard(entry.getUser()));
              } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
                e.printStackTrace();
                subscriber.onError(e);
              }
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(vCard -> {
                  Log.d(TAG, "contactCollection: " + entry.getUser());
                  ContactRealm account = new ContactRealm();
                  if (vCard.getField("NICKNAME") != null) {
                    account.setAvatar(
                        vCard.getField("AVATAR") != null ? vCard.getField("AVATAR") : "");
                    account.setNickName(vCard.getField("NICKNAME"));
                    account.setSex(
                        vCard.getField("SEX") != null && !vCard.getField("SEX").equals("男"));
                    account.setSignature(
                        vCard.getField("SIGNATURE") != null ? vCard.getField("SIGNATURE") : "");
                    account.setUserId(entry.getUser());
                    HermesEventBus.getDefault()
                        .postSticky(new IMContactResponseCollection(account));
                  } else {
                    mUserServiceApi.selfAccount(entry.getUser())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(user -> {
                          ContactRealm realm = new ContactRealm();
                          realm.setUid(user.getUid());
                          realm.setAvatar(user.getAvatar());
                          realm.setSignature(user.getSignature());
                          realm.setSex(user.isSex());
                          realm.setUserId(user.getUserId());
                          realm.setRelative(user.relative);
                          realm.setNid(user.nid);
                          HermesEventBus.getDefault()
                              .postSticky(new IMContactResponseCollection(realm));
                        }, new ErrorAction() {
                          @Override public void call(Throwable throwable) {
                            super.call(throwable);
                            LogUtils.e(TAG, "---->从网络中获取数据失败");
                          }
                        });
                  }
                }, new ErrorAction() {
                  @Override public void call(Throwable throwable) {
                    super.call(throwable);
                    HermesEventBus.getDefault().postSticky(new IMContactResponseCollection(null));
                  }
                });
          }
        });
  }

  /**
   * 发送添加好友请求
   *
   * @param user {@link IMAddContactRequestModel}
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void addContactFirend(
      IMAddContactRequestModel user) {
    mRoster = Roster.getInstanceFor(mXMPPConnection);
    Observable.create(subscriber -> {
      try {
        mRoster.createEntry(user.userId, user.nickName, null);
        subscriber.onNext(user);
      } catch (SmackException.NotLoggedInException | SmackException.NoResponseException | SmackException.NotConnectedException | XMPPException.XMPPErrorException e) {
        e.printStackTrace();
        subscriber.onError(e);
        if (e instanceof SmackException.NotConnectedException) {
          connect(true);
        }
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {
      //发送添加好友成功
      LogUtils.d(TAG, "---->: 向用户发送添加好友请求成功");
      HermesEventBus.getDefault()
          .postSticky(new IMAddContactResponseModel(new Result(200, "Success")));
    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
        //发送添加好友请求失败
        LogUtils.d(TAG, "call: 添加好友请求失败");
        HermesEventBus.getDefault()
            .postSticky(new IMAddContactResponseModel(new Result(400, "Success")));
      }
    });
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void disconnect(IMAccountExitRequestModel event) {
    mXMPPConnection.disconnect();
    connect(false);
  }

  /**
   * 接收到群邀请信息
   * @param conn
   * @param room
   * @param inviter
   * @param reason
   * @param password
   * @param message
   */
  @Override public void invitationReceived(XMPPConnection conn, MultiUserChat room, String inviter,
      String reason, String password, Message message) {

  }

  //@Subscribe(threadMode = ThreadMode.MAIN)
  //public void createRoom(){
  //  MultiUserChat multiUserChat = mMultiUserChatManager.getMultiUserChat()
  //}

  private class SupportServiceConnection implements ServiceConnection {

    @Override public void onServiceConnected(ComponentName name, IBinder service) {
      LogUtils.d(TAG, "onServiceConnected: supportService连接成功");
    }

    @Override public void onServiceDisconnected(ComponentName name) {
      LogUtils.d(TAG, "onServiceDisconnected: SupportService连接被关闭");
      Intent intent = new Intent(SupportService.this, XMPPService.class);
      SupportService.this.startService(intent);
      SupportService.this.bindService(intent, mSupportServiceConnection, Context.BIND_IMPORTANT);
    }
  }

  private class SupportBinder extends SupportAIDLConnection.Stub {

    @Override public String getServiceName() throws RemoteException {
      return "SupportService连接";
    }
  }
}
