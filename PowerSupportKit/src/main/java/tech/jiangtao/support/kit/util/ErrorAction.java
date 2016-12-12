package tech.jiangtao.support.kit.util;

import android.util.Log;
import rx.functions.Action1;

/**
 * Created by kevin on 10/11/2016.
 */

public abstract class ErrorAction implements Action1<Throwable> {
  protected static final String TAG = ErrorAction.class.getSimpleName();

  @Override public void call(Throwable throwable) {
    Log.d(TAG, "call: "+throwable);
  }

}
