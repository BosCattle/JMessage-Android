package tech.jiangtao.support.kit.eventbus;

import org.jivesoftware.smack.packet.Message;

/**
 * Class: RecieveMessage </br>
 * Description: 接受到的消息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/12/2016 8:30 PM</br>
 * Update: 06/12/2016 8:30 PM </br>
 **/

public class RecieveMessage extends BaseMessage {

  public String userJid;
  public Message.Type type;

  public RecieveMessage(Object message,Message.Type type,String userJid) {
    super(message);
    this.userJid = userJid;
    this.type = type;
  }
}
