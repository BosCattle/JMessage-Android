package tech.jiangtao.support.kit.callback;

import tech.jiangtao.support.kit.eventbus.QueryUserResult;

/**
 * Class: QueryUserCallBack </br>
 * Description: 查询用户信息回调 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/01/2017 8:56 PM</br>
 * Update: 06/01/2017 8:56 PM </br>
 **/

public interface QueryUserCallBack {

  void querySuccess(QueryUserResult result);

  void queryFail(String errorReason);
}
