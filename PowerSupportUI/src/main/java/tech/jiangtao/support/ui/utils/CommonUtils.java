package tech.jiangtao.support.ui.utils;

import tech.jiangtao.support.ui.BuildConfig;

/**
 * Created by kevin on 29/12/2016.
 */

public class CommonUtils {

  public static String getUrl(String type,String name){
    return BuildConfig.BASE_URL+type+"/"+name;
  }
}
