package tech.jiangtao.support.ui;

import android.content.Context;
import android.content.Intent;
import com.melink.bqmmsdk.sdk.BQMM;
import io.realm.Realm;
import java.lang.annotation.Annotation;
import java.util.Properties;
import java.util.UUID;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.annotation.ChatRouter;
import tech.jiangtao.support.kit.annotation.GroupChatRouter;
import tech.jiangtao.support.kit.annotation.InvitedRouter;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.service.XMPPService;
import tech.jiangtao.support.kit.util.PropertyUtils;

public class SupportUI {

  private static String MM_APPID = "MM_APPID";
  private static String MM_AppSecret = "MM_APPSECRET";
  private static String PORT = "PORT";
  private static String SERVICE_NAME = "SERVICE_NAME";

  // server
  private static String HOST = "HOST";
  public static String RESOURCE_ADDRESS = "RESOURCE_ADDRESS";
  private static String API_ADDRESS = "API_ADDRESS";


  public static void initialize(Context context,String resourceName) {
    initValue(context,resourceName);
    String resource = UUID.randomUUID().toString();
    //---------------------------------------配置中心--------------------------------
    //------------------------------------------------------------------------------
    initialize(context, SERVICE_NAME, resource, HOST, Integer.parseInt(PORT), RESOURCE_ADDRESS,
        API_ADDRESS);
    BQMM.getInstance().initConfig(context, MM_APPID, MM_AppSecret);
    Realm.init(context);
  }

  private static void initValue(Context context,String resourceName) {
    Properties properties = PropertyUtils.getProperties(context,resourceName);
    MM_APPID = properties.getProperty(MM_APPID);
    MM_AppSecret = properties.getProperty(MM_AppSecret);
    PORT = properties.getProperty(PORT);
    SERVICE_NAME = properties.getProperty(SERVICE_NAME);

    // 线上
    HOST = properties.getProperty(HOST);
    RESOURCE_ADDRESS = properties.getProperty(RESOURCE_ADDRESS);
    API_ADDRESS = properties.getProperty(API_ADDRESS);
  }

  private static void initialize(Context context, String serviceName, String resource, String host,
      int port, String resourceAddress, String apiAddress) {
    SupportIM.initialize(context, serviceName, resource, host, port, resourceAddress, apiAddress);
    // 解析chat，groupchat，invite的页面
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
