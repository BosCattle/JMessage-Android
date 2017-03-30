package tech.jiangtao.support.ui.utils;

import tech.jiangtao.support.ui.SupportUI;

/**
 * Class: CommonUtils </br>
 * Description: 配置资源链接 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 07/01/2017 8:18 PM</br>
 * Update: 07/01/2017 8:18 PM </br>
 **/

public class CommonUtils {

  public static String getUrl(String type,String name){
    return SupportUI.BASE_URL+type+"/"+name;
  }
}
