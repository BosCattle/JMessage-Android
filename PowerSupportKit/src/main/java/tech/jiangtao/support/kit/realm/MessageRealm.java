package tech.jiangtao.support.kit.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.sql.Date;

/**
 * Class: MessageRealm </br>
 * Description: 消息存储 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 05/12/2016 12:54 AM</br>
 * Update: 05/12/2016 12:54 AM </br>
 * 将time转换为{@link Date}
 * http://xmpp.org/extensions/xep-0082.html
 * Example 2. Datetime of the first human steps on the Moon (UTC)
 * 1969-07-21T02:56:15Z
 **/

public class MessageRealm extends RealmObject {

  @PrimaryKey
  public String id;
  // message_from
  public String mainJID;
  // message_to
  public String withJID;
  // 消息内容
  public String textMessage;
  // TODO: 03/01/2017 暂时保留时间戳，备用
  public java.util.Date time;
  // // TODO: 03/01/2017 暂时保留，thread_id
  public String thread;
  // 具体到聊天类型
  public String type;
  // 消息类型
  public String messageType;
  // 消息状态
  // TODO: 03/01/2017 true: 表示已读，false:表示未读
  public boolean messageStatus;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMainJID() {
    return mainJID;
  }

  public void setMainJID(String mainJID) {
    this.mainJID = mainJID;
  }

  public String getWithJID() {
    return withJID;
  }

  public void setWithJID(String withJID) {
    this.withJID = withJID;
  }

  public String getTextMessage() {
    return textMessage;
  }

  public void setTextMessage(String textMessage) {
    this.textMessage = textMessage;
  }

  public java.util.Date getTime() {
    return time;
  }

  public void setTime(java.util.Date time) {
    this.time = time;
  }

  public String getThread() {
    return thread;
  }

  public void setThread(String thread) {
    this.thread = thread;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMessageType() {
    return messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public boolean isMessageStatus() {
    return messageStatus;
  }

  public void setMessageStatus(boolean messageStatus) {
    this.messageStatus = messageStatus;
  }
}
