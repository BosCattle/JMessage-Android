package tech.jiangtao.support.kit;

import android.content.Context;
import android.content.Intent;
import io.realm.Realm;
import java.lang.annotation.Annotation;
import java.util.Properties;
import java.util.UUID;
import tech.jiangtao.support.kit.annotation.ChatRouter;
import tech.jiangtao.support.kit.annotation.GroupChatRouter;
import tech.jiangtao.support.kit.annotation.InvitedRouter;
import tech.jiangtao.support.kit.manager.IMSettingManager;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.service.XMPPService;
import tech.jiangtao.support.kit.util.PropertyUtils;
import xiaofei.library.hermes.Hermes;
import xiaofei.library.hermes.HermesListener;
import xiaofei.library.hermes.HermesService;
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
  public static final String SENDERFRIENDID = "sessionId";
  public static final String MESSAGE_ID = "id";
  public static final String PINYIN = "pinYin";
  public static final String GROUPID = "groupId";
  public static final String SESSIONID = "sessionId";
  public static final String NEW_FLAG = "add_friend";
  public static final String MUC_GROUP = "muc-group";
  public static final String GROUP = "group";

  private static String MM_APPID = "MM_APPID";
  private static String MM_AppSecret = "MM_APPSECRET";
  private static String PORT = "PORT";
  private static String SERVICE_NAME = "SERVICE_NAME";

  // server
  private static String HOST = "HOST";
  public static String RESOURCE_ADDRESS_CONFIG = "RESOURCE_ADDRESS";
  private static String API_ADDRESS_CONFIG = "API_ADDRESS";

  private static void initialize(Context context) {
    HermesEventBus.getDefault().init(context);
  }

  public static void initialize(Context context, String resourceName) {
    initValue(context, resourceName);
    IMSettingManager manager = new IMSettingManager();
    String resource = manager.getAndroidId(context);
    //---------------------------------------配置中心--------------------------------
    //------------------------------------------------------------------------------
    initialize(context, SERVICE_NAME, resource, HOST, Integer.parseInt(PORT), RESOURCE_ADDRESS,
        API_ADDRESS);
    Realm.init(context);
    HermesEventBus.getDefault().init(context);
    Hermes.setHermesListener(new HermesListener() {
      @Override public void onHermesConnected(Class<? extends HermesService> service) {

      }

      @Override public void onHermesDisconnected(Class<? extends HermesService> service) {
        super.onHermesDisconnected(service);
        HermesEventBus.getDefault().init(context);
      }
    });
  }

  private static void initValue(Context context, String resourceName) {
    Properties properties = PropertyUtils.getProperties(context, resourceName);
    MM_APPID = properties.getProperty(MM_APPID);
    MM_AppSecret = properties.getProperty(MM_AppSecret);
    PORT = properties.getProperty(PORT);
    SERVICE_NAME = properties.getProperty(SERVICE_NAME);

    // 线上
    HOST = properties.getProperty(HOST);
    RESOURCE_ADDRESS = properties.getProperty(RESOURCE_ADDRESS_CONFIG);
    API_ADDRESS = properties.getProperty(API_ADDRESS_CONFIG);
  }

  private static void initializeContext(Context context, String serviceName) {
    initialize(context);
    mDomain = serviceName;
  }

  private static void initialize(Context context, String serviceName, String resource) {
    initializeContext(context, serviceName);
    mResource = resource;
  }

  private static void initialize(Context context, String serviceName, String resource,
      String host) {
    initialize(context, serviceName, resource);
    mHost = host;
  }

  public static void initialize(Context context, String serviceName, String resource, String host,
      int port, String resourceAddress, String apiAddress) {
    HermesEventBus.getDefault().init(context);
    initialize(context, serviceName, resource, host);
    RESOURCE_ADDRESS = resourceAddress;
    API_ADDRESS = apiAddress;
    mPort = port;

    Class clazz = context.getApplicationContext().getClass();
    Annotation[] annotations = clazz.getAnnotations();
    Class chatClazz = null;
    Class groupChatClazz = null;
    Class invitedClass = null;
    for (int i = 0; i < annotations.length; i++) {
      Annotation annotation = annotations[i];
      if (annotation instanceof ChatRouter) {
        chatClazz = ((ChatRouter) annotation).router();
      }
      if (annotation instanceof GroupChatRouter) {
        groupChatClazz = ((GroupChatRouter) annotation).router();
      }
      if (annotation instanceof InvitedRouter) {
        invitedClass = ((InvitedRouter) annotation).router();
      }
    }
    Intent intent = new Intent(context, XMPPService.class);
    intent.putExtra(XMPPService.CHAT_CLASS, chatClazz);
    intent.putExtra(XMPPService.GROUP_CHAT_CLASS, groupChatClazz);
    intent.putExtra(XMPPService.INVITED_CLASS, invitedClass);
    context.startService(intent);
    Intent intent1 = new Intent(context, SupportService.class);
    context.startService(intent1);
  }
}
