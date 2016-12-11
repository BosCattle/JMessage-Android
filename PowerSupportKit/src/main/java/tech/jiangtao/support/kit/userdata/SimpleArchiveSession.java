package tech.jiangtao.support.kit.userdata;

import io.realm.Realm;
import tech.jiangtao.support.kit.realm.SessionRealm;

public class SimpleArchiveSession {

    public Realm mRealm;

  /**
   * 注意线程统一
   */
  public SimpleArchiveSession(){
      mRealm = Realm.getDefaultInstance();
    }

  public void createSession(String jid){
    SessionRealm sessionRealm = new SessionRealm();
  }
}
