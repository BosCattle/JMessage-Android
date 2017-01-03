package tech.jiangtao.support.kit.archive;

import android.util.Log;
import org.jivesoftware.smack.packet.IQ;

/**
 * Class: MessageArchiveRequestIQ </br>
 * Description: 自定义发送的消息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 05/12/2016 11:41 PM</br>
 * Update: 05/12/2016 11:41 PM </br>
 **/

public class MessageArchiveRequestIQ extends IQ {

  public static final String TAG = MessageArchiveRequestIQ.class.getSimpleName();
  public String jid;
  public String time;

  public MessageArchiveRequestIQ(String childElementName) {
    super("retrieve","urn:xmpp:archive");
    setType(Type.get);
  }

  public MessageArchiveRequestIQ(String childElementName, String childElementNamespace) {
    super("retrieve","urn:xmpp:archive");
    setType(Type.get);
  }

  public String getJid() {
    return jid;
  }

  public void setJid(String jid) {
    this.jid = jid;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  @Override protected IQChildElementXmlStringBuilder getIQChildElementBuilder(
      IQChildElementXmlStringBuilder xml) {
    if (getJid()!=null&&getJid()!="") {
      xml.optAttribute("with", getJid());
    }
    if (getTime()!=null&&getTime()!="") {
      xml.optAttribute("start", getTime());
    }
    xml.rightAngleBracket();
    Log.d(TAG, "getIQChildElementBuilder: "+xml.toString());
    return xml;
  }
}
