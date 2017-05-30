package tech.jiangtao.support.kit.manager;

import io.realm.Realm;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import tech.jiangtao.support.kit.callback.IMListenerCollection;
import tech.jiangtao.support.kit.eventbus.IMRoomRequestModel;
import tech.jiangtao.support.kit.eventbus.IMRoomResponseModel;
import tech.jiangtao.support.kit.eventbus.IMRoomStoreModel;
import tech.jiangtao.support.kit.model.group.Group;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.realm.GroupRealm;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: IMRoomManager </br>
 * Description: 群组管理器 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 30/05/2017 06:11</br>
 * Update: 30/05/2017 06:11 </br>
 **/

public class IMRoomManager {

  private Realm mRealm;
  private IMListenerCollection.IMRoomCreateListener mIMRoomCreateListener;

  private IMRoomManager() {
    connectHermes();
    connectRealm();
  }

  public static IMRoomManager geInstance() {
    return IMRoomManagerHolder.sIMRoomManager;
  }

  private static class IMRoomManagerHolder {
    private static final IMRoomManager sIMRoomManager = new IMRoomManager();
  }

  /**
   * 保存了群组信息
   * @param model
   */
  // TODO: 30/05/2017 有问题
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void createRoomCallBack(IMRoomResponseModel model) {
    // 保存到数据库
    connectRealm();
    mRealm.executeTransaction(realm -> {
      GroupRealm groupRealm = realm.createObject(GroupRealm.class, model.roomId);
      groupRealm.setAvatar(model.avatar);
      groupRealm.setDescription(model.roomDesc);
      groupRealm.setName(model.nickName);
      for (String s : model.userIds) {
        IMContactManager.geInstance()
            .readSingleContact(s, new IMListenerCollection.IMRealmQueryListener<ContactRealm>() {
              @Override public void result(ContactRealm contactRealm) {
                groupRealm.contactRealms.add(contactRealm);
              }
            });
      }
      realm.copyToRealmOrUpdate(groupRealm);
      mIMRoomCreateListener.success(groupRealm);
    });
  }

  public void storeRoom(IMRoomStoreModel model){
    connectRealm();
    mRealm.executeTransaction(realm -> {
      GroupRealm groupRealm = realm.createObject(GroupRealm.class, model.roomId);
      groupRealm.setAvatar(model.avatar);
      groupRealm.setDescription(model.roomDesc);
      groupRealm.setName(model.nickName);
      for (String s : model.userIds) {
        IMContactManager.geInstance().readSingleContact(s, new IMListenerCollection.IMRealmQueryListener<ContactRealm>() {
          @Override public void result(ContactRealm contactRealm) {
            groupRealm.contactRealms.add(contactRealm);
          }
        });
      }
      realm.copyToRealmOrUpdate(groupRealm);
    });
  }

  /**
   * 创建群组
   */
  public void createRoom(IMRoomRequestModel model,
      IMListenerCollection.IMRoomCreateListener listener) {
    connectHermes();
    HermesEventBus.getDefault().postSticky(model);
    mIMRoomCreateListener = listener;
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
