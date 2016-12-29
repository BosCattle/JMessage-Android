package tech.jiangtao.support.kit.archive.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum MessageExtensionType {
  TEXT("text"),
  IMAGE("image"),
  AUDIO("audio"),
  VIDEO("video");
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
