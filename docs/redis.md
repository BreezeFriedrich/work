#Links
+ [redis & memcached区别剖析](https://www.biaodianfu.com/redis-vs-memcached.html)
+ redis 官网
>[下载与simple start](https://redis.io/download)  
>[redis命令](http://try.redis.io/)  
>[Redis data types and abstractions](https://redis.io/topics/data-types-intro)

+ [Spring Data Redis 官网](http://projects.spring.io/spring-data-redis/)
>- [2.1.0.M1/reference](https://docs.spring.io/spring-data/redis/docs/2.1.0.M1/reference/html/#get-started)  
- [2.1.0.M1/api](https://docs.spring.io/spring-data/redis/docs/2.1.0.M1/api/)

+ Spring 整合 Redis

>[redis整合spring(redisTemplate工具类)](http://blog.csdn.net/qq_34021712/article/details/75949706)  
>[spring集成redis](https://www.cnblogs.com/hello-daocaoren/p/7891907.html)  

>[java之redis篇(spring-data-redis整合)](https://www.cnblogs.com/tankaixiong/p/3660075.html)  
>[Spring整合Redis(第一篇)—SDR简述](https://www.cnblogs.com/zjrodger/p/5800593.html)  
>[Spring集成Redis步骤](http://blog.csdn.net/jrn1012/article/details/70344776)  
>[使用Spring Data Redis操作Redis（一） 很全面](http://blog.csdn.net/albertfly/article/details/51494080)  
>[redis和spring集成（注解实现，方便，快捷）](http://blog.csdn.net/fighterandknight/article/details/53432276/)  
>[JEDIS](http://blog.csdn.net/legend_x/article/details/12317087)  
>[Springboot系列redis使用之1](https://www.2cto.com/kf/201702/595093.html)  

+ [Linux下Redis的安装和部署](https://www.cnblogs.com/wangchunniu1314/p/6339416.html)

#Installation
一. Windows环境  
[微软维护github下载地址](https://github.com/MicrosoftArchive/redis/releases)  

二. linux环境

	[root@izbp1d9xxma2xabonszbi2z local]# hostnamectl
>
	 Static hostname: izbp1d9xxma2xabonszbi2z
	 Pretty hostname: iZbp1d9xxma2xabonszbi2Z
	       Icon name: computer-vm
	         Chassis: vm
	      Machine ID: 963c2c41b08343f7b063dddac6b2e486
	         Boot ID: f3566542440d4d24bf181008f161083d
	  Virtualization: kvm
	Operating System: CentOS Linux 7 (Core)
	CPE OS Name: cpe:/o:centos:centos:7
	          Kernel: Linux 3.10.0-514.26.2.el7.x86_64
	    Architecture: x86-64

改个主机名

	[root@izbp1d9xxma2xabonszbi2z local]# hostnamectl set-hostname geyser
1. 下载安装

	$ wget http://download.redis.io/releases/redis-4.0.8.tar.gz  
	$ tar xzf redis-4.0.8.tar.gz  
	$ cd redis-4.0.8  
	$ make  
...compile->src directory  

2. Run Redis with:

    $ src/redis-server
interact with Redis using the built-in client:

	$ src/redis-cli
	redis> set foo bar
	OK
	redis> get foo
	"bar"
3. /home/admin/install/redis-4.0.8

	mv /home/admin/install/redis-4.0.8/redis.conf /usr/local/redis/etc
	mv /home/admin/install/redis-4.0.8/src/redis-benchmark /usr/local/redis/bin
	cd /home/admin/install/redis-4.0.8/src
	cp -i mkreleasehdr.sh redis-benchmark redis-check-aof redis-server redis-cli /usr/local/redis/bin
	ps -ef|grep redis
	netstat -tunpl|grep redis
	/usr/local/redis/bin/redis-server
	netstat -tunpl|grep 6379
	/usr/local/redis/bin/redis-cli
4. 将Redis的命令所在目录添加到系统参数PATH中

	vi /etc/profile
末尾添加: export PATH="$PATH:/usr/local/redis/bin"

	source /etc/profile
5. 后台启动redis服务

	vi /usr/local/redis/etc/redis.conf
编辑redis.conf文件:

+ 将daemonize属性改为yes（表明需要在后台运行）  
+ 修改pid文件路径，此处可选，使用默认路径也可以。  
pidfile /usr/local/redis/redis.pid
+ 取消保护模式，此处可选。取消了便不要求使用密码验证了。  
protected-mode no
+ 配置日志文件路径  
logfile "/usr/local/redis/logs/redis.log"  

b)再次启动redis服务，并指定启动服务配置文件

	redis-server /usr/local/redis/etc/redis.conf
关闭redis-server进程:

	pkill redis-server
6. 将redis-server注册为系统服务  

1).centos6环境下

	vim /etc/init.d/redis

文件/etc/init.d/redis: 
	
	#!/bin/sh
	#
	# redis        Startup script for Redis Server
	#
	# chkconfig: - 80 12
	# description: Redis is an open source, advanced key-value store.
	#
	# processname: redis-server
	# config: /etc/redis.conf
	# pidfile: /var/run/redis.pid
	source /etc/init.d/functions
	BIN="/usr/local/redis/bin"
	CONFIG="/usr/local/redis/redis.conf"
	PIDFILE="/var/run/redis.pid"
	### Read configuration
	[ -r "$SYSCONFIG" ] && source "$SYSCONFIG"
	RETVAL=0
	prog="redis-server"
	desc="Redis Server"
	start() {
	        if [ -e $PIDFILE ];then
	             echo "$desc already running...."
	             exit 1
	        fi
	        echo -n $"Starting $desc: "
	        daemon $BIN/$prog $CONFIG
	        RETVAL=$?
	        echo
	        [ $RETVAL -eq 0 ] && touch /var/lock/subsys/$prog
	        return $RETVAL
	}
	stop() {
	        echo -n $"Stop $desc: "
	        killproc $prog
	        RETVAL=$?
	        echo
	        [ $RETVAL -eq 0 ] && rm -f /var/lock/subsys/$prog $PIDFILE
	        return $RETVAL
	}
	restart() {
	        stop
	        start
	}
	case "$1" in
	  start)
	        start
	        ;;
	  stop)
	        stop
	        ;;
	  restart)
	        restart
	        ;;
	  condrestart)
	        [ -e /var/lock/subsys/$prog ] && restart
	        RETVAL=$?
	        ;;
	  status)
	        status $prog
	        RETVAL=$?
	        ;;
	   *)
	        echo $"Usage: $0 {start|stop|restart|condrestart|status}"
	        RETVAL=1
	esac
	exit $RETVAL

命令

	chmod +x /etc/init.d/redis
	service redis start
会报错:
>Starting redis (via systemctl):  Job for redis.service failed because the control process exited with error code. See "systemctl status redis.service" and "journalctl -xe" for details.

解决:有三种方式修改内核参数，但要有root权限：  
1. 编辑/etc/sysctl.conf ，改vm.overcommit_memory=1，然后sysctl -p 使配置文件生效  
2. sysctl vm.overcommit_memory=1  
3. echo 1 > /proc/sys/vm/overcommit_memory

	vim /etc/sysctl.conf
vm.overcommit_memory = 1

	sysctl -p
	service redis stop
注册系统服务

	chkconfig --add redis
2).centos7 注册系统服务

	vim /usr/lib/systemd/system/redis-server.service
文件redis-server.service :

	[Unit]
	Description=The redis-server Process Manager
	After=syslog.target network.target remote-fs.target nss-lookup.target
	
	[Service]
	Type=forking
	PIDFile=/var/run/redis.pid
	ExecStart=/usr/local/redis/bin/redis-server /usr/local/redis/etc/redis.conf
	ExecReload=/bin/kill -s HUP $MAINPID
	ExecStop=/bin/kill -s QUIT $MAINPID
	PrivateTmp=true
	
	[Install]
	WantedBy=multi-user.target

以上redis-server.service文件内容最终被修改为

	[Unit]
	Description=The redis-server Process Manager
	After=syslog.target network.target
	
	[Service]
	Type=forking
	PIDFile=/var/run/redis.pid
	ExecStart=/usr/local/redis/bin/redis-server /usr/local/redis/etc/redis.conf
	ExecReload=/bin/kill -s HUP $MAINPID 
	ExecStop=/bin/kill -s QUIT $MAINPID
	
	[Install]
	WantedBy=multi-user.target

附录:  
参数Type=simple会导致以系统服务方式启动redis-server(systemctl和service)时会收到结束信号
>signal-handler (1520062559) Received SIGTERM scheduling shutdown...

参数PrivateTmp的介绍 :
>Takes a boolean argument. If true, sets up a new file system namespace for the executed processes and mounts private /tmp and /var/tmp directories inside it that is not shared by processes outside of the namespace. This is useful to secure access to temporary files of the process, but makes sharing between processes via /tmp or /var/tmp impossible. If this is enabled, all temporary files created by a service in these directories will be removed after the service is stopped. Defaults to false. It is possible to run two or more units within the same private /tmp and /var/tmp namespace by using the JoinsNamespaceOf= directive, see systemd.unit(5) for details. This setting is implied if DynamicUser= is set. For this setting the same restrictions regarding mount propagation and privileges apply as for ReadOnlyPaths= and related calls, see above. Enabling this setting has the side effect of adding Requires= and After= dependencies on all mount units necessary to access /tmp and /var/tmp. Moreover an implicitly After= ordering on systemd-tmpfiles-setup.service(8) is added.
>  
Note that the implementation of this setting might be impossible (for example if mount namespaces are not available), and the unit should be written in a way that does not solely rely on this setting for security.

查看配置文件内容

	systemctl cat redis-server.service
查看配置文件状态

	systemctl list-unit-files
状态:
>+ enabled：已建立启动链接
+ disabled：没建立启动链接
+ static：该配置文件没有[Install]部分（无法执行），只能作为其他配置文件的依赖
+ masked：该配置文件被禁止建立启动链接  

从配置文件的状态无法看出该Unit是否正在运行,必须执行systemctl status命令

	systemctl status redis-server.service
软连接  
Systemd 默认从目录/etc/systemd/system/读取配置文件。但是，里面存放的大部分文件都是符号链接，指向目录/usr/lib/systemd/system/，真正的配置文件存放在那个目录。
systemctl enable命令用于在上面两个目录之间，建立符号链接关系。

	systemctl enable redis-server.service
>等同于
>
	ln -s '/usr/lib/systemd/system/redis-server.service' '/etc/systemd/system/multi-user.target.wants/redis-server.service'
如果配置文件里面设置了开机启动，systemctl enable命令相当于激活开机启动。  
与之对应的，systemctl disable命令用于在两个目录之间，撤销符号链接关系，相当于撤销开机启动
>
	systemctl disable redis-server.service

一旦修改配置文件，就要让 SystemD 重新加载配置文件，然后重新启动

	systemctl daemon-reload
	systemctl restart redis-server.service
systemctl start redis-sever失败！分析问题

	journalctl|grep redis-server
进一步分析,打开redis.conf中指定的logfile

	cat /usr/local/redis/logs/redis.log
redis.log 错误关键片段  

+ 片段1
>WARNING you have Transparent Huge Pages (THP) support enabled in your kernel. This will create latency and memory usage issues with Redis. To fix this issue run the command 'echo never > /sys/kernel/mm/transparent_hugepage/enabled' as root, and add it to your /etc/rc.local in order to retain the setting after a reboot. Redis must be restarted after THP is disabled.

解决方法 禁用THP(透明大页)  
先查看THP是否启用了

	cat /sys/kernel/mm/transparent_hugepage/enabled
>[always] madvise never

[always]表示启用了THP  
禁用THP:  
1).临时设置

	echo never > /sys/kernel/mm/transparent_hugepage/enabled
再次查看是否启用了THP

	cat /sys/kernel/mm/transparent_hugepage/enabled
>always madvise [never]

表明暂时禁用了THP.

+ 片段2
>WARNING: The TCP backlog setting of 511 cannot be enforced because /proc/sys/net/core/somaxconn is set to the lower value of 128.

解决方法

	vim /etc/sysctl.conf
末尾添加
>net.core.somaxconn= 1024

末尾添加(为了解决另一个问题)
>vm.overcommit_memory = 1  

使生效

	sysctl -p

2).设置/etc/rc.loca,在系统重启后生效.

	vi /etc/rc.local
末尾添加
>if test -f /sys/kernel/mm/redhat_transparent_hugepage/enabled; then  
>     echo never > /sys/kernel/mm/redhat_transparent_hugepage/enabled  
fi

注:常用命令
>sysctl -p  
systemctl enable redis-server  
systemctl disable redis-server  
systemctl start redis-server  
systemctl restart redis-server  
systemctl stop redis-server  
systemctl cat redis-server.service
systemctl status redis-server  
pkill redis  
ps -ef|grep 6379  
netstat -tunpl|grep redis  
journalctl|grep redis-server  

#Redis commands
+ 配置外网访问

1). 修改配置,允许远程访问,添加连接密码:  

	vim /usr/local/redis/etc/redis.conf
>\#bind 127.0.0.1  
protectedmode yes

添加密码
>requirepass yourredispassword

	systemctl restart redis-server

2). (iptable白名单、路由、)阿里云要开放6379端口  
3). 远程登录:

	redis-cli -h 47.96.25.55 -p 6379 -a yourredispassword
+ database表示redis数据库实例
+ Redis的用户权限管理  
目标：创建有限权限的redis用户,用redis用户开启redis-server对外网服务,禁止redis用户登录系统.

		groupadd -g 2001 redis
		useradd -u2001 -g2001 -credis redis
	
		chmod 755 /usr/local/redis/bin
		chown -R redis:redis /usr/local/redis
	
		usermod -s /bin/bash redis
		查看redis-server进程 ps aux|grep redis
		su redis -c systemctl restart redis-server
		再次查看redis-server进程 ps aux|grep redis
		usermod -s /sbin/nologin redis

#Redis database Commands
1.SET、GET

	> SET company yishu  
	> GET company yishu  
2.INCR、DECR

	> SET num 10
	> INCR num => 11
	> DECR num => 10
注:提供的INCR、DECR是原子操作  

3.EXPIRE、TTL

	> SET company yishu  
	> Expire company 120  
	> TTL company => (<=120)  
注:单位s.  

4.list (RPUSH、LPUSH、RPOP、LPOP、LLEN、LRANGE)

	> RPUSH friends "Allen"  
	> RPUSH friends "Bob"  
	> LPUSH friends "Sam"  
	> LPUSH friends "Tony"
	> LRANGE friends 0 -1  
	> LLEN friends  
	> RPOP friends  
	> LPOP friends  
注:
>
1. list的key名字 不能是已存在的其他类型key-value数据的key.
2. RPUSH:移入,存储在双向链表表头;LPUSH : 表尾
3. LRANGE 查询list指定片段,有两个索引参数.0是头,-1代表尾.
4. RPOP:从链表表尾移除一个元素;LPOP:从表头移除一个元素.
5. LLEN:返回list的长度.

5.set (SADD、SREM、SISMEMBER、SMEMBERS、SUNION)

	> SADD family "mother"
	> SADD family "father"
	> SADD family "sun"
	> SADD family "daughter"
	> SMEMBERS family
	> SREM family "daughter"
	> SISMEMBER family "daughter"
	> SADD relatives "cousin"
	> SADD relatives "uncle"
	> SUNION family relatives

注:
>1.set无序不重复.  
2.SADD往set中添加 SREM从set中删除  
3.SISMEMBER判断集合中是否存在指定元素,1表示存在,0表示不存在.  
4.SUNION combines two or more sets and returns the list of all elements.返回多个set的所有元素,无序.

6.sorted set (ZADD、ZRANGE)

	> ZADD hackers 1940 "Alan Kay"
	> ZADD hackers 1906 "Grace Hopper"
	> ZADD hackers 1953 "Richard Stallman"
	> ZADD hackers 1965 "Yukihiro Matsumoto"
	> ZADD hackers 1916 "Claude Shannon"
	> ZADD hackers 1969 "Linus Torvalds"
	> ZADD hackers 1957 "Sophie Wilson"
	> ZADD hackers 1912 "Alan Turing"
	> ZRANGE hackers 2 4
	1) "Claude Shannon", 2) "Alan Kay", 3) "Richard Stallman"
	> ZRANGE hackers 0 -1
7.Hashes (HSET、HMSET、HGET、HGETALL)

	> HSET user name "John Smith"
	> HSET user email "john.smith@example.com"
	> HSET user password "s3cret"
	> HGET user name => "John Smith"
	> HMSET audience gender "male" name "Percy Bysshe Shelley" email "shelley@example.com"
	OK
	> HGETALL user
	1) "name"
	2) "John Smith"
	3) "email"
	4) "john.smith@example.com"
	5) "password"
	6) "s3cret"
	Numerical values in hash fields are handled exactly the same as in simple strings and there are operations to increment this value in an atomic way.
	> HSET user:1000 visits 10
	> HINCRBY user:1000 visits 1 => 11
	> HINCRBY user:1000 visits 10 => 21
	> HDEL user:1000 visits
	> HINCRBY user:1000 visits 1 => 1