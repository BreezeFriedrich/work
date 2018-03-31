#与coding无关
##服务器
+ 服务器环境: Linux CentOS+jdk+tomcat(+MySQL+nginx+redis)
+ 域名及域名解析
+ 购买https证书,然后修改域名解析和配置tomcat 的https访问.
##公众帐号
+ 申请微信公众号 *(只有企业才能开通公众号)*
+ 微信公众号认证 *(每年都需要认证,￥300+企业信息+企业对公帐户)*
+ 登录微信公众平台,设置(回复、菜单、公众号开发信息、服务器配置、)

#coding
##获取公众号各种接口权限
回复、菜单、扫一扫、微信支付、商家Wi-Fi...
##openid & UID

#lockWechat
##流程
###1.登录
###2.开锁授权密码
struts.xml、struts-unlock.xml、UnlockAction、AccountAction、AuthpasswordInterceptor、 phoneInterceptor
>
	入口:UnlockAction(authById、prohibitAuthById...)——>authpasswordStack.authpasswordInterceptor——>session中是否有authpassword——>有,actionInvocation.invoke()  
					——>无,根据ownerPhoneNumber获取数据库中authpassword——>获取到授权码——>proofAuthpassword.jsp  
																	——>未设置手势密码——>set_authPassword.jsp  
																	——>获取不到授权码——>error.jsp  