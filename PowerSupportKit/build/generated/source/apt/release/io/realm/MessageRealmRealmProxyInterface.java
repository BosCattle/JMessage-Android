package io.realm;


public interface MessageRealmRealmProxyInterface {
    public String realmGet$id();
    public void realmSet$id(String value);
    public String realmGet$mainJID();
    public void realmSet$mainJID(String value);
    public String realmGet$withJID();
    public void realmSet$withJID(String value);
    public String realmGet$textMessage();
    public void realmSet$textMessage(String value);
    public java.util.Date realmGet$time();
    public void realmSet$time(java.util.Date value);
    public String realmGet$thread();
    public void realmSet$thread(String value);
    public String realmGet$type();
    public void realmSet$type(String value);
    public String realmGet$messageType();
    public void realmSet$messageType(String value);
    public boolean realmGet$messageStatus();
    public void realmSet$messageStatus(boolean value);
}
