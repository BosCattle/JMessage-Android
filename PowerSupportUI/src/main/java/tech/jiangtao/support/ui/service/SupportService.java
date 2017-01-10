package tech.jiangtao.support.ui.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.packet.DefaultExtensionElement;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.InvitationRejectionListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import de.measite.minidns.record.MX;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;
import org.jivesoftware.smackx.xdata.packet.DataForm;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.archive.MessageArchiveIQProvider;
import tech.jiangtao.support.kit.archive.MessageArchiveRequestIQ;
import tech.jiangtao.support.kit.archive.MessageArchiveStanzaFilter;
import tech.jiangtao.support.kit.archive.MessageArchiveStanzaListener;
import tech.jiangtao.support.kit.archive.type.MessageAuthor;
import tech.jiangtao.support.kit.archive.type.MessageExtensionType;
import tech.jiangtao.support.kit.eventbus.AddRosterEvent;
import tech.jiangtao.support.kit.eventbus.ContactEvent;
import tech.jiangtao.support.kit.eventbus.DeleteVCardRealm;
import tech.jiangtao.support.kit.eventbus.FriendRequest;
import tech.jiangtao.support.kit.eventbus.LocalVCardEvent;
import tech.jiangtao.support.kit.eventbus.LoginCallbackEvent;
import tech.jiangtao.support.kit.eventbus.LoginParam;
import tech.jiangtao.support.kit.eventbus.NotificationConnection;
import tech.jiangtao.support.kit.eventbus.OwnVCardRealm;
import tech.jiangtao.support.kit.eventbus.QueryUser;
import tech.jiangtao.support.kit.eventbus.QueryUserResult;
import tech.jiangtao.support.kit.eventbus.RecieveFriend;
import tech.jiangtao.support.kit.eventbus.RecieveMessage;
import tech.jiangtao.support.kit.eventbus.RegisterAccount;
import tech.jiangtao.support.kit.eventbus.RegisterResult;
import tech.jiangtao.support.kit.eventbus.RosterEntryBus;
import tech.jiangtao.support.kit.eventbus.TextMessage;
import tech.jiangtao.support.kit.eventbus.UnRegisterEvent;
import tech.jiangtao.support.kit.eventbus.muc.PullJoinedRooms;
import tech.jiangtao.support.kit.init.SupportIM;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.ui.SupportAIDLConnection;
import tech.jiangtao.support.ui.reciever.TickBroadcastReceiver;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.PinYinUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import xiaofei.library.hermeseventbus.HermesEventBus;

import static xiaofei.library.hermes.Hermes.getContext;

