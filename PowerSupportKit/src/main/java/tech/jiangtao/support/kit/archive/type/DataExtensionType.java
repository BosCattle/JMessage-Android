package tech.jiangtao.support.kit.archive.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum DataExtensionType {
  TEXT("text"),
  IMAGE("image"),
  AUDIO("audio"),
  VIDEO("video");
  private final String mValue;

  DataExtensionType(String value) {
    mValue = value;
  }

  @Override @JsonValue public String toString() {
    return mValue;
  }

  private static final Map<String, DataExtensionType> STRING_MAPPING = new HashMap<>();

  static {
    for (DataExtensionType calorieType : DataExtensionType.values()) {
      STRING_MAPPING.put(calorieType.toString().toUpperCase(), calorieType);
    }
  }

  @JsonCreator public static DataExtensionType fromValue(String value) {
    return STRING_MAPPING.get(value.toUpperCase());
  }
}
