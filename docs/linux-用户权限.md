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
	id mysql
	tree usr/local/redis -gp
[Linux 中将用户添加到组的指令](http://blog.csdn.net/u013078295/article/details/52485434)

---
Redis的用户权限管理

	groupadd -g 2001 redis
	useradd -u2001 -g2001 -credis redis

	chmod 755 /usr/local/redis/bin
	chown -R redis:redis /usr/local/redis

	usermod -s /bin/bash redis
	查看redis-server进程 ps aux|grep redis
	su redis -c systemctl restart redis-server
	再次查看redis-server进程 ps aux|grep redis
	usermod -s /sbin/nologin redis
	
MySQL的用户权限管理

	groupadd mysql
	useradd -g mysql mysql -s /sbin/nologin
	chmod 755 /apps/DB/mysql/scripts/mysql_install_db
	chown -R mysql.mysql /apps/DB/mysql/

#问题
一、useradd 与 adduser的区别

 添加用户

1. 在root权限下，useradd只是创建了一个用户名，如 （useradd  +用户名 ），它并没有在/home目录下创建同名文件夹，也没有创建密码，因此利用这个用户登录系统，是登录不了的，为了避免这样的情况出现，可以用 （useradd -m +用户名）的方式创建，它会在/home目录下创建同名文件夹，然后利用（ passwd + 用户名）为指定的用户名设置密码。

添加用户：useradd -m 用户名  然后设置密码  passwd 用户名



2. 可以直接利用adduser创建新用户（adduser +用户名）这样在/home目录下会自动创建同名文件夹（创建用户的家目录），会建立同名组，建立新用户密码，还会从/etc/SKEL目录下拷贝文件到家目录，完成初始化。是否加密主目录等等。

添加用户：adduser + 用户名  

删除用户

删除用户，“userdel 用户名”即可。最好将它留在系统上的文件也删除掉，可以使用“userdel -r 用户名”来实现。 

删除用户：userdel  -r  用户名 

二、/sbin/nologin 与 /bin/bash    更改用户是否可ssh登录

	usermod -s /sbin/nologin + 用户名
	usermod -s /bin/bash + 用户名  

nologin命令

用户和工作组管理 nologin命令可以实现礼貌地拒绝用户登录系统，同时给出信息。如果尝试以这类用户登录，就在log里添加记录，然后在终端输出This account is currently not available信息，就是这样。一般设置这样的帐号是给启动服务的账号所用的，这只是让服务启动起来，但是不能登录系统。  
语法 nologin 实例 Linux禁止用户登录： 禁止用户登录后，用户不能登录系统，但可以登录ftp、SAMBA等。  
我们在Linux下做系统维护的时候，希望个别用户或者所有用户不能登录系统，保证系统在维护期间正常运行。这个时候我们就要禁止用户登录。  
1、禁止个别用户登录，比如禁止lynn用户登录。  

	passwd -l lynn // 锁定lynn用户，这样该用户就不能登录了。 
	passwd -u lynn // 对锁定的用户lynn进行解锁，用户可登录了。
2、我们通过修改/etc/passwd文件中用户登录的shell vi /etc/passwd 更改为： lynn:x:500:500::/home/lynn:/sbin/nologin 该用户就无法登录了。  
3、禁止所有用户登录。

	touch /etc/nologin // 除root以外的用户不能登录了。