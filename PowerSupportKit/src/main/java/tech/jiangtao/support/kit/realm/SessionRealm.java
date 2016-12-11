package tech.jiangtao.support.kit.realm;

import java.util.Date;
import org.jxmpp.jid.Jid;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SessionRealm extends RealmObject {

    @PrimaryKey
    public int id = Integer.parseInt(UUID.randomUUID().toString());
    public String mainJID;
    public String withJID;
    public Date lastDate;
    public String lastMessage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
