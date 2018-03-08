#Links
[spring官网](https://spring.io/projects) --> [spring-framework-reference](https://docs.spring.io/spring/docs/4.3.14.RELEASE/spring-framework-reference/htmlsingle/)参考其Resources模块  
[Spring加载properties文件的两种方式](http://blog.csdn.net/eson_15/article/details/51365707)  
[五种方式让你在java中读取properties文件内容不再是难题](https://www.cnblogs.com/hafiz/p/5876243.html)  
[org.springframework.core.io.support.PropertiesLoaderUtils类](http://outofmemory.cn/code-snippet/2770/Spring-usage-program-mode-duqu-properties-file)  
[Spring加载Properties配置文件的四种方式](http://blog.csdn.net/haha_sir/article/details/79105951)  

>[Spring中加载外部资源文件的几种方式](http://blog.csdn.net/a617332635/article/details/72236280)  
>[使用@Import和@ImportResource进行Spring Java config和xml的混合配置](http://blog.csdn.net/jiaobuchong/article/details/50530027)

##spring-framework中的Resource模块
[spring官网](https://spring.io/projects) --> [spring-framework-reference](https://docs.spring.io/spring/docs/4.3.14.RELEASE/spring-framework-reference/htmlsingle/)参考其Resources模块-->翻译[Spring 框架概述(三)之Resources](http://blog.csdn.net/xiangjai/article/details/53954252)
## Java工具类
工具类PropertyUtil的static静态代码块中读取properties文件内容保存在static属性中  

	import com.yishu.util.PropertyUtil
	
	/**
	 * Desc:properties文件获取工具类
	 */
	public class PropertyUtil {
	    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	    private static Properties props;
	    static{
	        loadProps();
	    }
	
	    synchronized static private void loadProps(){
	        logger.info("开始加载properties文件内容.......");
	        props = new Properties();
	        InputStream in = null;
	        try {
	　　　　　　　<!--第一种，通过类加载器进行获取properties文件流-->
	            in = PropertyUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
	　　　　　　  <!--第二种，通过类进行获取properties文件流-->
	            //in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
	            props.load(in);
	        } catch (FileNotFoundException e) {
	            logger.error("jdbc.properties文件未找到");
	        } catch (IOException e) {
	            logger.error("出现IOException");
	        } finally {
	            try {
	                if(null != in) {
	                    in.close();
	                }
	            } catch (IOException e) {
	                logger.error("jdbc.properties文件流关闭出现异常");
	            }
	        }
	        logger.info("加载properties文件内容完成...........");
	        logger.info("properties文件内容：" + props);
	    }
	
	    public static String getProperty(String key){
	        if(null == props) {
	            loadProps();
	        }
	        return props.getProperty(key);
	    }
	
	    public static String getProperty(String key, String defaultValue) {
	        if(null == props) {
	            loadProps();
	        }
	        return props.getProperty(key, defaultValue);
	    }
	}

## spring-framework
基于xml配置方式中的PropertyPlaceholderConfigurer类和这里基于注解方式的PropertiesFactoryBean类都是继承PropertiesLoaderSupport，都是用来加载properties配置文件的。  

+ org.springframework.beans.factory.config.***PropertyPlaceholderConfigurer***  

		<context:property-placeholder location="classpath:jdbc.properties" ignore-unresolvable="true"/>
上面的配置和下面配置等价，是对下面配置的简化

		<bean id="propertyConfigurer" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
		   <property name="ignoreUnresolvablePlaceholders" value="true"/>
		   <property name="locations">
		      <list>
		         <value>classpath:jdbc.properties</value>
		      </list>
		    </property>
		</bean>
读取配置属性: 通过将配置文件加载至spring上下文中，然后通过${}取得值，常用于bean的属性上.

	>用途1：Spring的xml配置文件中，可以通过${属性名}使用properties文件配置的值  
    用途2：可以使用@Value("${属性名}")注解读取properties文件配置的值，再给字段赋值  
	方法1：注解在字段上，给字段赋值  
	方法2：注解在字段的setter方法中赋值

+ org.springframework.beans.factory.config.***PropertyPlaceholderConfigurer子类***  
建立PropertyPlaceholderConfigurer子类:

		package com.yishu.util.PropertyConfigurer
		
		public class PropertyConfigurer extends PropertyPlaceholderConfigurer {
		
		    private Properties props;       // 存取properties配置文件key-value结果
		
		    @Override
		    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
		                            throws BeansException {
		        super.processProperties(beanFactoryToProcess, props);
		        this.props = props;
		    }
		
		    public String getProperty(String key){
		        return this.props.getProperty(key);
		    }
		
		    public String getProperty(String key, String defaultValue) {
		        return this.props.getProperty(key, defaultValue);
		    }
		
		    public Object setProperty(String key, String value) {
		        return this.props.setProperty(key, value);
		    }
		}
applicationContext.xml中配置该自定义类:

		<bean id="propertyConfigurer" class="com.yishu.util.PropertyConfigurer">
		   <property name="ignoreUnresolvablePlaceholders" value="true"/>
		   <property name="ignoreResourceNotFound" value="true"/>
		   <property name="locations">
		       <list>
		          <value>classpath:jdbc.properties</value>
		       </list>
		   </property>
		</bean>

+ org.springframework.beans.factory.config.***PropertiesFactoryBean***  
使用注解的方式注入，主要用在java代码中使用注解注入properties文件中相应的value值  

		<bean id="prop" class="org.springframework.beans.factory.config.PropertiesFactoryBean">
		   <!-- 这里是PropertiesFactoryBean类，它也有个locations属性，也是接收一个数组，跟上面一样 -->
		   <property name="locations">
		       <array>
		          <value>classpath:jdbc.properties</value>
		       </array>
		   </property>
		</bean>
读取配置属性: 通过SpEL表达式#{}获取bean的属性.  

	>用途：可以使用@Value("#{prop.属性名}")注解读取properties文件配置的值，再给字段赋值  
	方法1：注解在字段上，给字段赋值  
	方法2：注解在字段的setter方法中赋值  
	注意：@Value("#{prop.属性名}") 中的 prop 是 注册的PropertiesFactoryBean的 Bean ID

+ 使用***util:properties***标签进行暴露properties文件中的内容
applicationContext.xml头部schema和<util:properties>:  

		xmlns:util="http://www.springframework.org/schema/util"
		xsi:schemaLocation="http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-3.2.xsd"

		<util:properties id="propertiesReader" location="classpath:jdbc.properties"/>
**实际上<util:list>、<util:map>、<util:set>、<util:properties>等标签是spring用来取代ListFactoryBean、MapFactoryBean、SetFactoryBean、PropertiesFactoryBean的简单写法.**
所以util:properties是PropertiesFactoryBean读取配置的一种简写.

+ @PropertySource 注解实现配置文件加载  
[Spring中@PropertySouce注解的使用](http://blog.csdn.net/l153097889/article/details/52476219)  
[Spring的@PropertySource和@Value注解例子](http://blog.csdn.net/BalterNotz/article/details/53585888)  

##Spring4自定义@Value功能##
[Spring4自定义@Value功能](http://blog.csdn.net/mn960mn/article/details/77430685)