package tech.jiangtao.support.kit.manager;

import android.content.Context;
import android.util.Log;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.api.ApiService;
import tech.jiangtao.support.kit.api.service.UserServiceApi;
import tech.jiangtao.support.kit.callback.IMListenerCollection;
import tech.jiangtao.support.kit.eventbus.IMContactRequestModel;
import tech.jiangtao.support.kit.eventbus.IMContactResponseCollection;
import tech.jiangtao.support.kit.eventbus.IMDeleteContactRequestModel;
import tech.jiangtao.support.kit.eventbus.IMDeleteContactResponseModel;
import tech.jiangtao.support.kit.model.Account;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.realm.SessionRealm;
import tech.jiangtao.support.kit.util.ContactComparator;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: IMContactManager </br>
 * Description: 通讯录管理器 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 02:50</br>
 * Update: 27/05/2017 02:50 </br>
 **/
// TODO: 27/05/2017 添加通讯录好友
// TODO: 27/05/2017 修改通讯录好友
// TODO: 27/05/2017 删除通讯录中的好友
// TODO: 27/05/2017 读通讯到UI
public class IMContactManager {

  public static final String TAG = IMContactManager.class.getCanonicalName();

  private Realm mRealm;
  private UserServiceApi mUserServiceApi;
  private IMListenerCollection.IMRealmChangeListener<ContactRealm> mRealmIMRealmChangeListener;
  private IMListenerCollection.IMDeleteContactListener<ContactRealm> mIMDeleteContactListener;

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
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
    mRealm.executeTransaction(realm -> {
      RealmQuery<ContactRealm> realmQuery = realm.where(ContactRealm.class);
      // 查询,根据nickName进行排序
      RealmResults<ContactRealm> contactRealms = realmQuery.findAllSorted(SupportIM.PINYIN);
      realmIMRealmChangeListener.change(contactRealms);
    });
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
            realmIMRealmChangeListener.change((RealmResults<ContactRealm>) contactRealms);
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
   *
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void receiveContactFromProcess(
      IMContactResponseCollection contactResponseCollection) {
    if (contactResponseCollection.getModels()!=null) {
      updateSingleIMContactRealm(contactResponseCollection.getModels(), mRealmIMRealmChangeListener);
    }
  }

  /**
   * 写数据到数据库中
   */
  public void writeToRealm(List<ContactRealm> contactRealms) {
    if (mRealm == null || mRealm.isClosed()) {
      mRealm = Realm.getDefaultInstance();
    }
    LogUtils.d(TAG,"写数据");
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
    mRealm.executeTransactionAsync(realm -> {
      realm.copyToRealmOrUpdate(contactRealm);
    }, () -> readContacts(realmIMRealmChangeListener));
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
      mRealm.executeTransactionAsync(realm -> {
        realms.deleteAllFromRealm();
        if (messageResult.size() != 0) {
          messageResult.deleteAllFromRealm();
        }
      }, () -> readContacts(mRealmIMRealmChangeListener));
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
}
