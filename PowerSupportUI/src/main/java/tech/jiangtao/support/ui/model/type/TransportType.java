package tech.jiangtao.support.ui.model.type;

/**
 * Class: TransportType </br>
 * Description: 文件传输枚举 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 12/04/2017 12:10 AM</br>
 * Update: 12/04/2017 12:10 AM </br>
 **/

public enum TransportType {

  IMAGE("image"),
  AUDIO("audio"),
  VEDIO("video"),
  AVATAR("avatar"),
  FILE("file");
  private final String text;

  /**
   * @param text
   */
  private TransportType(final String text) {
    this.text = text;
  }

  public static TransportType toTransportType(String textParam) {
    return TransportType.valueOf(textParam);
  }
}
