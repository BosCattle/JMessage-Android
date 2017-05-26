// ServiceListener.aidl
package tech.jiangtao.support.kit;
import tech.jiangtao.support.kit.Account;
import tech.jiangtao.support.kit.Result;

// Declare any non-default types here with import statements
interface ServiceListener {

  /**
   * 登录成功
   */
  void loginSuccess(Account user);

  /**
   * 登录失败
   * @param e
   */
  void loginFailed(Result result);

  /**
   * 退出登录成功
   */
  void disconnectFinish();
}
