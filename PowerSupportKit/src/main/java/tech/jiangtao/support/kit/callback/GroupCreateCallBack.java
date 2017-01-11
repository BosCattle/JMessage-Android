package tech.jiangtao.support.kit.callback;

/**
 * Class: GroupCreateCallBack </br>
 * Description: 创建群组回调 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 12/01/2017 12:54 AM</br>
 * Update: 12/01/2017 12:54 AM </br>
 **/

public interface GroupCreateCallBack {

  // 加入群组信息
  void createSuccess();

  void createFailed(String failedReason);
}
