package tech.jiangtao.support.ui;

import android.content.Context;
import android.content.Intent;
import com.melink.bqmmsdk.sdk.BQMM;
import java.util.Properties;
import java.util.UUID;
import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.ui.service.SupportService;
import tech.jiangtao.support.ui.service.XMPPService;
import tech.jiangtao.support.ui.utils.PropertyUtils;

public class SupportUI {

  private static String MM_APPID = "MM_APPID";
  private static String MM_AppSecret = "MM_APPSECRET";
  private static String PORT = "PORT";
  private static String SERVICE_NAME = "SERVICE_NAME";

  // server
  private static String HOST = "HOST";
  public static String RESOURCE_ADDRESS = "RESOURCE_ADDRESS";
  private static String API_ADDRESS = "API_ADDRESS";

  // company
  private static String HOST1 = "HOST1";
  public static String RESOURCE_ADDRESS1 = "RESOURCE_ADDRESS1";
  private static String API_ADDRESS1 = "API_ADDRESS1";

  // home
  private static String HOST2 = "HOST2";
  public static String RESOURCE_ADDRESS2 = "RESOURCE_ADDRESS2";
  private static String API_ADDRESS2 = "API_ADDRESS2";

  public static void initialize(Context context) {
    initValue(context);
    String resource = UUID.randomUUID().toString();
    //---------------------------------------配置中心--------------------------------
    //------------------------------------------------------------------------------
    initialize(context, SERVICE_NAME, resource, HOST2, Integer.parseInt(PORT),
        RESOURCE_ADDRESS2, API_ADDRESS2);
    BQMM.getInstance().initConfig(context, MM_APPID, MM_AppSecret);
  }

  private static void initValue(Context context) {
    Properties properties = PropertyUtils.getProperties(context);
    MM_APPID = properties.getProperty(MM_APPID);
    MM_AppSecret = properties.getProperty(MM_AppSecret);
    PORT = properties.getProperty(PORT);
    SERVICE_NAME = properties.getProperty(SERVICE_NAME);

    // 线上
    HOST = properties.getProperty(HOST);
    RESOURCE_ADDRESS = properties.getProperty(RESOURCE_ADDRESS);
    API_ADDRESS = properties.getProperty(API_ADDRESS);

    // 公司
    HOST1 = properties.getProperty(HOST1);
    RESOURCE_ADDRESS1 = properties.getProperty(RESOURCE_ADDRESS1);
    API_ADDRESS1 = properties.getProperty(API_ADDRESS1);

    // 家里
    HOST2 = properties.getProperty(HOST2);
    RESOURCE_ADDRESS2 = properties.getProperty(RESOURCE_ADDRESS2);
    API_ADDRESS2 = properties.getProperty(API_ADDRESS2);
  }

  private static void initialize(Context context, String serviceName, String resource, String host,
      int port, String resourceAddress, String apiAddress) {
    SupportIM.initialize(context, serviceName, resource, host, port, resourceAddress, apiAddress);
    Intent intent = new Intent(context, XMPPService.class);
    context.startService(intent);
    Intent intent1 = new Intent(context, SupportService.class);
    context.startService(intent1);
  }
}
