[maven自动部署到远程tomcat教程](https://www.cnblogs.com/xyb930826/p/5725340.html)

1.设置tomcat manager

tomcat/conf/tomcat-users.xml

	<role rolename="admin-gui"/>
	<role rolename="manager-gui"/>
	<role rolename="manager-script"/>
	<user username="manager" password="XXXXXX" roles="manager-gui,manager-script"/>
	<user username="root" password="YYYYYY" roles="admin-gui,manager-gui,manager-script"/>
		
tomcat/conf/Catalina/localhost/manager.xml

	<?xml version="1.0" encoding="UTF-8"?>
	<Context privileged="true" antiResourceLocking="false" docBase="${catalina.home}/webapps/manager">
		<Valve className="org.apache.catalina.valves.RemoteAddrValve" allow="^.*$" />
	</Context>
		
2.给maven配置server信息(username&password)  
两种:1).maven/config/settings.xml <servers>里添加(与tomcat的manager-script一致):  

	<server>
		<id>tomcat-local</id>
	    <username>manager</username>
	    <password>XXXXXX</password>
	</server>
	<server>
		<id>tomcat-43.254.149.67</id>
		<username>manager</username>
		<password>XXXXXX</password>
	</server>

然后maven配置中tomcat7-maven-plugin中引用server id即可.  

	<plugin>
		<groupId>org.apache.tomcat.maven</groupId>
		<artifactId>tomcat7-maven-plugin</artifactId>
		<version>2.2</version>
		<configuration>
			<finalName>lockWechat</finalName>
			<uriEncoding>UTF-8</uriEncoding>
			<url>${deploy.url}/manager/text</url>
			<port>80</port>
			<path>/lockWechat</path>
			<server>tomcat-43.254.149.67</server><!--在maven目录conf/setting.xml标签servers中定义,默认引用其username和password-->
			<update>true</update>
		</configuration>
	</plugin>
	
2).maven配置中tomcat7-maven-plugin中配置服务器tomcat mannager-gui的username&password.  

	<plugin>
		<groupId>org.apache.tomcat.maven</groupId>
		<artifactId>tomcat7-maven-plugin</artifactId>
		<version>2.2</version>
		<configuration>
			<finalName>lockWechat</finalName>
			<uriEncoding>UTF-8</uriEncoding>
			<url>${deploy.url}/manager/text</url>
			<port>80</port>
			<path>/lockWechat</path>
			<username>admin</username>
			<password>XXXXXX</password>
			<update>true</update>
		</configuration>
	</plugin>
		
3.重启tomcat,使配置生效.  
打开 例如 http://127.0.0.1:80/manager 访问tomcat manager gui ,输入用户名和密码(-u root -p YYYYYY与manager-gui相同),可以验证tomcat-user.xml配置.  

4.在项目pom.xml同级目录下,输入mvn 命令;如  

	mvn tomcat7:run ; mvn tomcat7:shutdown  
	mvn tomcat7:deploy;mvn tomcat:undeploy;mvn tomcat7:redeploy

5.https  
对于https访问远程tomcat443端口,例如执行mvn tomcat7:deploy,会报错误:
>sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target

参:[彻底解决unable to find valid certification path to requested target](http://blog.csdn.net/frankcheng5143/article/details/52164939)  
[Could not connect to SMTP host,PKIX path building failed,unable to find valid certification path...](http://blog.csdn.net/frankcheng5143/article/details/52022289)  
[ 解决PKIX问题：unable to find valid certification path to requested target](http://blog.csdn.net/jadyer/article/details/7799540?utm_source=tuicool&utm_medium=referral)  
[JDK自带工具keytool生成ssl证书](https://www.cnblogs.com/zhangzb/p/5200418.html)  
[将安全证书导入到java的cacerts证书库](http://www.mamicode.com/info-detail-99920.html)  
[stackoverflow: SSL Configuration on Maven Tomcat Plugin](https://stackoverflow.com/questions/11633486/ssl-configuration-on-maven-tomcat-plugin)-->
[pom.xml](https://subversion.assembla.com/svn/freshcode_public/learn/tomcat-maven-plugin/pom.xml)  
[解决 PKIX：unable to find valid certification path to requested target](http://bijian1013.iteye.com/blog/2310856)  
[密钥、证书生成和管理总结](https://www.cnblogs.com/pixy/p/4722381.html)  

解决办法:把远程网站的公钥导入本地系统注册过的jre中.

+ 下载lockwx.manxing.1793.com.cer文件

+ 导入到

		keytool -import -alias lockwx.manxing1798.com -file lockwx.manxing1798.com.cer
		
		keytool -delete -alias lockwx.manxing1798.com
		keytool -genkeypair -alias "lockwx.manxing1798.com" -keyalg "RSA" -keystore "lockwx.manxing1798.com.keystore"
		keytool -genkey -alias lockwx.manxing1798.com -keyalg RSA -keysize 2018 -dname "cn=www.yishutech.com,o=yishutech.com,c=cn" -keypass yishutech -keystore C:/Program Files/Java/jre7/lib/security/lockwx.manxing1798.com.keystore -storepass yishutech -validity 365

		keytool -genkey -alias lockwx.manxing1798.com -keyalg RSA -keysize 2018 -dname "cn=www.yishutech.com,o=yishutech.com,c=cn" -keypass yishutech -keystore lockwx.manxing1798.com.keystore -storepass yishutech -validity 365
		keytool -import -alias manxing1798 -file lockwx.manxing1798.com.cer -keystore lockwx.manxing1798.com.keystore -storepass yishutech
		keytool -list -keystore lockwx.manxing1798.com.keystore -alias manxing1798
		keytool -delete -alias lockwx.manxing1798.com -keystore lockwx.manxing1798.com.keystore
		keytool -list -keystore lockwx.manxing1798.com.keystore