public class SupportService extends Service
    implements ChatManagerListener, ConnectionListener, RosterListener, InvitationListener,
    InvitationRejectionListener {

  private static final String TAG = SupportService.class.getSimpleName();
  private XMPPTCPConnection mXMPPConnection;
  private AccountManager mAccountManager;
  private Roster mRoster;
  private VCardManager mVCardManager;
  private AppPreferences appPreferences;
  private SupportServiceConnection mSupportServiceConnection;
  private SupportBinder mSupportBinder;
  private Presence mFriendsPresence;
  private MultiUserChatManager mMultiUserChatManager;
  private ChatManager mChatManager;

  @Override public void onCreate() {
    super.onCreate();
    appPreferences = new AppPreferences(this);
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
  public void onMessage(NotificationConnection connection) {
    LogUtils.d(TAG, "是否连接" + mXMPPConnection.isConnected());
    LogUtils.d(TAG, "是否认证" + mXMPPConnection.isAuthenticated());
    if (connection.connectChoice && !mXMPPConnection.isConnected()) {
      //收到广播，开始连接
      LogUtils.d(TAG, "onMessage: 开始连接");
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
    chat.addMessageListener((chat1, message) -> {
      DefaultExtensionElement messageExtension =
          (DefaultExtensionElement) message.getExtension("message:extension");
      if (message.getBody() != null) {
        if (messageExtension == null
            || messageExtension.getValue("type") == null
            || messageExtension.getValue("type").equals(MessageExtensionType.TEXT.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(message.getStanzaId(), message.getType(), message.getFrom(),
                  message.getTo(), chat1.getThreadID(), message.getBody(),
                  MessageExtensionType.TEXT, false, MessageAuthor.FRIEND));
        }
        if (messageExtension.getValue("type").equals(MessageExtensionType.IMAGE.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(message.getStanzaId(), message.getType(), message.getFrom(),
                  message.getTo(), chat1.getThreadID(), message.getBody(),
                  MessageExtensionType.IMAGE, false, MessageAuthor.FRIEND));
        }
        if (messageExtension.getValue("type").equals(MessageExtensionType.AUDIO.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(message.getStanzaId(), message.getType(), message.getFrom(),
                  message.getTo(), chat1.getThreadID(), message.getBody(),
                  MessageExtensionType.AUDIO, false, MessageAuthor.FRIEND));
        }
        if (messageExtension.getValue("type").equals(MessageExtensionType.VIDEO.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(message.getStanzaId(), message.getType(), message.getFrom(),
                  message.getTo(), chat1.getThreadID(), message.getBody(),
                  MessageExtensionType.VIDEO, false, MessageAuthor.FRIEND));
        }
      }
      //发送消息到守护服务，先保存会话到另外一个会话表，然后保存消息到历史消息表
    });
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void sendMessage(TextMessage message) {
    Chat chat = ChatManager.getInstanceFor(mXMPPConnection).createChat(message.userJID);
    Observable.create(new Observable.OnSubscribe<Message>() {
      @Override public void call(Subscriber<? super Message> subscriber) {
        try {
          Message message1 = new Message();
          message1.setBody(message.message);
          DefaultExtensionElement extensionElement =
              new DefaultExtensionElement("message_type", "message:extension");
          extensionElement.setValue("type", message.messageType.toString());
          message1.addExtension(extensionElement);
          chat.sendMessage(message1);
          subscriber.onNext(message1);
        } catch (SmackException.NotConnectedException e) {
          connect(true);
          subscriber.onError(e);
          e.printStackTrace();
        }
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
      LogUtils.d(TAG, "sendMessage: 发送成功");
      //缓存消息
      DefaultExtensionElement messageExtension =
          (DefaultExtensionElement) s.getExtension("message:extension");
      LogUtils.d(TAG, "sendMessage: 打印出别人的jid为:" + s.getTo());
      String userJid = null;
      final AppPreferences appPreferences = new AppPreferences(getContext());
      try {
        userJid = StringSplitUtil.splitDivider(appPreferences.getString("userJid"));
      } catch (ItemNotFoundException e) {
        e.printStackTrace();
      }
      if (s.getBody() != null) {
        if (messageExtension == null
            || messageExtension.getValue("type") == null
            || messageExtension.getValue("type").equals(MessageExtensionType.TEXT.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(s.getStanzaId(), s.getType(), userJid, s.getTo(),
                  chat.getThreadID(), s.getBody(), MessageExtensionType.TEXT, false,
                  MessageAuthor.OWN));
        }
        if (messageExtension.getValue("type").equals(MessageExtensionType.IMAGE.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(s.getStanzaId(), s.getType(), userJid, s.getTo(),
                  chat.getThreadID(), s.getBody(), MessageExtensionType.IMAGE, false,
                  MessageAuthor.OWN));
        }
        if (messageExtension.getValue("type").equals(MessageExtensionType.AUDIO.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(s.getStanzaId(), s.getType(), userJid, s.getTo(),
                  chat.getThreadID(), s.getBody(), MessageExtensionType.AUDIO, false,
                  MessageAuthor.OWN));
        }
        if (messageExtension.getValue("type").equals(MessageExtensionType.VIDEO.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(s.getStanzaId(), s.getType(), userJid, s.getTo(),
                  chat.getThreadID(), s.getBody(), MessageExtensionType.VIDEO, false,
                  MessageAuthor.OWN));
        }
      }
    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
      }
    });
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void loginEvent(LoginParam param) {
    LogUtils.d(TAG, "loginEvent: 进入登录");
    login(param.username, param.password);
  }

  public void sendMessageArchive(String jid, String time) {
    MessageArchiveRequestIQ iq = new MessageArchiveRequestIQ(null);
    iq.setJid(jid);
    iq.setTime(time);
    iq.setType(IQ.Type.get);
    LogUtils.e(TAG, "sendMessageArchive: " + iq.toXML());
    try {
      mXMPPConnection.sendStanza(iq);
    } catch (SmackException.NotConnectedException e) {
      e.printStackTrace();
      connect(true);
    }
  }

  public void requestAllMessageArchive(String time) {
    MessageArchiveRequestIQ iq = new MessageArchiveRequestIQ(null);
    if (time == null || time.equals("")) {
      iq.setTime("2017-01-01T00:00:00Z");
    } else {
      iq.setTime(time);
    }
    iq.setType(IQ.Type.get);
    LogUtils.e(TAG, "sendMessageArchive: " + iq.toXML());
    try {
      mXMPPConnection.sendStanza(iq);
    } catch (SmackException.NotConnectedException e) {
      e.printStackTrace();
      connect(true);
    }
  }

  public void connect(boolean needAutoLogin) {
    if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
      init();
      Observable.create(new Observable.OnSubscribe<AbstractXMPPConnection>() {
        @Override public void call(Subscriber<? super AbstractXMPPConnection> subscriber) {
          try {
            subscriber.onNext(mXMPPConnection.connect());
          } catch (SmackException | IOException | XMPPException e) {
            e.printStackTrace();
            subscriber.onError(new Throwable(e.toString()));
          }
        }
      })
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(abstractXMPPConnection -> {
            LogUtils.d(TAG, "connect: 连接成功");
            mXMPPConnection = (XMPPTCPConnection) abstractXMPPConnection;
            try {
              String username = appPreferences.getString("username");
              String password = appPreferences.getString("password");
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
      // 保存用户jid
      appPreferences.put("userJid", mXMPPConnection.getUser());
      appPreferences.put("username", username);
      appPreferences.put("password", password);
      HermesEventBus.getDefault().postSticky(new LoginCallbackEvent("登录成功", null));
      LocalVCardEvent event = new LocalVCardEvent();
      event.setJid(StringSplitUtil.splitDivider(mXMPPConnection.getUser()));
      addOrUpdateVCard(event);
      getRoster(new ContactEvent());
    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
        LogUtils.d(TAG, "call: 登录失败" + throwable);
        HermesEventBus.getDefault().post(new LoginCallbackEvent(null, throwable.getMessage()));
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
    mXMPPConnection.setPacketReplyTimeout(20000);
    mXMPPConnection.addConnectionListener(this);
  }

  @Override public void connected(XMPPConnection connection) {
    mXMPPConnection = (XMPPTCPConnection) connection;
  }

  @Override public void authenticated(XMPPConnection connection, boolean resumed) {
    mXMPPConnection = (XMPPTCPConnection) connection;
    HermesEventBus.getDefault().postSticky(new LoginCallbackEvent("登录成功", null));
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
    ProviderManager.addIQProvider("chat", "urn:xmpp:archive", new MessageArchiveIQProvider());
    mXMPPConnection.addAsyncStanzaListener(new MessageArchiveStanzaListener(),
        new MessageArchiveStanzaFilter());
    mChatManager = ChatManager.getInstanceFor(mXMPPConnection);
    mChatManager.addChatListener(this);
    mMultiUserChatManager = MultiUserChatManager.getInstanceFor(mXMPPConnection);
    mMultiUserChatManager.addInvitationListener(this);
    rosterPresence();
  }

  /**
   * 添加好友
   */
  public void rosterPresence() {
    Roster roster = Roster.getInstanceFor(mXMPPConnection);
    roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
    roster.addRosterListener(this);
    mXMPPConnection.addSyncStanzaListener(packet -> {
      mFriendsPresence = (Presence) packet;
      String from = mFriendsPresence.getFrom();//发送方
      String to = mFriendsPresence.getTo();//接收方
      if (mFriendsPresence.getType().equals(Presence.Type.subscribe)) {
        // 收到好友请求,现在的代码是自动自动接受好友请求
        LogUtils.d(TAG, "addFriend: 接受到好友请求");
        HermesEventBus.getDefault()
            .post(new FriendRequest(mFriendsPresence.getFrom(),
                StringSplitUtil.splitPrefix(mFriendsPresence.getFrom()), ""));
      } else if (mFriendsPresence.getType().equals(Presence.Type.unsubscribe)) {
        // 不同意添加好友
        LogUtils.d(TAG, "addFriend: 对方不同意好友请求");
        Presence newp = new Presence(Presence.Type.unsubscribed);
        newp.setMode(Presence.Mode.available);
        newp.setPriority(24);
        newp.setTo(mFriendsPresence.getFrom());
        mXMPPConnection.sendStanza(newp);
      } else if (mFriendsPresence.getType().equals(Presence.Type.subscribed)) {
        LogUtils.d(TAG, "addFriend: 对方同意添加好友。");
        //发送广播传递response字符串
        //对方同意添加好友";
      }
    }, stanza -> {
      if (stanza instanceof Presence) {
        Presence presence = (Presence) stanza;
        if (presence.getType().equals(Presence.Type.subscribed) || presence.getType()
            .equals(Presence.Type.subscribe) || presence.getType()
            .equals(Presence.Type.unsubscribed) || presence.getType()
            .equals(Presence.Type.unsubscribe)) {
          return true;
        }
      }
      return false;
    });
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onRecieveFriendRequest(RecieveFriend request) {
    if (request.agreeFriends) {
      // 这部分代码是同意添加好友请求
      Presence newp = new Presence(Presence.Type.subscribed);
      newp.setMode(Presence.Mode.available);
      newp.setPriority(24);
      newp.setTo(mFriendsPresence.getFrom());
      try {
        mXMPPConnection.sendStanza(newp);
        Presence subscription = new Presence(Presence.Type.subscribe);
        subscription.setTo(mFriendsPresence.getFrom());
        mXMPPConnection.sendStanza(subscription);
      } catch (SmackException.NotConnectedException e) {
        e.printStackTrace();
      }
    } else {
      Presence presenceRes = new Presence(Presence.Type.unsubscribe);
      presenceRes.setTo(mFriendsPresence.getFrom());
      try {
        mXMPPConnection.sendStanza(presenceRes);
      } catch (SmackException.NotConnectedException e) {
        e.printStackTrace();
      }
    }
  }

  //
  @Override public void entriesAdded(Collection<String> addresses) {
    LogUtils.d(TAG, "entriesAdded: 好友添加成功");
  }

  @Override public void entriesUpdated(Collection<String> addresses) {
    LogUtils.d(TAG, "entriesUpdated: 好友更新成功");
  }

  @Override public void entriesDeleted(Collection<String> addresses) {
    LogUtils.d(TAG, "entriesDeleted: 好友删除成功");
  }

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

  //获取网络通讯录
  @Subscribe(threadMode = ThreadMode.MAIN) public void getRoster(ContactEvent event) {
    mRoster = Roster.getInstanceFor(mXMPPConnection);
    mVCardManager = VCardManager.getInstanceFor(mXMPPConnection);
    Collection<RosterEntry> entries = mRoster.getEntries();
    LogUtils.d(TAG, "getContact:获取到我的好友数量" + entries.size());
    Set<RosterEntry> set = new HashSet<>();
    set.addAll(entries);
    for (RosterEntry en : set) {
      LogUtils.d(TAG, "updateContact: " + en.getUser());
      Observable.create((Observable.OnSubscribe<VCard>) subscriber -> {
        try {
          subscriber.onNext(mVCardManager.loadVCard(en.getUser()));
        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
          e.printStackTrace();
          subscriber.onError(e);
          if (e instanceof SmackException.NotConnectedException) connect(true);
        }
      }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(vCard -> {
        //更新本地数据库
        LogUtils.d(TAG, "getRoster: vcard" + vCard.getField("avatar"));
        LogUtils.d(TAG, "getRoster: 打印出各自的jid" + vCard.getFrom());
        LogUtils.d(TAG, "getRoster: " + en.getName());
        HermesEventBus.getDefault()
            .post(new VCardRealm(StringSplitUtil.splitDivider(vCard.getFrom()), en.getName(),
                vCard.getField("avatar"), PinYinUtils.getPinyinFirstLetter(en.getName()),
                PinYinUtils.ccs2Pinyin(en.getName()), true));
      }, new ErrorAction() {
        @Override public void call(Throwable throwable) {
          super.call(throwable);
          LogUtils.d(TAG, "call:获取通讯录发生错误 " + throwable.toString());
        }
      });
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void queryUser(QueryUser user) {
    mVCardManager = VCardManager.getInstanceFor(mXMPPConnection);
    Observable.create(new Observable.OnSubscribe<VCard>() {
      @Override public void call(Subscriber<? super VCard> subscriber) {
        try {
          subscriber.onNext(mVCardManager.loadVCard(user.username + "@" + SupportIM.mDomain));
        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
          e.printStackTrace();
          subscriber.onError(e);
          connect(true);
        }
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
   * @param user {@link RosterEntryBus}
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void deleteFriends(RosterEntryBus user) {
    mRoster = Roster.getInstanceFor(mXMPPConnection);
    RosterEntry entry = mRoster.getEntry(user.jid);
    Observable.create(new Observable.OnSubscribe<RosterEntry>() {
      @Override public void call(Subscriber<? super RosterEntry> subscriber) {
        try {
          mRoster.removeEntry(entry);
          subscriber.onNext(entry);
        } catch (SmackException.NotLoggedInException | SmackException.NoResponseException | SmackException.NotConnectedException | XMPPException.XMPPErrorException e) {
          e.printStackTrace();
          subscriber.onError(e);
          connect(true);
        }
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(rosterEntry -> {
          //删除成功
          HermesEventBus.getDefault().post(new DeleteVCardRealm(user.jid));
        });
  }

  /**
   * 发送添加好友请求
   *
   * @param user {@link AddRosterEvent}
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void addFirend(AddRosterEvent user) {
    mRoster = Roster.getInstanceFor(mXMPPConnection);
    Observable.create(subscriber -> {
      try {
        mRoster.createEntry(user.jid, user.nickname, null);
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
      LogUtils.d(TAG, "addFirend: 添加好友请求成功");
    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
        //发送添加好友请求失败
        LogUtils.d(TAG, "call: 添加好友请求失败");
      }
    });
  }

  //添加或者更新vCard;
  @Subscribe(threadMode = ThreadMode.MAIN) public void addOrUpdateVCard(
      LocalVCardEvent vCardRealm) {
    mVCardManager = VCardManager.getInstanceFor(mXMPPConnection);
    Observable.create((Observable.OnSubscribe<VCard>) subscriber -> {
      try {
        subscriber.onNext(mVCardManager.loadVCard(vCardRealm.getJid()));
      } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
        e.printStackTrace();
        subscriber.onError(e);
        connect(true);
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(vCard -> {
      LogUtils.d(TAG, "addOrUpdateVCard: 打印出VCard为" + vCard.toXML());
      LogUtils.d(TAG, "addOrUpdateVCard: 打印出头像为" + vCard.getField("avatar"));
      vCard.setField("sex", vCardRealm.getSex());
      vCard.setField("subject", vCardRealm.getSubject());
      vCard.setField("office", vCardRealm.getOffice());
      vCard.setField("voice", vCardRealm.getPhoneNumber());
      vCard.setField("signature", vCardRealm.getSignature());
      if (vCardRealm.getAvatar() != null && vCardRealm.getAvatar() != "") {
        vCard.setField("avatar", vCardRealm.getAvatar());
      }
      vCard.setEmailWork(vCardRealm.getEmail());
      if (vCardRealm.getNickName() != null && vCardRealm.getNickName() != "") {
        vCard.setNickName(vCardRealm.getNickName());
      } else {
        vCard.setNickName(StringSplitUtil.splitPrefix(vCardRealm.getJid()));
      }
      Observable.create((Observable.OnSubscribe<String>) subscriber -> {
        try {
          mVCardManager.saveVCard(vCard);
          subscriber.onNext(null);
        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
          e.printStackTrace();
          subscriber.onError(e);
          if (e instanceof SmackException.NotConnectedException) connect(true);
        }
      }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
        //保存VCard成功,发送给通知,保存到数据库
        HermesEventBus.getDefault()
            .post(new VCardRealm(StringSplitUtil.splitDivider(vCardRealm.getJid()),
                (vCardRealm.getNickName() != null && vCardRealm.getNickName() != "")
                    ? vCardRealm.getNickName() : vCard.getNickName(),
                (vCardRealm.getAvatar() != null && vCardRealm.getAvatar() != "")
                    ? vCardRealm.getAvatar() : vCard.getField("avatar"),
                PinYinUtils.getPinyinFirstLetter(
                    (vCardRealm.getNickName() != null && vCardRealm.getNickName() != "")
                        ? vCardRealm.getNickName() : vCard.getNickName()), PinYinUtils.ccs2Pinyin(
                (vCardRealm.getNickName() != null && vCardRealm.getNickName() != "")
                    ? vCardRealm.getNickName() : vCard.getNickName()), true));
        HermesEventBus.getDefault().post(new OwnVCardRealm("更新成功", null));
      }, new ErrorAction() {
        @Override public void call(Throwable throwable) {
          super.call(throwable);
          //保存VCard失败,发送给通知
          HermesEventBus.getDefault().post(new OwnVCardRealm(null, "更新失败1" + throwable));
        }
      });
    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
        //获取VCard失败，发送给通知
        HermesEventBus.getDefault().post(new OwnVCardRealm(null, "更新失败2" + throwable));
      }
    });
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void disconnect(UnRegisterEvent event) {
    mXMPPConnection.disconnect();
    connect(false);
  }

  /**
   * 添加群组
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void createMuc(String mucJid, String nickname,
      Collection<String> owner) {
    MultiUserChat multiUserChat = mMultiUserChatManager.getMultiUserChat(mucJid);
    try {
      //表示当前用户在房间中的昵称
      multiUserChat.create(nickname);
      Form form = multiUserChat.getConfigurationForm();
      Form submitForm = form.createAnswerForm();
      //房间的名称
      submitForm.setAnswer("muc#roomconfig_roomname", StringSplitUtil.splitPrefix(mucJid));
      //保证只有注册的昵称才能进入房间
      submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
      //设置为永久房间
      submitForm.setAnswer("muc#roomconfig_persistentroom", true);
      //设置房间人数上限，注意，参数不是int！！！！！
      submitForm.setAnswer("muc#roomconfig_maxusers", new ArrayList<>(100));
      ArrayList owners = new ArrayList();
      owners.addAll(owner);
      submitForm.setAnswer("muc#roomconfig_roomowners", owners);
      multiUserChat.sendConfigurationForm(submitForm);
      try {
        pullJoinedRooms(
            new PullJoinedRooms(appPreferences.getString("username" + "@" + SupportIM.mDomain)));
      } catch (ItemNotFoundException e) {
        e.printStackTrace();
      }
    } catch (XMPPException.XMPPErrorException | SmackException e) {
      e.printStackTrace();
    }
  }

  // 邀请群成员
  @Subscribe(threadMode = ThreadMode.MAIN) public void inviteMucMember(String mucJid,
      String userJid, String reason) {
    MultiUserChat multiUserChat = mMultiUserChatManager.getMultiUserChat(mucJid);
    Message message = new Message();
    try {
      multiUserChat.invite(message, userJid, reason);
    } catch (SmackException.NotConnectedException e) {
      e.printStackTrace();
    }
  }

  // 更新群信息
  @Subscribe(threadMode = ThreadMode.MAIN) public void updateMucForm(String mucJid) {
    MultiUserChat multiUserChat = mMultiUserChatManager.getMultiUserChat(mucJid);
    try {
      multiUserChat.sendConfigurationForm(new Form(DataForm.Type.form));
    } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取用户所加入的群组
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void pullJoinedRooms(PullJoinedRooms user) {
    List<String> rooms = new ArrayList<>();
    String username = null;
    try {
      username = appPreferences.getString("username");
    } catch (ItemNotFoundException e) {
      e.printStackTrace();
    }
    try {
      rooms = mMultiUserChatManager.getJoinedRooms(user.user);
    } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
      e.printStackTrace();
    }
    ArrayList<String> roomData = new ArrayList<>();
    roomData.addAll(rooms);
    ArrayList<RoomInfo> roomInfo = new ArrayList<>();
    for (int i = 0; i < rooms.size(); i++) {
      try {
        roomInfo.add(mMultiUserChatManager.getRoomInfo(roomData.get(i)));
      } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
        e.printStackTrace();
      }
    }
    if (user.user != null && user.user.equals(username + "@" + SupportIM.mDomain)) {
      //是当前用户，发送通知，缓存到本地数据库

    } else {
      // 不是当前用户,暂时不做此功能

    }
  }

  @Override public void invitationReceived(XMPPConnection conn, MultiUserChat room, String inviter,
      String reason, String password, Message message) {
    // 这里是收到群邀请请求
    try {
      room.join(inviter);
    } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
      e.printStackTrace();
    }
  }

  @Override public void invitationDeclined(String invitee, String reason) {
    //发出的邀请被拒绝

  }

  class SupportServiceConnection implements ServiceConnection {

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

  class SupportBinder extends SupportAIDLConnection.Stub {

    @Override public String getServiceName() throws RemoteException {
      return "SupportService连接";
    }
  }
}
