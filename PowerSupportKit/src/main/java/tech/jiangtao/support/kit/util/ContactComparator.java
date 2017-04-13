package tech.jiangtao.support.kit.util;

import java.util.Comparator;
import tech.jiangtao.support.kit.realm.ContactRealm;

/**
 * Class: ContactComparator </br>
 * Description:  </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 13/04/2017 8:31 PM</br>
 * Update: 13/04/2017 8:31 PM </br>
 **/

public class ContactComparator implements Comparator<ContactRealm> {

  @Override public int compare(ContactRealm contactRealm1, ContactRealm contactRealm2) {
    String a = PinYinUtils.getPinyinFirstLetter(PinYinUtils.ccs2Pinyin(
        PinYinUtils.getPinyinFirstLetter(PinYinUtils.ccs2Pinyin(contactRealm1.getNickName()))));
    String b = PinYinUtils.getPinyinFirstLetter(PinYinUtils.ccs2Pinyin(
        PinYinUtils.getPinyinFirstLetter(PinYinUtils.ccs2Pinyin(contactRealm2.getNickName()))));
    return a.compareTo(b);
  }
}
