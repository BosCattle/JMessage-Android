package tech.jiangtao.support.kit.manager;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.util.Collections;
import java.util.List;
import net.grandcentrix.tray.AppPreferences;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.api.ApiService;
import tech.jiangtao.support.kit.api.service.UserServiceApi;
import tech.jiangtao.support.kit.archive.type.MessageAuthor;
import tech.jiangtao.support.kit.archive.type.MessageExtensionType;
import tech.jiangtao.support.kit.callback.IMListenerCollection;
import tech.jiangtao.support.kit.eventbus.IMAddContactRequestModel;
import tech.jiangtao.support.kit.eventbus.IMAddContactResponseModel;
import tech.jiangtao.support.kit.eventbus.IMContactDealModel;
import tech.jiangtao.support.kit.eventbus.IMContactDealResponseModel;
import tech.jiangtao.support.kit.eventbus.IMContactRequestModel;
import tech.jiangtao.support.kit.eventbus.IMContactRequestNotificationModel;
import tech.jiangtao.support.kit.eventbus.IMContactResponseCollection;
import tech.jiangtao.support.kit.eventbus.IMDeleteContactRequestModel;
import tech.jiangtao.support.kit.eventbus.IMDeleteContactResponseModel;
import tech.jiangtao.support.kit.eventbus.IMLoginResponseModel;
import tech.jiangtao.support.kit.eventbus.IMMessageResponseModel;
import tech.jiangtao.support.kit.model.Account;
import tech.jiangtao.support.kit.model.Result;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.realm.SessionRealm;
import tech.jiangtao.support.kit.util.ContactComparator;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: IMContactManager </br>
 * Description: 通讯录管理器 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 02:50</br>
 * Update: 27/05/2017 02:50 </br>
 **/
public class IMContactManager {

  public static final String TAG = IMContactManager.class.getCanonicalName();

  private Realm mRealm;
  private UserServiceApi mUserServiceApi;
  private IMListenerCollection.IMRealmChangeListener<ContactRealm> mRealmIMRealmChangeListener;
  private IMListenerCollection.IMDeleteContactListener<ContactRealm> mIMDeleteContactListener;
  private IMListenerCollection.IMAddContactListener mIMAddContactListener;
  private IMListenerCollection.IMFriendNotificationListener mFriendNotificationListener;
  private IMListenerCollection.IMDealFriendInvitedListener mDealFriendInvitedListener;

  private IMContactManager() {
    if (!HermesEventBus.getDefault().isRegistered(this)) {
      HermesEventBus.getDefault().register(this);
    }
  }

  public static IMContactManager geInstance() {
    return IMContactManagerHolder.sIMContactManager;
  }

  private static class IMContactManagerHolder {
    private static final IMContactManager sIMContactManager = new IMContactManager();
  }

  /**
   * 获取通讯录信息
   */
  public void readContacts(
      IMListenerCollection.IMRealmChangeListener<ContactRealm> realmIMRealmChangeListener) {
    connectRealm();
    RealmQuery<ContactRealm> realmQuery = mRealm.where(ContactRealm.class);
    // 查询,根据nickName进行排序
    RealmResults<ContactRealm> contactRealms = realmQuery.findAllSorted(SupportIM.PINYIN);
    if (realmIMRealmChangeListener != null) {
      realmIMRealmChangeListener.change(contactRealms);
    }
  }

