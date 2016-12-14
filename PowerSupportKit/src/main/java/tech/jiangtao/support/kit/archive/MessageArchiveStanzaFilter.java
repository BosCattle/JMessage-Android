package tech.jiangtao.support.kit.archive;

import android.util.Log;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Stanza;

/**
 * Class: MessageArchiveStanzaFilter </br>
 * Description: 基于消息归档的拦截器 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/12/2016 12:31 PM</br>
 * Update: 06/12/2016 12:31 PM </br>
 **/

public class MessageArchiveStanzaFilter implements StanzaFilter {
  @Override public boolean accept(Stanza stanza) {
//    return stanza.hasExtension("chat", "urn:xmpp:archive");
    return true;
  }
}
