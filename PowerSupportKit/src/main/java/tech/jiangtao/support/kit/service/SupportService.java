package tech.jiangtao.support.kit.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.cocosw.favor.FavorAdapter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.greenrobot.eventbus.EventBus;
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
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferNegotiator;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.archive.MessageArchiveIQProvider;
import tech.jiangtao.support.kit.archive.MessageArchiveRequestIQ;
import tech.jiangtao.support.kit.archive.MessageArchiveStanzaFilter;
import tech.jiangtao.support.kit.archive.MessageArchiveStanzaListener;
import tech.jiangtao.support.kit.archive.MessageBody;
import tech.jiangtao.support.kit.archive.type.FileType;
import tech.jiangtao.support.kit.archive.type.MessageBodyType;
import tech.jiangtao.support.kit.callback.ConnectionCallback;
import tech.jiangtao.support.kit.eventbus.MessageTest;
import tech.jiangtao.support.kit.eventbus.NormalFileMessage;
import tech.jiangtao.support.kit.eventbus.RecieveMessage;
import tech.jiangtao.support.kit.eventbus.TextMessage;
import tech.jiangtao.support.kit.init.SupportIM;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.realm.sharepreference.Account;
import tech.jiangtao.support.kit.userdata.SimpleArchiveMessage;
import tech.jiangtao.support.kit.util.DateUtils;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.FileUtil;