  public void readSingleContact(String userId,
      IMListenerCollection.IMRealmQueryListener<ContactRealm> listener) {
    connectRealm();
    LogUtils.d(TAG, userId);
    ContactRealm contactRealm =
        mRealm.where(ContactRealm.class).equalTo(SupportIM.USER_ID, userId).findFirst();
    if (contactRealm != null) {
      listener.result(contactRealm);
    } else {
      // 通过http获取用户信息
      LogUtils.d(TAG, "通讯录中没有该用户信息，通过网络获取");
      mUserServiceApi = ApiService.getInstance().createApiService(UserServiceApi.class);
      mUserServiceApi.selfAccount(userId)
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
            updateSingleIMContactRealm(realm, null);
            listener.result(realm);
          }, new ErrorAction() {
            @Override public void call(Throwable throwable) {
              super.call(throwable);
              LogUtils.d(TAG, "在通讯录管理器中获取用户信息失败" + throwable.getMessage());
            }
          });
    }
  }

  /**
   * 获取通讯录
   * @param model
   * @param listener
   */
  public void readSingleContact(IMMessageResponseModel model,
      IMListenerCollection.IMRealmQueryListener<ContactRealm> listener) {
    connectRealm();
    String userId = null;
    if (model.author.equals(MessageAuthor.OWN)) {
      userId = StringSplitUtil.splitDivider(model.getMessage().getMsgReceived());
    } else if (model.getAuthor().equals(MessageAuthor.FRIEND)) {
      userId = StringSplitUtil.splitDivider(model.getMessage().getMsgSender());
    }
    if (model.getMessage().getChatType().equals(MessageExtensionType.GROUP_CHAT.toString())) {
      userId = StringSplitUtil.splitDivider(model.getMessage().getMsgSender());
    }
    LogUtils.d(TAG, userId);
    ContactRealm contactRealm =
        mRealm.where(ContactRealm.class).equalTo(SupportIM.USER_ID, userId).findFirst();
    if (contactRealm != null) {
      listener.result(contactRealm);
    } else {
      // 通过http获取用户信息
      LogUtils.d(TAG, "通讯录中没有该用户信息，通过网络获取");
      mUserServiceApi = ApiService.getInstance().createApiService(UserServiceApi.class);
      mUserServiceApi.selfAccount(userId)
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
            updateSingleIMContactRealm(realm, null);
            listener.result(realm);
          }, new ErrorAction() {
            @Override public void call(Throwable throwable) {
              super.call(throwable);
              LogUtils.d(TAG, "在通讯录管理器中获取用户信息失败" + throwable.getMessage());
            }
          });
    }
  }

  /**
   * 通过网络获取通讯录信息
   */
  public void readContactsFromHttp(Context context,
      IMListenerCollection.IMRealmChangeListener<ContactRealm> realmIMRealmChangeListener) {
    mUserServiceApi = ApiService.getInstance().createApiService(UserServiceApi.class);
    IMAccountManager accountManager = new IMAccountManager(context);
    Account account = accountManager.account();
    if (account != null && account.userId != null) {
      mUserServiceApi.queryUserFriends(account.userId)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(contactRealms -> {
            Collections.sort(contactRealms, new ContactComparator());
            // -->将数据放到数据库
            writeToRealm(contactRealms);
            realmIMRealmChangeListener.change(contactRealms);
          }, new ErrorAction() {
            @Override public void call(Throwable throwable) {
              super.call(throwable);
              Log.d(TAG, "call: " + throwable.getMessage());
              // 从数据库中取数据，放到界面上
              readContacts(realmIMRealmChangeListener);
            }
          });
    }
  }

  /**
   * 通过XMPP获取通讯录信息
   */
  public void readContactsFromXMPP(
      IMListenerCollection.IMRealmChangeListener<ContactRealm> realmIMRealmChangeListener) {
    if (!HermesEventBus.getDefault().isRegistered(this)) {
      HermesEventBus.getDefault().register(this);
    }
    HermesEventBus.getDefault().postSticky(new IMContactRequestModel());
    mRealmIMRealmChangeListener = realmIMRealmChangeListener;
  }

  /**
   * 通过xmpp获取通讯录的回调
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void receiveContactFromProcess(
      IMContactResponseCollection contactResponseCollection) {
    if (contactResponseCollection.getModels() != null) {
      updateSingleIMContactRealm(contactResponseCollection.getModels(),
          mRealmIMRealmChangeListener);
    }
  }

  /**
   * 写数据到数据库中
   */
  public void writeToRealm(List<ContactRealm> contactRealms) {
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
    LogUtils.d(TAG, "写数据");
    mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(contactRealms));
  }

  /**
   * 更新一条数据
   */
  public void updateSingleIMContactRealm(ContactRealm contactRealm,
      IMListenerCollection.IMRealmChangeListener<ContactRealm> realmIMRealmChangeListener) {
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
    mRealm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(contactRealm), () -> {
      if (mRealmIMRealmChangeListener != null) {
        readContacts(realmIMRealmChangeListener);
      }
    });
  }

  /**
   * 删除一条数据
   */
  public void deleteSingleIMContactRealm(ContactRealm contactRealm,
      IMListenerCollection.IMRealmChangeListener<ContactRealm> realmIMRealmChangeListener,
      IMListenerCollection.IMDeleteContactListener<ContactRealm> deleteContactListener) {
    mRealmIMRealmChangeListener = realmIMRealmChangeListener;
    mIMDeleteContactListener = deleteContactListener;
    LogUtils.d(TAG, contactRealm.getUserId());
    HermesEventBus.getDefault().post(new IMDeleteContactRequestModel(contactRealm.getUserId()));
  }

  /**
   * XMPP删除Contact成功的回调，删除成功后将本地数据库一起删除
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void deleteContactFromXMPP(
      IMDeleteContactResponseModel deleteContactResponseModel) {
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
    LogUtils.d(TAG, deleteContactResponseModel.userId + "   " + deleteContactResponseModel.result);
    mIMDeleteContactListener.deleteContactSuccess(deleteContactResponseModel);
    if (deleteContactResponseModel.userId != null) {
      RealmResults<ContactRealm> realms = mRealm.where(ContactRealm.class)
          .equalTo("userId", deleteContactResponseModel.userId)
          .findAll();
      RealmResults<SessionRealm> messageResult = mRealm.where(SessionRealm.class)
          .equalTo(SupportIM.SENDERFRIENDID, deleteContactResponseModel.userId)
          .findAll();
      mRealm.executeTransaction(realm -> {
        realms.deleteAllFromRealm();
        if (messageResult.size() != 0) {
          messageResult.deleteAllFromRealm();
        }
        readContacts(mRealmIMRealmChangeListener);
      });
    } else {
      mIMDeleteContactListener.deleteContactFailed(deleteContactResponseModel);
    }
  }

  /**
   * 删除表
   */
  public void deleteAllIMContactRealm(
      IMListenerCollection.IMRealmChangeListener<ContactRealm> realmIMRealmChangeListener) {
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
    mRealm.delete(ContactRealm.class);
    readContacts(realmIMRealmChangeListener);
  }

  /**
   * 销毁mRealm
   */
  public void destory() {
    mRealm.close();
    mRealm = null;
    if (HermesEventBus.getDefault().isRegistered(this)) {
      HermesEventBus.getDefault().unregister(this);
    }
  }

  /**
   * 请求添加好友
   */
  public void requestMakeFriend(IMAddContactRequestModel model,
      IMListenerCollection.IMAddContactListener listener) {
    connectHermes();
    HermesEventBus.getDefault().postSticky(model);
    mIMAddContactListener = listener;
  }

  /**
   * 用户自己发送添加好友请求的回调
   * 1. 发送成功
   * 2. 发送失败
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void requestMakeFriendCallBack(
      IMAddContactResponseModel model) {
    if (model.result.getCode() == 200) {
      mIMAddContactListener.addContactSuccess(model);
    } else {
      mIMAddContactListener.addContactFailed(model);
    }
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

  public void setmFriendNotificationListener(
      IMListenerCollection.IMFriendNotificationListener mFriendNotificationListener) {
    this.mFriendNotificationListener = mFriendNotificationListener;
  }

  /**
   * 添加好友通知
   */
  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN) @Subscribe(threadMode = ThreadMode.MAIN)
  public void addFriendsNotification(IMContactRequestNotificationModel model) {
    if (mFriendNotificationListener == null) {
      return;
    }
    switch (model.getType()) {
      case SUBSCRIBE:
        mFriendNotificationListener.receivedUserInvited(model.getContactRealm());
        break;
      case SUBSCRIBED:
        mFriendNotificationListener.receivedAgreeInvited(model.getContactRealm());
        break;
      case UNSUBSCRIBE:
        mFriendNotificationListener.receivedRejectInvited(model.getContactRealm());
        break;
    }
  }

  /**
   * 处理好友请求
   * 1. 同意加微好友
   * 2. 拒绝加为好友
   */
  public void requestDealInvited(IMContactDealModel model,
      IMListenerCollection.IMDealFriendInvitedListener imDealFriendInvitedListener) {
    connectHermes();
    HermesEventBus.getDefault().postSticky(model);
    mDealFriendInvitedListener = imDealFriendInvitedListener;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void dealInvited(IMContactDealResponseModel model) {
    if (model.value) {
      mDealFriendInvitedListener.success();
      mDealFriendInvitedListener.failed();
    }
  }

  public void saveAdminContact(Context context) {
    AppPreferences mAppPreferences = new AppPreferences(context);
    String userId = mAppPreferences.getString(SupportIM.USER_ID, null);
    mUserServiceApi = ApiService.getInstance().createApiService(UserServiceApi.class);
    mUserServiceApi.selfAccount(userId)
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
          updateSingleIMContactRealm(realm, null);
        });
  }
}
