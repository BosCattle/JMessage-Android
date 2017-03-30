package tech.jiangtao.support.kit.realm;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

// TODO: 03/01/2017 会话结构表 
public class SessionRealm extends RealmObject {

    @PrimaryKey
    public String session_id;
    public String user_from;
    public String user_to;
    public String vcard_id;
    public String message_id;
    public int unReadCount;

    public SessionRealm(){}

    public SessionRealm(String session_id, String user_from, String user_to, String vcard_id,
        String message_id, int unReadCount) {
        this.session_id = session_id;
        this.user_from = user_from;
        this.user_to = user_to;
        this.vcard_id = vcard_id;
        this.message_id = message_id;
        this.unReadCount = unReadCount;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getUser_from() {
        return user_from;
    }

    public void setUser_from(String user_from) {
        this.user_from = user_from;
    }

    public String getUser_to() {
        return user_to;
    }

    public void setUser_to(String user_to) {
        this.user_to = user_to;
    }

    public String getVcard_id() {
        return vcard_id;
    }

    public void setVcard_id(String vcard_id) {
        this.vcard_id = vcard_id;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }
}
