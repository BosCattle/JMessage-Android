package tech.jiangtao.support.ui.fragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import tech.jiangtao.support.kit.eventbus.RecieveLastMessage;


/**
 * Class: BaseChatFragment </br>
 * Description: 聊天基类 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 17/04/2017 3:58 AM</br>
 * Update: 17/04/2017 3:58 AM </br>
 **/
public abstract class BaseChatFragment extends BaseFragment {

  @Subscribe(threadMode = ThreadMode.MAIN) public abstract void onMessage(RecieveLastMessage message);
}
