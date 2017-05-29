package tech.jiangtao.support.kit.manager;

import io.realm.Realm;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import tech.jiangtao.support.kit.callback.IMListenerCollection;
import tech.jiangtao.support.kit.eventbus.IMRoomRequestModel;
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
   * 创建群组
   * @param model
   * @param listener
   */
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void createRoom(IMRoomRequestModel model,IMListenerCollection.IMRoomCreateListener listener){
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
