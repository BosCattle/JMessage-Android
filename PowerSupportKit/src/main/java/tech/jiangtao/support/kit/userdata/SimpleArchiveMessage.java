package tech.jiangtao.support.kit.userdata;

import android.util.Log;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.Date;
import tech.jiangtao.support.kit.realm.MessageRealm;
import tech.jiangtao.support.kit.service.SupportService;
import tech.jiangtao.support.kit.util.DateUtils;
import tech.jiangtao.support.kit.util.StringSplitUtil;

/**
 * Class: SimpleArchiveMessage </br>
 * Description: 归档消息 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 05/12/2016 12:55 AM</br>
 * Update: 05/12/2016 12:55 AM </br>
 **/

public class SimpleArchiveMessage {

  public static final String TAG = SimpleArchiveMessage.class.getSimpleName();
  private Realm mRealm;

  public SimpleArchiveMessage() {
    mRealm = Realm.getDefaultInstance();
  }

  public RealmResults<MessageRealm> loadArchiveMessage(String userJid) {
    Log.d(TAG, "loadArchiveMessage: "+StringSplitUtil.splitDivider(SupportService.getmXMPPConnection().getUser()));
    Log.d(TAG, "loadArchiveMessage: "+StringSplitUtil.splitDivider(userJid));
    return mRealm.where(MessageRealm.class)
        .equalTo("mainJID", StringSplitUtil.splitDivider(SupportService.getmXMPPConnection().getUser()))
        .equalTo("withJID", StringSplitUtil.splitDivider(userJid))
        .or()
        .equalTo("mainJID", StringSplitUtil.splitDivider(userJid))
        .equalTo("withJID", StringSplitUtil.splitDivider(SupportService.getmXMPPConnection().getUser()))
        .findAll();
  }

  public String getLastUpdateTime(){
    Date date = mRealm.where(MessageRealm.class).maximumDate("time");
    if (date==null) {
    return null;
    }
    return DateUtils.getDefaultUTCTimeZone(date.getTime()/1000);
  }

  public void persistMessage(){

  }
}
