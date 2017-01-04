## 文档
[集成文档](http://blog.jiangtao.tech/2016/12/23/SupportChat%E6%96%87%E6%A1%A3/)
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
  	        compile 'com.github.BosCattle:JMessage:V0.0.1'
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
  	    <groupId>com.github.BosCattle</groupId>
  	    <artifactId>JMessage</artifactId>
  	    <version>V0.0.1</version>
 </dependency>
```
