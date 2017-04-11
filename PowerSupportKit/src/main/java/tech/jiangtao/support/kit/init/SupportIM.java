package tech.jiangtao.support.kit.init;

import android.content.Context;
import io.realm.Realm;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: SupportIM </br>
 * Description: 拿到上下文的application </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 01/12/2016 11:15 PM</br>
 * Update: 01/12/2016 11:15 PM </br>
 * 初始化数据，全局保存
 **/
// TODO: 09/04/2017 有点流氓，后期升级
public class SupportIM {

  public static String mDomain;
  public static String mResource;
  public static String mHost;
  public static int mPort;
  public static final String USER = "user";
  private static void initialize(Context context){
    Realm.init(context);
    HermesEventBus.getDefault().init(context);
  }

  private static void initialize(Context context, String serviceName){
    initialize(context);
    mDomain = serviceName;
  }

  private static void initialize(Context context, String serviceName, String resource){
    initialize(context,serviceName);
    mResource = resource;
  }

  private static void initialize(Context context, String serviceName, String resource, String host){
    initialize(context,serviceName,resource);
    mHost = host;
  }

  public static void initialize(Context context,String serviceName,String resource,String host,int port){
    initialize(context,serviceName,resource,host);
    mPort = port;
  }
}
