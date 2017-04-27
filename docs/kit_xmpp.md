# Kit配置

## 登录


```java

private SimpleLogin mSimpleLogin;
mSimpleLogin = new SimpleLogin();
mSimpleLogin.startLogin(new LoginParam(mLoginUsername.getText().toString(),
            mLoginPassword.getText().toString()), this);

```

## 发送好友请求


```java



```


## 处理好友请求

```java


```




## 发送单聊消息

```java

  /**
   * 发送消息到对方，并且添加到本地
   */
  public void sendMyFriendMessage(String message, DataExtensionType type,MessageExtensionType messageExtensionType) {
    TextMessage message1 =
        new TextMessage(org.jivesoftware.smack.packet.Message.Type.chat, mContactRealm.getUserId(),
            message, type,messageExtensionType);
    message1.messageType = type;
    HermesEventBus.getDefault().postSticky(message1);
    //将消息更新到本地
    mChatInput.setText("");
    hideKeyBoard();
  }

```


## 发送群聊消息

```java

  /**
   * 发送消息到对方，并且添加到本地
   */
  public void sendMyFriendMessage(String message, DataExtensionType type) {
    TextMessage message1 =
        new TextMessage(org.jivesoftware.smack.packet.Message.Type.chat, mGroup.getGroupId(),
            message, type, MessageExtensionType.GROUP_CHAT);
    message1.messageType = type;
    HermesEventBus.getDefault().postSticky(message1);
    //将消息更新到本地
    mChatInput.setText("");
    hideKeyBoard();
  }

```

## 接收消息

```java

@Subscribe(threadMode = ThreadMode.MAIN) public void onMessage(RecieveLastMessage message) {

}
```