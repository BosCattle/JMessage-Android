# 项目配置

## 项目引入



[![](https://jitpack.io/v/BosCattle/JMessage.svg)](https://jitpack.io/#BosCattle/JMessage)

### Gradle

```shell
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	dependencies {
  	        compile 'com.github.BosCattle.JMessage:PowerSupportUI:Tag'
  	}
```
### Maven
```shell
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
</repositories>
<dependency>
  	    <groupId>com.github.BosCattle.JMessage</groupId>
        <artifactId>PowerSupportUI</artifactId>
        <version>Tag</version>
 </dependency>
```

## Application配置

1. 自定义Application，并在`onCreate()`方法中调用

```java
SupportUI.initialize(getApplicationContext(), "resource.properties");
```

其中第二个参数表示你在`Assets`下的配置文件，此文件必须创建并且声明，其中包括了PORT，SERVICE_NAME，HOST，RESOURCE_ADDRESS，API_ADDRESS变量。项目初始化时，需要这些参数进行初始化。一个简化版的`resource.properties`.

```properties
MM_APPID = 6e7ea2251ca5479d875916785c4418f1 //表情包
MM_APPSECRET = 026eb8a2cb7b4ab18135a6a0454fd698 // 表情包
PORT = 5222 // socket 端口号
SERVICE_NAME = dc-a4b8eb92-xmpp.jiangtao.tech. // domin

# home
HOST2 = 192.168.3.4 // 主机地址
RESOURCE_ADDRESS2 = http://192.168.3.4:9090/resource/ //资源服务器地址
API_ADDRESS2 = http://192.168.3.4:9090/tigase/ // http请求地址
```

2. 在创建Application后需要给Application加上必要的注解

```java
@ChatRouter(router = ChatActivity.class)
@GroupChatRouter(router = GroupChatActivity.class)
@InvitedRouter(router = AllInvitedActivity.class)
public class PowerApp extends Application {
  @Override public void onCreate() {
    super.onCreate();
    SimpleHUD.backgroundHexColor = "#FF4081";
    SupportUI.initialize(getApplicationContext(), "resource.properties");
  }
}
```

```java
@ChatRouter(router = ChatActivity.class) // 表示单聊消息点击后进入的页面
```

```java
@GroupChatRouter(router = GroupChatActivity.class) // 表示群聊消息点击后进入的页面
```

```java
@InvitedRouter(router = AllInvitedActivity.class) // 表示点击好友和群聊消息点击后进入的页面
```

