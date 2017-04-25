package tech.jiangtao.support.kit.model.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

/**
 * Class: TransportType </br>
 * Description: 文件传输枚举 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 12/04/2017 12:10 AM</br>
 * Update: 12/04/2017 12:10 AM </br>
 **/

public enum TransportType {

  IMAGE("image"), AUDIO("audio"), VIDEO("video"), AVATAR("avatar"), FILE("file");
  private final String mValue;

  TransportType(String value) {
    mValue = value;
  }

  @Override @JsonValue public String toString() {
    return mValue;
  }

  private static final Map<String, TransportType> STRING_MAPPING = new HashMap<>();

  static {
    for (TransportType calorieType : TransportType.values()) {
      STRING_MAPPING.put(calorieType.toString().toUpperCase(), calorieType);
    }
  }

  @JsonCreator public static TransportType fromValue(String value) {
    return STRING_MAPPING.get(value.toUpperCase());
  }
}
