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

/**
 * Class: MessageArchiveStanzaListener </br>
 * Description: 服务器返回的归档消息解析 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 05/12/2016 11:49 PM</br>
 * Update: 05/12/2016 11:49 PM </br>
 **/

public class MessageArchiveStanzaListener implements StanzaListener {

  private Realm mRealm;

  public static final String TAG = MessageArchiveStanzaListener.class.getSimpleName();

  @Override public void processPacket(Stanza packet) throws SmackException.NotConnectedException {
    //具体的消息包处理工具
    if (mRealm == null) {
      mRealm = Realm.getDefaultInstance();
    }
    if (packet instanceof MessageArchiveAnswerIQ) {
      MessageArchiveAnswerIQ maaIQ = (MessageArchiveAnswerIQ) packet;
      for (MessageBody body : maaIQ.messageBody) {
        setMessageRealm(body, maaIQ.getTime());
      }
    }
  }

  private void setMessageRealm(MessageBody body, String time) {
    //首先，判断数据库中是否有改内容
    MessageRealm messageRealm = null;
    //date有问题,老子也是醉了
    java.util.Date date =
        DateUtils.getSumUTCTimeZone(DateUtils.UTCConvertToLong(time), Long.valueOf(body.getSecs()));
    messageRealm = new MessageRealm();
    createMessageRealm(messageRealm, body, date);
    mRealm.beginTransaction();
    mRealm.copyToRealm(messageRealm);
    mRealm.commitTransaction();
  }

  private void updateMessageRealm(MessageRealm messageRealm, MessageBody body) {
    messageRealm.setThread(body.getThread());
    messageRealm.setTextMessage(body.getBody());
    if (body.getType() == MessageBodyType.TYPE_FROM) {
      messageRealm.setMainJID(body.getWith());
      messageRealm.setWithJID(SupportService.getmXMPPConnection().getUser());
    } else {
      messageRealm.setMainJID(SupportService.getmXMPPConnection().getUser());
      messageRealm.setWithJID(body.getWith());
    }
  }

  private void createMessageRealm(MessageRealm messageRealm, MessageBody body,
      java.util.Date time) {
    messageRealm.setThread(body.getThread());
    messageRealm.setTextMessage(body.getBody());
    messageRealm.setTime(time);
    String other = StringSplitUtil.splitDivider(body.getWith());
    String main = StringSplitUtil.splitDivider(SupportService.getmXMPPConnection().getUser());
    Log.d(TAG, "createMessageRealm: "+other+"       "+main);
    if (body.getType() == MessageBodyType.TYPE_FROM) {
      messageRealm.setMainJID(other);
      messageRealm.setWithJID(main);
    } else {
      messageRealm.setMainJID(main);
      messageRealm.setWithJID(other);
    }
  }
}
