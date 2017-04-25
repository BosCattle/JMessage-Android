package tech.jiangtao.support.ui.pattern;

import tech.jiangtao.support.kit.model.Message;
import tech.jiangtao.support.kit.model.type.MessageType;

/**
 * Class: ConstructMessage </br>
 * Description: 构建消息信息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 09/04/2017 1:59 AM</br>
 * Update: 09/04/2017 1:59 AM </br>
 **/

public class ConstructMessage  {
    public String mAvatar;
    public Message mMessage;
    public MessageType mMessageType;

    public static class Builder{
        public String avatar;
        public Message message;
        public MessageType messageType;

        public ConstructMessage build(){
            ConstructMessage constructMessage = new ConstructMessage();
            constructMessage.mAvatar = avatar;
            constructMessage.mMessage = message;
            constructMessage.mMessageType = messageType;
            return constructMessage;
        }

        public Builder avatar(String avatar){
            this.avatar = avatar;
            return this;
        }

        public Builder message(Message message1){
            message = message1;
            return this;
        }

        public Builder itemType(MessageType messageType1){
            this.messageType = messageType1;
            return this;
        }
    }
}
