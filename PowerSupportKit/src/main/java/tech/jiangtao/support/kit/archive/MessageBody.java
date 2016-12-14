package tech.jiangtao.support.kit.archive;

import tech.jiangtao.support.kit.archive.type.MessageBodyType;

/**
 * Class: MessageBody </br>
 * Description: 消息体 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/12/2016 10:06 AM</br>
 * Update: 06/12/2016 10:06 AM </br>
 **/

public class MessageBody {
  public MessageBodyType type;
  public String secs;
  public String with;
  public String body;
  public String thread;

  public String getSecs() {
    return secs;
  }

  public void setSecs(String secs) {
    this.secs = secs;
  }

  public String getWith() {
    return with;
  }

  public void setWith(String with) {
    this.with = with;
  }

  public MessageBody(MessageBodyType type, String secs, String with, String body, String thread) {
    this.type = type;
    this.secs = secs;
    this.with = with;
    this.body = body;
    this.thread = thread;
  }

  public MessageBody(MessageBodyType type, String secs, String body, String thread) {
    this.type = type;
    this.secs = secs;
    this.body = body;
    this.thread = thread;
  }

  public MessageBody(String body, String thread) {
    this.body = body;
    this.thread = thread;
  }

  public MessageBodyType getType() {
    return type;
  }

  public void setType(MessageBodyType type) {
    this.type = type;
  }

  public MessageBody(MessageBodyType type, String body, String thread) {
    this.type = type;
    this.body = body;
    this.thread = thread;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getThread() {
    return thread;
  }

  public void setThread(String thread) {
    this.thread = thread;
  }
}
