package tech.jiangtao.support.kit.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.sql.Date;
import java.util.UUID;

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
  public String id = UUID.randomUUID().toString();
  public String mainJID;
  public String withJID;
  public String textMessage;
  public java.util.Date time;
  public String thread;

  public java.util.Date getTime() {
    return time;
  }

  public void setTime(java.util.Date time) {
    this.time = time;
  }

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

  public void setTime(Date time) {
    this.time = time;
  }

  public String getThread() {
    return thread;
  }

  public void setThread(String thread) {
    this.thread = thread;
  }

  public String getTextMessage() {
    return textMessage;
  }

  public void setTextMessage(String textMessage) {
    this.textMessage = textMessage;
  }
}
