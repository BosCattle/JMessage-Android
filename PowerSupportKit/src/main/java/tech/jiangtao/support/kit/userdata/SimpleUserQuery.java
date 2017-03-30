package tech.jiangtao.support.kit.userdata;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import tech.jiangtao.support.kit.callback.QueryUserCallBack;
import tech.jiangtao.support.kit.eventbus.QueryUser;
import tech.jiangtao.support.kit.eventbus.QueryUserResult;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: SimpleUserQuery </br>
 * Description: 查询用户信息回调 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/01/2017 8:55 PM</br>
 * Update: 06/01/2017 8:55 PM </br>
 **/

public class SimpleUserQuery {

  private QueryUserCallBack mCallback;

  public SimpleUserQuery(){
    if (!HermesEventBus.getDefault().isRegistered(this)){
      HermesEventBus.getDefault().register(this);
    }
  }

  public void startQuery(QueryUser user,QueryUserCallBack callback){
    mCallback = callback;
    HermesEventBus.getDefault().post(user);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onQueryResult(QueryUserResult result){
    if (result!=null){
      mCallback.querySuccess(result);
    }else {
      mCallback.queryFail("查询失败");
    }
  }

  public void destroy(){
    HermesEventBus.getDefault().unregister(this);
  }
}
