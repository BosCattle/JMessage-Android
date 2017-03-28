package tech.jiangtao.support.kit.archive;

import android.os.Parcelable;
import java.util.Set;

import org.jivesoftware.smack.packet.Element;
import org.jivesoftware.smack.packet.IQ;

import tech.jiangtao.support.kit.archive.type.MessageBodyType;

/**
 * Class: MessageArchiveAnswerIQ </br>
 * Description: 归档消息应答 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 06/12/2016 9:27 AM</br>
 * Update: 06/12/2016 9:27 AM </br>
 **/

public class MessageArchiveAnswerIQ extends IQ {

    public String time;
    private String withJid;
    Set<MessageBody> messageBody;
    public int first;
    private int last;
    public int count;

    public String getWithJid() {
        return withJid;
    }

    public void setWithJid(String withJid) {
        this.withJid = withJid;
    }


    protected MessageArchiveAnswerIQ(String childElementName) {
        super("chat", "urn:xmpp:archive");
    }

    public MessageArchiveAnswerIQ(String childElementName, String childElementNamespace) {
        super("chat", "urn:xmpp:archive");

    }

    @Override
    protected IQChildElementXmlStringBuilder getIQChildElementBuilder(
            IQChildElementXmlStringBuilder xml) {
        xml.optAttribute("with", getWithJid());
        xml.optAttribute("start", getTime());
        xml.rightAngleBracket();
        for (MessageBody messageBody : getMessageBody()) {
            if (messageBody.getType() == MessageBodyType.TYPE_FROM) {
                xml.element(new MessageArchiveElements.FromElement("from", messageBody.getSecs(),
                        messageBody.getWith(),messageBody.getBody(), messageBody.getThread()));
            }else if (messageBody.getType()==MessageBodyType.TYPE_TO){
                xml.element(new MessageArchiveElements.FromElement("to", messageBody.getSecs(),
                        messageBody.getWith(),messageBody.getBody(), messageBody.getThread()));
            }else {
            }
        }
        return xml;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Set<MessageBody> getMessageBody() {
        return messageBody;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setMessageBody(Set<MessageBody> messageBody) {
        this.messageBody = messageBody;
    }
}
