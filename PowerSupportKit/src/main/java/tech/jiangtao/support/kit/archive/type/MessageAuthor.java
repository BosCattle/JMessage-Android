package tech.jiangtao.support.kit.archive.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum MessageAuthor {
  OWN("own"),
  FRIEND("friend"),
  SERVER("server");
  private final String mValue;

  MessageAuthor(String value) {
    mValue = value;
  }

  @Override @JsonValue public String toString() {
    return mValue;
  }

  private static final Map<String, MessageAuthor> STRING_MAPPING = new HashMap<>();

  static {
    for (MessageAuthor calorieType : MessageAuthor.values()) {
      STRING_MAPPING.put(calorieType.toString().toUpperCase(), calorieType);
    }
  }

  @JsonCreator public static MessageAuthor fromValue(String value) {
    return STRING_MAPPING.get(value.toUpperCase());
  }
}
