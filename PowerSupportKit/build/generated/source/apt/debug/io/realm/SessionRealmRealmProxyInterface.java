package io.realm;


public interface SessionRealmRealmProxyInterface {
    public String realmGet$session_id();
    public void realmSet$session_id(String value);
    public String realmGet$user_from();
    public void realmSet$user_from(String value);
    public String realmGet$user_to();
    public void realmSet$user_to(String value);
    public String realmGet$vcard_id();
    public void realmSet$vcard_id(String value);
    public String realmGet$message_id();
    public void realmSet$message_id(String value);
    public int realmGet$unReadCount();
    public void realmSet$unReadCount(int value);
}
