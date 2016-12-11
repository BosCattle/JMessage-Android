package tech.jiangtao.support.kit.archive;

import android.util.Log;
import java.io.IOException;
import java.util.HashSet;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import tech.jiangtao.support.kit.archive.type.MessageBodyType;

/**
 * Class: MessageArchiveIQProvider </br>
 * Description:  </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 05/12/2016 11:52 PM</br>
 * Update: 05/12/2016 11:52 PM </br>
 **/

public class MessageArchiveIQProvider extends IQProvider<MessageArchiveAnswerIQ> {

  public static final String TAG = MessageArchiveIQProvider.class.getSimpleName();

  @Override public MessageArchiveAnswerIQ parse(XmlPullParser parser, int initialDepth)
      throws XmlPullParserException, IOException, SmackException {
    //将xml解析为MessageArchiveIQ，具体的model类
    Log.e(TAG, "parse: " + parser.getName());
    MessageArchiveAnswerIQ mAAIQ = new MessageArchiveAnswerIQ(null);
    mAAIQ.messageBody = new HashSet<>();
    Log.e(TAG, "parse: " + parser.getAttributeValue(null, "with"));
    Log.e(TAG, "parse: " + parser.getAttributeValue(null, "start"));
    mAAIQ.setWithJid(parser.getAttributeValue(null, "with"));
    mAAIQ.setTime(parser.getAttributeValue(null, "start"));
    boolean done = false;
    while (!done) {
      int eventType = parser.next();
      if (eventType == XmlPullParser.START_TAG) {
        Log.e(TAG, "parse: 进入START_TAG");
        Log.e(TAG, "parse: " + parser.getName());
        if (parser.getName().equals("from")) {
          String messageFrom = "";
          String threadFrom = "";
          String with = parser.getAttributeValue(null,"with");
          String secs = parser.getAttributeValue(null, "secs");
          parser.next();
          if (parser.getName().equals("body")) {
            messageFrom = parser.nextText();
          }
          parser.next();
          if (parser.getName().equals("thread")) {
            threadFrom = parser.nextText();
          }
          MessageBody body =
              new MessageBody(MessageBodyType.TYPE_FROM, secs,
                  with,messageFrom, threadFrom);
          mAAIQ.messageBody.add(body);
        } else if (parser.getName().equals("to")) {
          String message = "";
          String thread = "";
          String secs = parser.getAttributeValue(null, "secs");
          String with = parser.getAttributeValue(null,"with");
          parser.next();
          if (parser.getName().equals("body")) {
            message = parser.nextText();
          }
          parser.next();
          if (parser.getName().equals("thread")) {
            thread = parser.nextText();
          }
          MessageBody body = new MessageBody(MessageBodyType.TYPE_TO, secs,with, message, thread);
          mAAIQ.messageBody.add(body);
        }
      } else if (eventType == XmlPullParser.END_TAG) {
        if (parser.getName().equals("chat")) {
          done = true;
        }
      } else {
        for (int i = 0; i < parser.getAttributeCount(); i++) {
          Log.e(TAG, "parse3: " + parser.getAttributeName(i));
        }
      }
    }
    Log.e(TAG, "parse finish: "+mAAIQ.toString());
    return mAAIQ;
  }
}
