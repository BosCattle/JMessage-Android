package tech.jiangtao.support.ui;

import android.content.Context;
import android.content.Intent;
import com.melink.bqmmsdk.sdk.BQMM;
import java.util.UUID;
import tech.jiangtao.support.kit.init.SupportIM;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.ui.service.XMPPService;

public class SupportUI {

  public static void initialize(Context context,String serviceName,String resource,String host,int port){
    BQMM.getInstance().initConfig(context,BuildConfig.MM_AppId,BuildConfig.MM_AppSecret);
    SupportIM.initialize(context,serviceName, resource,host,port);
    Intent intent = new Intent(context, XMPPService.class);
    context.startService(intent);
  }
}
