package tech.jiangtao.support.kit.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import com.cocosw.favor.FavorAdapter;
import net.grandcentrix.tray.AppPreferences;
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
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

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
import tech.jiangtao.support.kit.eventbus.ContactEvent;
import tech.jiangtao.support.kit.eventbus.LoginCallbackEvent;
import tech.jiangtao.support.kit.eventbus.LoginParam;
import tech.jiangtao.support.kit.eventbus.NotificationConnection;
import tech.jiangtao.support.kit.eventbus.OwnVCardRealm;
import tech.jiangtao.support.kit.eventbus.RecieveMessage;
import tech.jiangtao.support.kit.eventbus.TextMessage;
import tech.jiangtao.support.kit.init.SupportIM;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.kit.realm.sharepreference.Account;
import tech.jiangtao.support.kit.reciever.TickBroadcastReceiver;
import tech.jiangtao.support.kit.util.DateUtils;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.PinYinUtils;
import xiaofei.library.hermeseventbus.HermesEventBus;

import static xiaofei.library.hermes.Hermes.getContext;

public class SupportService extends Service
    implements ChatManagerListener, ConnectionListener, RosterListener {

  private static final String TAG = SupportService.class.getSimpleName();
  private XMPPTCPConnection mXMPPConnection;
  public static boolean mNeedAutoLogin = true;
  private AccountManager mAccountManager;
  private Roster mRoster;
  private VCardManager mVCardManager;
  private AppPreferences appPreferences = new AppPreferences(getContext());

  @Override public void onCreate() {
    super.onCreate();
    IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
    TickBroadcastReceiver receiver = new TickBroadcastReceiver();
    registerReceiver(receiver, filter);
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    if (!HermesEventBus.getDefault().isRegistered(this)) {
      HermesEventBus.getDefault().register(this);
    }
    connect();
    return START_STICKY;
  }

  @Subscribe(threadMode = ThreadMode.BACKGROUND)
  public void onMessage(NotificationConnection connection) {
    if (connection.connectChoice && !mXMPPConnection.isConnected()) {
      //收到广播，开始连接
      Log.d(TAG, "onMessage: 开始连接");
      connect();
    } else {
      mXMPPConnection.disconnect();
    }
  }

  @Override public IBinder onBind(Intent intent) {
    throw null;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    HermesEventBus.getDefault().unregister(this);
  }

  @Override public void chatCreated(Chat chat, boolean createdLocally) {
    chat.addMessageListener((chat1, message) -> {
      DefaultExtensionElement messageExtension =
          (DefaultExtensionElement) message.getExtension("message:extension");
      if (message.getBody() != null) {
        if (messageExtension==null||messageExtension.getValue("type") == null || messageExtension.getValue("type")
            .equals(MessageExtensionType.TEXT.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(message.getStanzaId(),message.getType(), message.getFrom(),message.getTo(),
                  chat1.getThreadID(),message.getBody(),MessageExtensionType.TEXT,false,
                  MessageAuthor.FRIEND));
        }
        if (messageExtension.getValue("type").equals(MessageExtensionType.IMAGE.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(message.getStanzaId(),message.getType(), message.getFrom(),message.getTo(),
                  chat1.getThreadID(),message.getBody(),MessageExtensionType.IMAGE,false,MessageAuthor.FRIEND));
        }
        if (messageExtension.getValue("type").equals(MessageExtensionType.AUDIO.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(message.getStanzaId(),message.getType(), message.getFrom(),message.getTo(),
                  chat1.getThreadID(),message.getBody(),MessageExtensionType.AUDIO,false,MessageAuthor.FRIEND));
        }
        if (messageExtension.getValue("type").equals(MessageExtensionType.VIDEO.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(message.getStanzaId(),message.getType(), message.getFrom(),message.getTo(),
                  chat1.getThreadID(),message.getBody(),MessageExtensionType.VIDEO,false,MessageAuthor.FRIEND));
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
          subscriber.onError(e);
          e.printStackTrace();
        }
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
      Log.d(TAG, "sendMessage: 发送成功");
      //缓存消息
      DefaultExtensionElement messageExtension =
          (DefaultExtensionElement) s.getExtension("message:extension");
      Log.d(TAG, "sendMessage: 打印出别人的jid为:"+s.getTo());
      Log.d(TAG, "sendMessage: 打印出自己的jid为:"+chat.getParticipant());
      if ( s.getBody()!= null) {
        if (messageExtension==null||messageExtension.getValue("type") == null || messageExtension.getValue("type")
            .equals(MessageExtensionType.TEXT.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(s.getStanzaId(),s.getType(), chat.getParticipant(),s.getTo(),
                  chat.getThreadID(),s.getBody(),MessageExtensionType.TEXT,false,
                  MessageAuthor.OWN));
        }
        if (messageExtension.getValue("type").equals(MessageExtensionType.IMAGE.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(s.getStanzaId(),s.getType(), chat.getParticipant(),s.getTo(),
                  chat.getThreadID(),s.getBody(),MessageExtensionType.IMAGE,false,MessageAuthor.OWN));
        }
        if (messageExtension.getValue("type").equals(MessageExtensionType.AUDIO.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(s.getStanzaId(),s.getType(), chat.getParticipant(),s.getTo(),
                  chat.getThreadID(),s.getBody(),MessageExtensionType.AUDIO,false,MessageAuthor.OWN));
        }
        if (messageExtension.getValue("type").equals(MessageExtensionType.VIDEO.toString())) {
          HermesEventBus.getDefault()
              .post(new RecieveMessage(s.getStanzaId(),s.getType(), chat.getParticipant(),s.getTo(),
                  chat.getThreadID(),s.getBody(),MessageExtensionType.VIDEO,false,MessageAuthor.OWN));
        }
      }
    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
      }
    });
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void loginEvent(LoginParam param) {
    Log.d(TAG, "loginEvent: 进入登录");
    login(param.username, param.password);
  }

  public void sendMessageArchive(String jid, String time) {
    MessageArchiveRequestIQ iq = new MessageArchiveRequestIQ(null);
    iq.setJid(jid);
    iq.setTime(time);
    iq.setType(IQ.Type.get);
    Log.e(TAG, "sendMessageArchive: " + iq.toXML());
    try {
      mXMPPConnection.sendStanza(iq);
    } catch (SmackException.NotConnectedException e) {
      e.printStackTrace();
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
    Log.e(TAG, "sendMessageArchive: " + iq.toXML());
    try {
      mXMPPConnection.sendStanza(iq);
    } catch (SmackException.NotConnectedException e) {
      e.printStackTrace();
    }
  }

  public void connect() {
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
          // TODO: 10/12/2016  读取本地数据库，判断有无账户，有，则登录，无---
          mXMPPConnection = (XMPPTCPConnection) abstractXMPPConnection;
          Account account = new FavorAdapter.Builder(this).build().create(Account.class);
          if (account != null
              && account.getUserName() != null
              && account.getPassword() != null
              && mNeedAutoLogin) {
            login(account.getUserName(), account.getPassword());
          }
        }, new ErrorAction() {
          @Override public void call(Throwable throwable) {
            super.call(throwable);
          }
        });
  }

  public void login(String username, String password) {
    Observable.create(subscriber2 -> {
      try {
        mXMPPConnection.login(username, password);
        subscriber2.onNext(null);
      } catch (XMPPException | SmackException | IOException e) {
        subscriber2.onError(e);
        e.printStackTrace();
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {
      Log.d(TAG, "login: 登录成功");
      // 保存用户jid
      appPreferences.put("userJid", mXMPPConnection.getUser());
      appPreferences.put("username", username);
      appPreferences.put("password", password);
      HermesEventBus.getDefault().postSticky(new LoginCallbackEvent("登录成功", null));
    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
        Log.d(TAG, "call: 登录失败");
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
    mXMPPConnection.setPacketReplyTimeout(10000);
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
    Log.d(TAG, "connectionClosed: 连接被关闭");
  }

  @Override public void connectionClosedOnError(Exception e) {
    Log.d(TAG, "connectionClosedOnError: 连接因为错误被关闭" + e.getMessage());
  }

  @Override public void reconnectionSuccessful() {
    Log.d(TAG, "reconnectionSuccessful: 重连成功");
    connectSuccessPerform();
  }

  @Override public void reconnectingIn(int seconds) {
    Log.d(TAG, "reconnectingIn: 正在重连" + seconds);
  }

  @Override public void reconnectionFailed(Exception e) {
    Log.d(TAG, "reconnectionFailed: 重连失败，失败原因" + e.getMessage());
  }

  public void connectSuccessPerform() {
    ProviderManager.addIQProvider("chat", "urn:xmpp:archive", new MessageArchiveIQProvider());
    mXMPPConnection.addAsyncStanzaListener(new MessageArchiveStanzaListener(),
        new MessageArchiveStanzaFilter());
    long time = appPreferences.getLong("last_modify",0);
    //将time转化为字符串
    requestAllMessageArchive(DateUtils.getDefaultUTCTimeZone(time));
    // TODO: 07/12/2016 读取数据库，得到最后的更新时间
    ChatManager manager = ChatManager.getInstanceFor(mXMPPConnection);
    manager.addChatListener(this);
    addFriend();
  }

  public void addFriend() {
    Roster roster = Roster.getInstanceFor(mXMPPConnection);
    roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
    roster.addRosterListener(this);
    mXMPPConnection.addSyncStanzaListener(packet -> {
      Presence presence = (Presence) packet;
      if (presence.getType().equals(Presence.Type.subscribe)) {
        // 收到好友请求,现在的代码是自动自动接受好友请求
        Log.d(TAG, "addFriend: 接受到好友请求");
        Presence newp = new Presence(Presence.Type.subscribed);
        newp.setMode(Presence.Mode.available);
        newp.setPriority(24);
        newp.setTo(presence.getFrom());
        mXMPPConnection.sendStanza(newp);
        Presence subscription = new Presence(Presence.Type.subscribe);
        subscription.setTo(presence.getFrom());
        mXMPPConnection.sendStanza(subscription);
      } else if (presence.getType().equals(Presence.Type.unsubscribe)) {
        // 不同意添加好友
        Log.d(TAG, "addFriend: 对方不同意好友请求");
        Presence newp = new Presence(Presence.Type.unsubscribed);
        newp.setMode(Presence.Mode.available);
        newp.setPriority(24);
        newp.setTo(presence.getFrom());
        mXMPPConnection.sendStanza(newp);
      } else if (presence.getType().equals(Presence.Type.subscribed)) {
        Log.d(TAG, "addFriend: 对方同意添加好友。");
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

  //
  @Override public void entriesAdded(Collection<String> addresses) {
    Log.d(TAG, "entriesAdded: 好友添加成功");
  }

  @Override public void entriesUpdated(Collection<String> addresses) {
    Log.d(TAG, "entriesUpdated: 好友更新成功");
  }

  @Override public void entriesDeleted(Collection<String> addresses) {
    Log.d(TAG, "entriesDeleted: 好友删除成功");
  }

  @Override public void presenceChanged(Presence presence) {
    Log.d(TAG, "presenceChanged: 我也不知道这是干嘛的。");
  }

  // 注册账户
  public void createAccount(String username, String password) {
    mAccountManager = AccountManager.getInstance(mXMPPConnection);
    Observable.create(subscriber -> {
      try {
        mAccountManager.createAccount(username, password);
        subscriber.onNext(null);
      } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
        subscriber.onError(e);
        e.printStackTrace();
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {

    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
      }
    });
  }

  // 通讯录事件
  @Subscribe(threadMode = ThreadMode.MAIN) public void getContact(ContactEvent event) {
    getRoster();
  }

  //获取网络通讯录,暂时没有保存到本地数据库
  public void getRoster() {
    mRoster = Roster.getInstanceFor(mXMPPConnection);
    mVCardManager = VCardManager.getInstanceFor(mXMPPConnection);
    Collection<RosterEntry> entries = mRoster.getEntries();
    Log.d(TAG, "getContact:获取到我的好友数量" + entries.size());
    Set<RosterEntry> set = new HashSet<>();
    set.addAll(entries);
    for (RosterEntry en : set) {
      Log.d(TAG, "updateContact: " + en.getUser());
      Observable.create((Observable.OnSubscribe<VCard>) subscriber -> {
        try {
          subscriber.onNext(mVCardManager.loadVCard(en.getUser()));
        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
          e.printStackTrace();
          subscriber.onError(e);
        }
      }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(vCard -> {
        //更新本地数据库
        Log.d(TAG, "getRoster: " + vCard.getFrom());
        VCardRealm vCardRealm = new VCardRealm();
        vCardRealm.setJid(vCard.getFrom());
        vCardRealm.setNickName(vCard.getNickName());
        vCardRealm.setSex(vCard.getField("sex"));
        vCardRealm.setSubject(vCard.getField("subject"));
        vCardRealm.setOffice(vCard.getField("office"));
        vCardRealm.setEmail(vCard.getEmailWork());
        vCardRealm.setPhoneNumber(vCard.getPhoneWork("voice"));
        vCardRealm.setSignature(vCard.getField("signature"));
        vCardRealm.setAvatar(vCard.getField("avatar"));
        if (vCard.getNickName() != null) {
          vCardRealm.setAllPinYin(PinYinUtils.ccs2Pinyin(vCard.getNickName()));
          vCardRealm.setFirstLetter(PinYinUtils.getPinyinFirstLetter(vCard.getNickName()));
        }
        vCardRealm.setFriend(true);
        HermesEventBus.getDefault().post(vCardRealm);
      }, new ErrorAction() {
        @Override public void call(Throwable throwable) {
          super.call(throwable);
          Log.d(TAG, "call: " + throwable.toString());
        }
      });
    }
  }

  //添加或者更新vCard;
  @Subscribe(threadMode = ThreadMode.MAIN) public void addOrUpdateVCard(
      VCardRealm vCardRealm) {
    Observable.create((Observable.OnSubscribe<VCard>) subscriber -> {
      try {
        subscriber.onNext(mVCardManager.loadVCard(vCardRealm.getJid()));
      } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
        e.printStackTrace();
        subscriber.onError(e);
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(vCard -> {
      vCard.setField("sex", vCardRealm.getSex());
      vCard.setField("subject", vCardRealm.getSubject());
      vCard.setField("office", vCardRealm.getOffice());
      vCard.setField("voice", vCardRealm.getPhoneNumber());
      vCard.setField("signature", vCardRealm.getSignature());
      vCard.setField("avatar", vCardRealm.getAvatar());
      vCard.setEmailWork(vCardRealm.getEmail());
      vCard.setNickName(vCardRealm.getNickName());
      Observable.create((Observable.OnSubscribe<String>) subscriber -> {
        try {
          mVCardManager.saveVCard(vCard);
          subscriber.onNext(null);
        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
          e.printStackTrace();
          subscriber.onError(e);
        }
      }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
        //保存VCard成功,发送给通知,保存到数据库
        HermesEventBus.getDefault().post(new OwnVCardRealm("更新成功", null));
      }, new ErrorAction() {
        @Override public void call(Throwable throwable) {
          super.call(throwable);
          //保存VCard失败,发送给通知
          HermesEventBus.getDefault().post(new OwnVCardRealm(null, "更新失败"));
        }
      });
    }, new ErrorAction() {
      @Override public void call(Throwable throwable) {
        super.call(throwable);
        //获取VCard失败，发送给通知
        HermesEventBus.getDefault().post(new OwnVCardRealm(null, "更新失败"));
      }
    });
  }
}
