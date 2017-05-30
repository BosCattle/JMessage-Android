package tech.jiangtao.support.kit.callback;

import io.realm.RealmObject;
import io.realm.RealmResults;
import java.util.List;
import tech.jiangtao.support.kit.eventbus.IMAddContactResponseModel;
import tech.jiangtao.support.kit.eventbus.IMDeleteContactResponseModel;
import tech.jiangtao.support.kit.model.Account;
import tech.jiangtao.support.kit.model.IMFilePath;
import tech.jiangtao.support.kit.model.Result;
import tech.jiangtao.support.kit.realm.ContactRealm;
import tech.jiangtao.support.kit.realm.GroupRealm;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.realm.SessionRealm;

/**
 * Class: IMListenerCollection </br>
 * Description: 监听器集合 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 27/05/2017 07:12</br>
 * Update: 27/05/2017 07:12 </br>
 **/

public interface IMListenerCollection {

  /**
   * 数据库改变
   */
  interface IMRealmChangeListener<T extends RealmObject> {

    void change(List<T> realmResults);
  }

  /**
   * 查询条ContactRealm
   */
  interface IMRealmQueryListener<T extends RealmObject> {
    void result(ContactRealm contactRealm);
  }

  /**
   * 登录回调
   */
  interface IMLoginListener {

    void loginSuccess(Account account);

    void loginFailed(Result result);
  }

  /**
   * 退出登录
   */
  interface IMExitlistener {

    void exitSuccess();
  }

  /**
   * 添加好友的回调
   */
  interface IMAddContactListener {

    void addContactSuccess(IMAddContactResponseModel model);

    void addContactFailed(IMAddContactResponseModel model);
  }

  /**
   * 删除好友的回调
   */
  interface IMDeleteContactListener<T extends ContactRealm> {

    void deleteContactSuccess(IMDeleteContactResponseModel model);

    void deleteContactFailed(IMDeleteContactResponseModel model);
  }

  /**
   * 关于好友的骚操作
   */
  interface IMFriendNotificationListener {
    // 接收到好友邀请
    void receivedUserInvited(ContactRealm contactRealm);

    // 好友同意了邀请
    void receivedAgreeInvited(ContactRealm contactRealm);

    // 好友拒绝了邀请
    void receivedRejectInvited(ContactRealm contactRealm);
  }

  /**
   * 我同意或者拒绝好友邀请的回调
   */
  interface IMDealFriendInvitedListener {

    void success();

    void failed();
  }

  //--------------------------------有关消息---------------------------------//

  /**
   * 获取消息
   */
  interface IMMessageNotificationListener {
    /**
     * 返回当前消息
     * @param messageRealms 当前页数的消息
     * @param page 总页数
     */
    void change(List<MessageRealm> messageRealms,int page);
  }

  /**
   * 获得消息来了
   */
  interface IMMessageChangeListener {
    /**
     * 发送消息成功
     * @param messageRealm
     */
    void message(MessageRealm messageRealm);

    /**
     * 发送消息失败
     * @param result
     */
    void error(Result result);
  }

  //--------------------------------附加功能--------------------------------------//
  interface IMFileUploadListener{
    void success(IMFilePath path);
    void failed(Result result);
  }

  //--------------------------------有关会话的-------------------------------------//

  /**
   * 会话改变
   */
  interface IMConversationChangeListener {
    void change(List<SessionRealm> sessionRealms);
  }

  /**
   * 删除会话
   */
  interface IMConversationDeleteListener {
    void success();

    void error(Result result);
  }

  /**
   * 查询单个会话
   */
  interface IMConversationQueryListener {
    void result(SessionRealm sessionRealm);
  }

  // --------------------------------------群组-------------------------------------//

  /**
   * 获取单个群组信息
   */
  interface IMGroupQueryListener {
    void result(GroupRealm group);
  }

  //---------------------------------------Room-----------------------------------//
  interface IMRoomCreateListener{
    /**
     * 创建群组的回调
     * @param groupRealm
     */
    void success(GroupRealm groupRealm);

    void error(Result result);
  }
}