public class SupportService extends Service
        implements ChatManagerListener, ConnectionListener, FileTransferListener {

    private static final String TAG = SupportService.class.getSimpleName();
    private static XMPPTCPConnection mXMPPConnection;
    public static boolean mNeedAutoLogin = true;
    private static FileTransferManager mFileTransferManager;
    private Realm mRealm;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (mRealm == null) {
            mRealm = Realm.getDefaultInstance();
        }
        connect();
        return START_STICKY;
    }


    private void setMessageRealm(MessageBody body, String time) {
        //首先，判断数据库中是否有改内容
        MessageRealm messageRealm;
        //date有问题,老子也是醉了
        java.util.Date date =
                DateUtils.getSumUTCTimeZone(DateUtils.UTCConvertToLong(time), Long.valueOf(body.getSecs()));
        RealmResults<MessageRealm> realms = mRealm.where(MessageRealm.class).equalTo("thread", body.getThread()).findAll();
        if (realms.size() == 0) {
            messageRealm = new MessageRealm();
            createMessageRealm(messageRealm, body, date);
            mRealm.beginTransaction();
            mRealm.copyToRealm(messageRealm);
            mRealm.commitTransaction();
        } else {
            Log.d(TAG, "setMessageRealm: 本地数据库已经有这条消息了。");
        }
    }

    private void createMessageRealm(MessageRealm messageRealm, MessageBody body,
                                    java.util.Date time) {
        messageRealm.setThread(body.getThread());
        messageRealm.setTextMessage(body.getBody());
        messageRealm.setTime(time);
        String other = body.getWith();
        String main = SupportService.getmXMPPConnection().getUser();
        Log.d(TAG, "createMessageRealm: " + other + "       " + main);
        if (body.getType() == MessageBodyType.TYPE_FROM) {
            messageRealm.setMainJID(other);
            messageRealm.setWithJID(main);
        } else {
            messageRealm.setMainJID(main);
            messageRealm.setWithJID(other);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MessageTest connection) {
        // 根据消息类型，作出调转服务，拿到了connection，对监听所有够功能。
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    public static XMPPConnection getmXMPPConnection() {
        if (mXMPPConnection != null) {
            return mXMPPConnection;
        } else {
            throw new NullPointerException("连接不能为空...");
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        chat.addMessageListener((chat1, message) -> {
            Log.d(TAG, "processMessage: " + message.getBody());
            Log.d(TAG, "processMessage: " + chat1.getParticipant());
            EventBus.getDefault()
                    .post(new RecieveMessage(message.getBody(), message.getType(), chat1.getParticipant()));
            //缓存消息
//      setMessageRealm(buildMessageBody(),null);
        });
    }

    public MessageBody buildMessageBody(Message message) {
//    MessageBody body = new MessageBody(MessageBodyType.TYPE_FROM);
        if (message.getBody() != null && !message.getBody().equals("")) {

        }
        return null;
    }

    @Subscribe
    public void sendMessage(TextMessage message) {
        Chat chat = ChatManager.getInstanceFor(mXMPPConnection).createChat(message.userJID);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    chat.sendMessage(message.message);
                    subscriber.onNext("");
                } catch (SmackException.NotConnectedException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
            Log.d(TAG, "sendMessage: 发送成功");
        }, new ErrorAction() {
            @Override
            public void call(Throwable throwable) {
                super.call(throwable);
            }
        });
    }

    public static void sendMessageArchive(String jid, String time) {
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

    public static void requestAllMessageArchive(String time) {
        MessageArchiveRequestIQ iq = new MessageArchiveRequestIQ(null);
        if (time == null || time.equals("")) {
            iq.setTime("1970-01-01T00:00:00Z");
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
            @Override
            public void call(Subscriber<? super AbstractXMPPConnection> subscriber) {
                try {
                    subscriber.onNext(mXMPPConnection.connect());
                } catch (SmackException | IOException | XMPPException e) {
                    subscriber.onError(new Throwable(e.toString()));
                    e.printStackTrace();
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
                        login(account.getUserName(), account.getPassword(), null);
                    }
                }, new ErrorAction() {
                    @Override
                    public void call(Throwable throwable) {
                        super.call(throwable);
                        // TODO: 2016/12/12 流氓了,后期再改吧
                        connect();
                    }
                });
    }

    public static void login(String username, String password, ConnectionCallback callback) {
        Observable.create(subscriber2 -> {
            try {
                mXMPPConnection.login(username, password);
                subscriber2.onCompleted();
            } catch (XMPPException | SmackException | IOException e) {
                subscriber2.onError(e);
                e.printStackTrace();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {

        }, new ErrorAction() {
            @Override
            public void call(Throwable throwable) {
                super.call(throwable);
                // TODO: 10/12/2016 登录失败
                if (callback != null) {
                    callback.connectionFailed(new Exception(throwable));
                }
            }
        }, () -> {
            // TODO: 10/12/2016 登录成功
            if (callback != null) {
                callback.connection(mXMPPConnection);
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

    @Override
    public void connected(XMPPConnection connection) {
        mXMPPConnection = (XMPPTCPConnection) connection;
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        mXMPPConnection = (XMPPTCPConnection) connection;
        connectSuccessPerform();
    }

    @Override
    public void connectionClosed() {
        connect();
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        connect();
    }

    @Override
    public void reconnectionSuccessful() {
        connectSuccessPerform();
    }

    @Override
    public void reconnectingIn(int seconds) {

    }

    @Override
    public void reconnectionFailed(Exception e) {
        connect();
    }

    public void connectSuccessPerform() {
        ProviderManager.addIQProvider("chat", "urn:xmpp:archive", new MessageArchiveIQProvider());
        mXMPPConnection.addAsyncStanzaListener(new MessageArchiveStanzaListener(),
                new MessageArchiveStanzaFilter());
        // TODO: 07/12/2016 读取数据库，得到最后的更新时间
        SimpleArchiveMessage message = new SimpleArchiveMessage();
        requestAllMessageArchive(message.getLastUpdateTime());
        ChatManager manager = ChatManager.getInstanceFor(mXMPPConnection);
        manager.addChatListener(this);
        mFileTransferManager = FileTransferManager.getInstanceFor(mXMPPConnection);
        mFileTransferManager.addFileTransferListener(this);
    }

    @Override
    public void fileTransferRequest(FileTransferRequest request) {
        // Accept it
        Observable.create(subscriber -> {
            FileTransferNegotiator fileTransferNegotiator = FileTransferNegotiator.getInstanceFor(mXMPPConnection);
            FileTransferNegotiator.IBB_ONLY = true;
            IncomingFileTransfer transfer = request.accept();
            try {
                transfer.recieveFile(new File(FileUtil.createNormalFileDic() + "/" + request.getFileName()));
                while(!transfer.isDone()) {
                    if(transfer.getStatus().equals(FileTransfer.Status.error)) {
                        System.out.println("ERROR!!! " + transfer.getError());
                    } else {
                        System.out.println(transfer.getStatus());
                        System.out.println(transfer.getProgress());
                    }
                }
                if (transfer.isDone()) {
                    subscriber.onCompleted();
                }
            } catch (SmackException | IOException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {

        }, new ErrorAction() {
            @Override
            public void call(Throwable throwable) {
                super.call(throwable);
                Log.d(TAG, "发送文件发生错误" + throwable);
            }
        }, () -> {
            if (request.getFileName().endsWith("mp4")) {
                EventBus.getDefault()
                    .post(new NormalFileMessage(FileType.TYPE_AUDIO, request.getFileName(),
                        FileUtil.createAudioDic() + "/" + request.getFileName(), request.getRequestor()));
            }else {
                EventBus.getDefault().post(new NormalFileMessage
                    (FileType.TYPE_IMAGE, request.getFileName(),
                        FileUtil.createNormalFileDic() + "/" + request.getFileName(), request.getRequestor()));
            }
        });
    }
}
