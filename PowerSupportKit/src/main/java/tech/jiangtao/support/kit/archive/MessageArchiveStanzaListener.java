package tech.jiangtao.support.kit.archive;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;

import java.sql.Date;

import java.util.Arrays;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.packet.Stanza;

import tech.jiangtao.support.kit.archive.type.MessageBodyType;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.realm.VCardRealm;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.util.DateUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Class: MessageArchiveStanzaListener </br>
 * Description: 服务器返回的归档消息解析 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 05/12/2016 11:49 PM</br>
 * Update: 05/12/2016 11:49 PM </br>
 **/
// TODO: 03/01/2017 不能在这儿保存消息到本地数据库
public class MessageArchiveStanzaListener implements StanzaListener {


  public MessageArchiveStanzaListener(){
  }

  public static final String TAG = MessageArchiveStanzaListener.class.getSimpleName();

  @Override public void processPacket(Stanza packet) throws SmackException.NotConnectedException {
    if (packet instanceof MessageArchiveAnswerIQ) {
      MessageArchiveAnswerIQ maaIQ = (MessageArchiveAnswerIQ) packet;
      for (MessageBody body : maaIQ.messageBody) {
        //将消息发送到本地可保存的服务中，拼接
        HermesEventBus.getDefault().post(body);
      }
    }
  }

  //private void updateMessageRealm(MessageRealm messageRealm, MessageBody body) {
  //java.util.Date date =
  //    DateUtils.getSumUTCTimeZone(DateUtils.UTCConvertToLong(time), Long.valueOf(body.getSecs()));
  //  messageRealm.setThread(body.getThread());
  //  messageRealm.setTextMessage(body.getBody());
  //  if (body.getType() == MessageBodyType.TYPE_FROM) {
  //    messageRealm.setMainJID(body.getWith());
  //    messageRealm.setWithJID(SupportService.getmXMPPConnection().getUser());
  //  } else {
  //    messageRealm.setMainJID(SupportService.getmXMPPConnection().getUser());
  //    messageRealm.setWithJID(body.getWith());
  //  }
  //}
  //
  //private void createMessageRealm(MessageRealm messageRealm, MessageBody body,
  //    java.util.Date time) {
  //  messageRealm.setThread(body.getThread());
  //  messageRealm.setTextMessage(body.getBody());
  //  messageRealm.setTime(time);
  //  String other = body.getWith();
  //  String main =SupportService.getmXMPPConnection().getUser();
  //  Log.d(TAG, "createMessageRealm: "+other+"       "+main);
  //  if (body.getType() == MessageBodyType.TYPE_FROM) {
  //    messageRealm.setMainJID(other);
  //    messageRealm.setWithJID(main);
  //  } else {
  //    messageRealm.setMainJID(main);
  //    messageRealm.setWithJID(other);
  //  }
  //}
}
