package tech.jiangtao.support.kit.archive.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

/**
 * Class: MessageType </br>
 * Description: 消息类型 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 03/01/2017 10:45 AM</br>
 * Update: 03/01/2017 10:45 AM </br>
 **/
// TODO: 03/01/2017 暂时不用
public enum MessageType {
  normal("normal"),
  chat("chat"),
  groupchat("groupchat"),
  headline("headline"),
  error("error");
  private final String mValue;

  MessageType(String value) {
    mValue = value;
  }

  @Override @JsonValue public String toString() {
    return mValue;
  }

  private static final Map<String, MessageExtensionType> STRING_MAPPING = new HashMap<>();

  static {
    for (MessageExtensionType calorieType : MessageExtensionType.values()) {
      STRING_MAPPING.put(calorieType.toString().toUpperCase(), calorieType);
    }
  }

  @JsonCreator public static MessageExtensionType fromValue(String value) {
    return STRING_MAPPING.get(value.toUpperCase());
  }
}
