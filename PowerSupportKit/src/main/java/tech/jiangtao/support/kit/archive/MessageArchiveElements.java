package tech.jiangtao.support.kit.archive;

import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.util.XmlStringBuilder;

/**
 * Class: MessageArchiveElements </br>
 * Description: 消息节点 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/12/2016 9:27 AM</br>
 * Update: 06/12/2016 9:27 AM </br>
 **/

public class MessageArchiveElements {

    public static class FromElement implements ExtensionElement {

        public String elementName;
        public String secs;
        public String with;
        public String body;
        public String thread;

        public FromElement(String elementName, String secs, String with, String body,
            String thread) {
            this.elementName = elementName;
            this.secs = secs;
            this.with = with;
            this.body = body;
            this.thread = thread;
        }

        public FromElement(String elementName, String secs, String body, String thread) {
            this.elementName = elementName;
            this.secs = secs;
            this.body = body;
            this.thread = thread;
        }

        public FromElement(String secs, String body, String thread) {
            this.secs = secs;
            this.body = body;
            this.thread = thread;
        }

        @Override
        public String getNamespace() {
            return null;
        }

        @Override
        public String getElementName() {
            return elementName;
        }

        @Override
        public CharSequence toXML() {
            XmlStringBuilder xml = new XmlStringBuilder();
            xml.halfOpenElement(this);
            xml.optAttribute("secs",secs);
            xml.optAttribute("with",with);
            xml.rightAngleBracket();
            xml.element("body",body);
            xml.element("thread",thread);
            xml.closeElement(this);
            return xml;
        }
    }

}
