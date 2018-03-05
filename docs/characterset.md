#*MySQL字符集设置*
[ 介绍三种查看MySQL字符集的方法](http://blog.itpub.net/15498/viewspace-2120723/)  

* 系统变量：  
>– character_set_server：默认的内部操作字符集  
– character_set_client：客户端来源数据使用的字符集  
– character_set_connection：连接层字符集  
– character_set_results：查询结果字符集  
– character_set_database：当前选中数据库的默认字符集  
– character_set_system：系统元数据(字段名等)字符集  
– 还有以collation_开头的同上面对应的变量，用来描述字符序。  

+ 查看字符集
	- 查看MySQL数据库服务器和数据库MySQL的字符集：

			mysql> show variables like '%char%';
	- 查看MySQL数据表（table）的MySQL字符集:

			mysql> use test
			mysql> show table status from test like '%work%';
	- 查看MySQL数据列（column）的MySQL字符集:

			mysql> show full columns from workinfo;
	- 解决字符集通讯设置不匹配的方法：
>(修改默认的character_set_client,character_set_connection,character_set_result)  
1. 重新编译mysql和php,mysql加入编译参数 –default-character-set=utf8  
2. PHP程序在查询数据库之前，执行mysql_query(”set names utf8;”);  
3.修改my.cnf，在[mysqld]中加入init-connect=”set names utf8” (对于超级用户连接该选项无效)  
4. 启动mysqld 加入参数 –skip-character-set-client-handshake 忽略客户端字符集  

	- (2)

	mysql> show variables like '%char%';
	+--------------------------+--------------------------+
	| Variable_name            | Value                    |
	+--------------------------+--------------------------+
	| character_set_client     | utf8mb4                  |
	| character_set_connection | utf8mb4                  |
	| character_set_database   | utf8mb4                  |
	| character_set_filesystem | binary                   |
	| character_set_results    | utf8mb4                  |
	| character_set_server     | utf8mb4                  |
	| character_set_system     | utf8                     |
	| character_sets_dir       | /usr/local/mysql-5.7.... |
	+--------------------------+--------------------------+

	mysql> show variables like '%coll%';
	+----------------------+--------------------+
	| Variable_name        | Value              |
	+----------------------+--------------------+
	| collation_connection | utf8mb4_general_ci |
	| collation_database   | utf8mb4_general_ci |
	| collation_server     | utf8mb4_general_ci |
	+----------------------+--------------------+