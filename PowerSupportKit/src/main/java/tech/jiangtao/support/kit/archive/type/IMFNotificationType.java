package tech.jiangtao.support.kit.archive.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum IMFNotificationType {
  //用户同意了你的请求
  SUBSCRIBED("subscribed"),
  // 用户不同意你的请求
  UNSUBSCRIBE("unsubscribe"),
  // 接收到好友邀请
  SUBSCRIBE("subscribe");
  private final String mValue;

  IMFNotificationType(String value) {
    mValue = value;
  }

  @Override @JsonValue public String toString() {
    return mValue;
  }

  private static final Map<String, IMFNotificationType> STRING_MAPPING = new HashMap<>();

  static {
    for (IMFNotificationType calorieType : IMFNotificationType.values()) {
      STRING_MAPPING.put(calorieType.toString().toUpperCase(), calorieType);
    }
  }

  @JsonCreator public static IMFNotificationType fromValue(String value) {
    return STRING_MAPPING.get(value.toUpperCase());
  }
}
