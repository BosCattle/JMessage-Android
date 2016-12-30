package tech.jiangtao.support.ui.pattern;

import tech.jiangtao.support.ui.model.Message;
import tech.jiangtao.support.ui.model.User;
import tech.jiangtao.support.ui.model.type.MessageType;

/**
 * Created by jiang on 2016/11/14.
 * 构建消息信息
 */

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
