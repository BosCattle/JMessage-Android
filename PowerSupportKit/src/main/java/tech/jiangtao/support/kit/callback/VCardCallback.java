package tech.jiangtao.support.kit.callback;

import org.jivesoftware.smackx.vcardtemp.packet.VCard;

/**
 * Class: VCardCallback </br>
 * Description: VCardCallback回调 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 04/12/2016 11:48 AM</br>
 * Update: 04/12/2016 11:48 AM </br>
 **/

public interface VCardCallback {

  void recieveVCard(VCard vCard,String userJid);

  void settingVCard(String message);

}
