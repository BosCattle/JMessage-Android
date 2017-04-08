package tech.jiangtao.support.kit.util;

import android.util.Log;
import rx.functions.Action1;

/**
 * Class: ErrorAction </br>
 * Description: 错误的响应 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 09/04/2017 2:07 AM</br>
 * Update: 09/04/2017 2:07 AM </br>
 **/

public abstract class ErrorAction implements Action1<Throwable> {
  protected static final String TAG = ErrorAction.class.getSimpleName();

  @Override public void call(Throwable throwable) {
    Log.d(TAG, "call: "+throwable);
  }

}
