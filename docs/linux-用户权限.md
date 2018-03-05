#Links
[Linux基础知识之用户和用户组以及 Linux 权限管理](https://www.linuxidc.com/Linux/2016-10/136251.htm)
[【linux相识相知】用户及权限管理](https://www.cnblogs.com/liubinsh/p/7148274.html)

+ 查看

		cat /etc/passwd //用户：密码：UID：GID：注释：home dir：shell
		cat /etc/shadow //用户：加密后的密码：最近修改密码的日期：密码最短使用天数：最长使用天数：密码到期前告警时间：密码过期恕期时间：账号失效时间：保留位
		cat /etc/group
		cat /etc/gshadow
+ 组

		groupadd [options] Group   
options: -g GID //默认是上一个组的GID+1;-r 创建系统组

		groupmod [options] Group //修改组信息
options: -g GID //修改GID;-n new_name:修改组名

		groupdel [options] Group
例: groupdel guest //删除guest组

	- 组密码

			gpasswd [options] GROUP
options: -a USER //向组中添加用户  
-d USER //从组中移除用户  
例：gpasswd -a user usergroup //将user添加到组usergroup  
	- 临时切换基本组

			newgrp [-] [group]
-：会模拟用户重新登录以实现初始化其工作环境,返回以前的状态，是用exit退出  
例：newgrp - guestgroup //切换为guestgroup组
+ user
	- 显示创建用户该命令的默认配置

			useradd -D
	- 创建用户

			useradd [options] USER
options: -u //UID, -g //GID, -G //附加组, -c //注释, -d //home dir, -s //默认shell, -r //创建系统用户
	- 修改用户的属性

			usermod [options] USER //修改用户的属性
options: -u, -g, -G, -a, -c, -d, -m, -l, -s, -L, -U
	- 删除用户和其相关文件

			userdel [options] USER
options: -r //删除用户时一并删除其home dir,默认不删除
	- 修改用户的认证信息

			passwd [-k] [-l] [-u [-f]] [-d] [-e] [-n mindays] [-x maxdays] [-w warndays] [-i inactivedays] [-S] [--stdin] [username]
-l:锁定
-u:解锁
-d:清除用户密码
-e:帐户失效时间  
例: passwd //修改自己的密码  
passwd USER //修改指定USER的密码(root才可以)  
+ 添加用户到某一个组
	- 可以使用

			usermod -G group_name user_name
这个命令可以添加一个用户到指定的组，但是以前添加的组就会清空掉。
	- 所以想要添加一个用户到一个组，同时保留以前添加的组时，请使用gpasswd这个命令来添加操作用户：

			gpasswd -a user_name group_name

+ 权限chmod

		chmod [option] MODE[,MODE]... / OCTAL-MODE / --reference=RFILE FILE
options: u //文件所属 ,g //组 ,o //其他人 ,a 所有 
	1. 直接将rwx直接赋值给相应的用户类型,比如：u=rwx,g=x,o= ,空代表该位没有权限  
例：

			chmod u=rwx,g=rw,o= /tmp/read/read.txt
	- 直接操作一类用户的一个权限位  
例：

			chmod u+x /tmp/read/read.txt // +赋权 -夺权
	- 直接指定八进制  
例：

			chmod 777 /tmp/read/read.txt
	- 参考其他文件的权限
例：

			chmod --reference=/etc/passwd  /tmp/read/read.txt

+ chown  
管理员可以修改文件的属主和属组  

		chown [OPTION] [OWNER][:[GROUP]] FILE...
		chown [OPTION] --reference=RFILE FILE...
例：

		chown guest:guest ./read.txt
选项 -R：递归修改子目录和文件

#练习

	cat /etc/passwd
	cat /etc/shadow
	cat /etc/group
	cat /etc/gshadow

---
	groupadd -g 2000 app-redis
	groupmod -g 2001 -n redis app-redis
	groupadd -g2002 mysql
	groupdel mysql
	groupadd -g2002 mysql

---
	useradd -D
	useradd -u2001 -g2001 -credis redis
	useradd -u2002 -g2002 -cmysql mysql
	usermod -g2001 -cdatabase mysql
	usermod -g2002 -cmysql mysql
	userdel -r mysql
	useradd -u2002 -g2002 -cmysql mysql

---
	touch mysql.txt
	ll
	chmod g+rwx mysql.txt
	chmod u=rwx,g=rw,o= mysql.txt
	chmod 754 mysql.txt

---
	chown :mysql mysql.txt
	chown mysql mysql.txt
	chown root:root mysql.txt
	chown mysql:mysql mysql.txt

---
	passwd -l mysql
	passwd -u mysql
	passwd mysql --> 输入密码2次
	passwd -d mysql

---
	grep mysql /etc/passwd
	id
[Linux 中将用户添加到组的指令](http://blog.csdn.net/u013078295/article/details/52485434)