package tech.jiangtao.support.kit;

import android.content.Context;
import io.realm.Realm;
import io.realm.RealmConfiguration;
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
  public static String RESOURCE_ADDRESS;
  public static String API_ADDRESS;
  public static final String USER = "user";
  public static final String USER_ID = "userId";
  public static final String USER_NAME = "nickName";
  public static final String USER_REAL_NAME = "username";
  public static final String USER_PASSWORD = "password";
  public static final String VCARD = "contact";
  public static final String SENDER = "sender";
  public static final String RECEIVER = "receiver";
  public static final String SENDERFRIENDID = "senderFriendId";
  public static final String MESSAGE_ID = "id";
  public static final String PINYIN = "pinYin";
  public static final String GROUPID = "groupId";
  public static final String SESSIONID = "sessionId";
  public static final String NEW_FLAG = "add_friend";
  public static final String MUC_GROUP = "muc-group";
  public static final String GROUP = "group";

  private static void initialize(Context context){
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

  public static void initialize(Context context,String serviceName,String resource,String host,int port,String resourceAddress,String apiAddress){
    initialize(context,serviceName,resource,host);
    RESOURCE_ADDRESS = resourceAddress;
    API_ADDRESS = apiAddress;
    mPort = port;
  }
}
