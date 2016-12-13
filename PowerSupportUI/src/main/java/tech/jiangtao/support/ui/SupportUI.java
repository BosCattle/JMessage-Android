package tech.jiangtao.support.ui;

import android.content.Context;
import com.melink.bqmmsdk.sdk.BQMM;

public class SupportUI {

  public static void initialize(Context context){
    BQMM.getInstance().initConfig(context,BuildConfig.MM_AppId,BuildConfig.MM_AppSecret);
  }
}
