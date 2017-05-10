package tech.jiangtao.support.kit.util;

import android.content.Context;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class: PropertyUtils </br>
 * Description: 读取配置文件 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 13/04/2017 9:08 PM</br>
 * Update: 13/04/2017 9:08 PM </br>
 **/

public class PropertyUtils {

  /**
   * 获取属性文件
   * @param c  {@link Context} 上下文
   * @return  {@link Properties}
   */
  public static Properties getProperties(Context c){
    Properties props = new Properties();
    try {
      InputStream in = c.getAssets().open("resource.properties");
      props.load(in);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return props;
  }

  /**
   * 获取属性文件
   * @param c {@link Context}  上下文
   * @param resource  资源文件位置
   * @return
   */
  public static Properties getProperties(Context c,String resource){
    Properties props = new Properties();
    try {
      InputStream in = c.getAssets().open(resource);
      props.load(in);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return props;
  }
}
