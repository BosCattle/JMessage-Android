package com.china.epower.chat.ui.pattern;

import com.china.epower.chat.model.Message;
import com.china.epower.chat.model.type.MessageType;
import com.china.epower.chat.model.User;

/**
 * Created by jiang on 2016/11/14.
 * 构建消息信息
 */

public class ConstructMessage  {
    public User mUser;
    public Message mMessage;
    public MessageType mMessageType;

    public static class Builder{
        public User user;
        public Message message;
        public MessageType messageType;

        public ConstructMessage build(){
            ConstructMessage constructMessage = new ConstructMessage();
            constructMessage.mUser = user;
            constructMessage.mMessage = message;
            constructMessage.mMessageType = messageType;
            return constructMessage;
        }

        public Builder user(User userParam){
            user = userParam;
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
