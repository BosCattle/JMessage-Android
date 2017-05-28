package tech.jiangtao.support.kit.manager;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.jiangtao.support.kit.api.ApiService;
import tech.jiangtao.support.kit.api.service.GroupServiceApi;
import tech.jiangtao.support.kit.callback.IMListenerCollection;
import tech.jiangtao.support.kit.eventbus.IMMessageResponseModel;
import tech.jiangtao.support.kit.realm.GroupRealm;
import tech.jiangtao.support.kit.util.ErrorAction;
import tech.jiangtao.support.kit.util.LogUtils;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: IMGroupManager </br>
 * Description: 群组管理器 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 02:45</br>
 * Update: 27/05/2017 02:45 </br>
 **/

public class IMGroupManager {

  private static final String TAG = IMGroupManager.class.getSimpleName();
  private Realm mRealm;
  private GroupServiceApi mGroupServiceApi;

  private IMGroupManager() {
    connectRealm();
  }

  public static IMGroupManager geInstance() {
    return IMGroupManagerHolder.sIMGroupManager;
  }

  private static class IMGroupManagerHolder {
    private static final IMGroupManager sIMGroupManager = new IMGroupManager();
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

  public void readSingleGroupRealm(IMMessageResponseModel model,
      IMListenerCollection.IMGroupQueryListener listener) {
    // 根据主键查询群组信息
    GroupRealm realm = mRealm.where(GroupRealm.class)
        .equalTo("groupId", model.getMessage().getGroup())
        .findFirst();
    if (realm != null) {
      listener.result(realm);
    } else {
      // 查询群组信息，并且写入数据库
      mGroupServiceApi = ApiService.getInstance().createApiService(GroupServiceApi.class);
      mGroupServiceApi.queryGroup(model.getMessage().getGroup())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe(groupRealm -> {
            if (groupRealm != null) {
              listener.result(groupRealm);
              storeSingleGroup(groupRealm);
            }
          }, new ErrorAction() {
            @Override public void call(Throwable throwable) {
              super.call(throwable);
              LogUtils.d(TAG, "获取群组失败" + throwable.getMessage());
            }
          });
    }
  }

  public void storeSingleGroup(GroupRealm groupRealm) {
    connectRealm();
    mRealm.executeTransactionAsync(new Realm.Transaction() {
      @Override public void execute(Realm realm) {
        realm.copyToRealmOrUpdate(groupRealm);
      }
    });
  }
}
