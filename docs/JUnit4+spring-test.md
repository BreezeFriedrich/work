#Links
+ 官网  
[Spring Framework Documentation (Version 5.0.4.RELEASE)](https://docs.spring.io/spring/docs/5.0.4.RELEASE/spring-framework-reference/)  
[Testing (v5.0.4)](https://docs.spring.io/spring/docs/5.0.4.RELEASE/spring-framework-reference/testing.html#testing)  
[Spring Framework Reference Documentation (v4.3.15)](https://docs.spring.io/spring/docs/4.3.15.BUILD-SNAPSHOT/spring-framework-reference/htmlsingle/)  
+ 其他  
[聊聊单元测试（三）——Spring Test+JUnit完美组合](http://blog.csdn.net/shan9liang/article/details/40452469)  
[Spring回顾之五 —— 测试，JUnit与SpringTest的完美结合](http://veiking.iteye.com/blog/2364788)  
[【第十三章】 测试 之 13.3 集成测试 ——跟我学spring3](http://sishuok.com/forum/blogPost/list/0/2557.html)  
[spring-test单元测试（三）-spring mvc请求测试](http://blog.csdn.net/wangxindong11/article/details/53319797)  

##注意
1.注意被测试对象在Spring中不能配置AOP切面代理，否则注入到TestCase时，会产生类型不匹配的异常。  
因为被代理后的类型发生了变化，注入到TestCase中时，与原始的类型有区别。

另外，运行TestCase时，可能需要加上两个jvm参数：
-Djavax.xml.parsers.DocumentBuilderFactory=com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl
-Djavax.xml.parsers.SAXParserFactory=com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl  

##用spring-test比只用JUnit4优点
+ 不用spring-test,只用JUnit4
+ JUnit4 + spring-test
