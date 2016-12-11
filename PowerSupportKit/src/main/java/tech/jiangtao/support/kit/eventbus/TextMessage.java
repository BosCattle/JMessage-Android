package tech.jiangtao.support.kit.eventbus;

import org.jivesoftware.smack.packet.Message;

/**
 * Class: TextMessage </br>
 * Description: 发送出去的文本消息，待重构 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 04/12/2016 3:21 AM</br>
 * Update: 04/12/2016 3:21 AM </br>
 **/

public class TextMessage extends BaseMessage {
  private Message.Type type;
  public String userJID;
  public String message;

  public TextMessage(Message.Type type, String userJID, String message) {
    super(message);
    this.type = type;
    this.userJID = userJID;
    this.message = message;
  }
}
