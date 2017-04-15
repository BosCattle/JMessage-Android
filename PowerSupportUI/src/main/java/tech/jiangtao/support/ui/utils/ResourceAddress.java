package tech.jiangtao.support.ui.utils;

import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.ui.model.type.TransportType;

/**
 * Class: ResourceAddress </br>
 * Description: 根据resourceId拼接url </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 12/04/2017 10:46 AM</br>
 * Update: 12/04/2017 10:46 AM </br>
 **/

public class ResourceAddress {

  public static String url(String resourceId, TransportType type) {
    if (type==null||resourceId==null){
      return null;
    }
    if (type == TransportType.AVATAR) {
      return SupportIM.RESOURCE_ADDRESS + "avatar/" + resourceId;
    } else if (type == TransportType.IMAGE) {
      return SupportIM.RESOURCE_ADDRESS + "image/" + resourceId;
    } else if (type == TransportType.AUDIO) {
      return SupportIM.RESOURCE_ADDRESS + "audio/" + resourceId;
    } else if (type == TransportType.VIDEO) {
      return SupportIM.RESOURCE_ADDRESS + "video/" + resourceId;
    } else if (type == TransportType.FILE) {
      return SupportIM.RESOURCE_ADDRESS + "file/" + resourceId;
    }
    return null;
  }
}
