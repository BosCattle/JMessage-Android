[![](https://jitpack.io/v/BosCattle/JMessage.svg)](https://jitpack.io/#BosCattle/JMessage)
## 介绍
Android版本聊天Library，基于XMPP开发，支持单聊，群聊。聊天类型支持文字，图片，表情，语音，视频，文件，地理位置。
## 文档
[集成文档](http://jmessage.jiangtao.tech/)
## 如何使用
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
