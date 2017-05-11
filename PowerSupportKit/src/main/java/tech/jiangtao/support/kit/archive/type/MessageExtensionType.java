package tech.jiangtao.support.kit.archive.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

/**
 * Class: MessageExtensionType </br>
 * Description: 消息拓展，根据单聊来拓展群聊 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 15/04/2017 7:33 PM</br>
 * Update: 15/04/2017 7:33 PM </br>
 **/

public enum MessageExtensionType {

  CHAT("chat"),
  GROUP_CHAT("groupChat"),
  PUSH("push");
  private final String mValue;

  MessageExtensionType(String value) {
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
