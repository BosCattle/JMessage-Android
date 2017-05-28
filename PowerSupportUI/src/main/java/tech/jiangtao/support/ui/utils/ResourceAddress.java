package tech.jiangtao.support.ui.utils;

import tech.jiangtao.support.kit.SupportIM;
import tech.jiangtao.support.kit.archive.type.DataExtensionType;
import tech.jiangtao.support.kit.model.type.TransportType;
import tech.jiangtao.support.kit.realm.SessionRealm;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import tech.jiangtao.support.kit.util.StringUtils;

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
    if (type == null || resourceId == null) {
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

  public static String avatar(SessionRealm session) {
    String avatar = "";
    switch (session.getMessageType()) {
      case 0:
        avatar = session.getContactRealm().getAvatar() == null ? ""
            : session.getContactRealm().getAvatar();
        break;
      case 1:
        if (session.getGroupRealm()==null){
          avatar = "";
        }else if (session.getGroupRealm().getAvatar() == null){
          avatar = "";
        }else {
          avatar = session.getGroupRealm().getAvatar();
        }
        break;
    }
    return avatar;
  }

  public static String nickName(SessionRealm session) {
    String nickName = "";
    switch (session.getMessageType()) {
      case 0:
        if (session.getContactRealm().getNickName() == null) {
          nickName = StringSplitUtil.splitPrefix(session.getContactRealm().getUserId());
        } else {
          nickName = session.getContactRealm().getNickName();
        }
        break;
      case 1:
        if (session.getGroupRealm() == null) {
          nickName = "群组名为空";
        } else if (session.getGroupRealm().getName() == null) {
          nickName = StringSplitUtil.splitPrefix(session.getGroupRealm().getGroupId());
        } else {
          nickName = session.getGroupRealm().getName();
        }
        break;
    }
    return nickName;
  }

  public static String content(SessionRealm sessionRealm) {
    String message = "";
    switch (sessionRealm.getMessageType()) {
      case 0:
        if (sessionRealm.getMessageRealm()
            .getMessageType()
            .equals(DataExtensionType.TEXT.toString())) {
          message = sessionRealm.getMessageRealm().textMessage;
        }
        if (sessionRealm.getMessageRealm()
            .getMessageType()
            .equals(DataExtensionType.IMAGE.toString())) {
          message = "图片";
        }
        if (sessionRealm.getMessageRealm()
            .getMessageType()
            .equals(DataExtensionType.AUDIO.toString())) {
          message = "语音";
        }
        if (sessionRealm.getMessageRealm()
            .getMessageType()
            .equals(DataExtensionType.VIDEO.toString())) {
          message = "视频";
        }
        break;
      case 1:
        if (sessionRealm.getMessageRealm()
            .getMessageType()
            .equals(DataExtensionType.TEXT.toString())) {
          message = sessionRealm.getContactRealm().getNickName()
              + ": "
              + sessionRealm.getMessageRealm().textMessage;
        }
        if (sessionRealm.getMessageRealm()
            .getMessageType()
            .equals(DataExtensionType.IMAGE.toString())) {
          message = sessionRealm.getContactRealm().getNickName() + ": 图片";
        }
        if (sessionRealm.getMessageRealm()
            .getMessageType()
            .equals(DataExtensionType.AUDIO.toString())) {
          message = sessionRealm.getContactRealm().getNickName() + ": 语音";
        }
        if (sessionRealm.getMessageRealm()
            .getMessageType()
            .equals(DataExtensionType.VIDEO.toString())) {
          message = sessionRealm.getContactRealm().getNickName() + ": 视频";
        }
        break;
    }
    return message;
  }
}
