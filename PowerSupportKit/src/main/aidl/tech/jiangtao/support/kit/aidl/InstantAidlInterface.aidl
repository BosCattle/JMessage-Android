package tech.jiangtao.support.kit.aidl;

import tech.jiangtao.support.kit.eventbus.TextMessage;
// 蛋疼的aidl
interface InstantAidlInterface {

   TextMessage recieveMessage(TextMessage message);
   void sendMessage(TextMessage message);

}
