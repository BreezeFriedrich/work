#Links
1.redis & memcached区别剖析 https://www.biaodianfu.com/redis-vs-memcached.html
2.redis 官网
>下载与simple start: https://redis.io/download
>redis命令: http://try.redis.io/
>Redis data types and abstractions https://redis.io/topics/data-types-intro
3.Spring Data Redis 官网 http://projects.spring.io/spring-data-redis/
>2.1.0.M1/reference https://docs.spring.io/spring-data/redis/docs/2.1.0.M1/reference/html/#get-started
>2.1.0.M1/api https://docs.spring.io/spring-data/redis/docs/2.1.0.M1/api/
4.Spring 整合 Redis
>Spring整合Redis(第一篇)—SDR简述 https://www.cnblogs.com/zjrodger/p/5800593.html
>spring集成redis https://www.cnblogs.com/hello-daocaoren/p/7891907.html
>Spring集成Redis步骤 http://blog.csdn.net/jrn1012/article/details/70344776
>使用Spring Data Redis操作Redis（一） 很全面 http://blog.csdn.net/albertfly/article/details/51494080
>redis整合spring(redisTemplate工具类) http://blog.csdn.net/qq_34021712/article/details/75949706
>java之redis篇(spring-data-redis整合) https://www.cnblogs.com/tankaixiong/p/3660075.html
>redis和spring集成（注解实现，方便，快捷） http://blog.csdn.net/fighterandknight/article/details/53432276/
>JEDIS http://blog.csdn.net/legend_x/article/details/12317087
>Springboot系列redis使用之1 https://www.2cto.com/kf/201702/595093.html
5.Linux下Redis的安装和部署
https://www.cnblogs.com/wangchunniu1314/p/6339416.html

#Installation
$ wget http://download.redis.io/releases/redis-4.0.8.tar.gz
$ tar xzf redis-4.0.8.tar.gz
$ cd redis-4.0.8
$ make
...compile->src directory
Run Redis with: $ src/redis-server
interact with Redis using the built-in client:
$ src/redis-cli
redis> set foo bar
OK
redis> get foo
"bar"

#Redis Commands
1.SET、GET
>SET company yishu
>GET company yishu
2.INCR、DECR
>SET num 10
>INCR num => 11
>DECR num => 10
注:提供的INCR、DECR是原子操作
3.EXPIRE、TTL
>SET company yishu
>Expire company 120
>TTL company => (<=120)
注:单位s.
4.list (RPUSH、LPUSH、RPOP、LPOP、LLEN、LRANGE)
>RPUSH friends "Allen"
>RPUSH friends "Bob"
>LPUSH friends "Sam"
>LPUSH friends "Tony"
>LRANGE friends 0 -1
>LLEN friends
>RPOP friends
>LPOP friends
注:1).list的key名字 不能是已存在的其他类型key-value数据的key.
2).RPUSH:移入,存储在双向链表表头;LPUSH : 表尾
3).LRANGE 查询list指定片段,有两个索引参数.0是头,-1代表尾.
4).RPOP:从链表表尾移除一个元素;LPOP:从表头移除一个元素.
5).LLEN:返回list的长度.
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
注:1).set无序不重复.
2).SADD往set中添加 SREM从set中删除
3).SISMEMBER判断集合中是否存在指定元素,1表示存在,0表示不存在.
4).SUNION combines two or more sets and returns the list of all elements.返回多个set的所有元素,无序.
6.sorted set (ZADD、ZRANGE)
> ZADD hackers 1940 "Alan Kay"
> ZADD hackers 1906 "Grace Hopper"
> ZADD hackers 1953 "Richard Stallman"
> ZADD hackers 1965 "Yukihiro Matsumoto"
> ZADD hackers 1916 "Claude Shannon"
> ZADD hackers 1969 "Linus Torvalds"
> ZADD hackers 1957 "Sophie Wilson"
> ZADD hackers 1912 "Alan Turing"
> ZRANGE hackers 2 4 => 1) "Claude Shannon", 2) "Alan Kay", 3) "Richard Stallman"
> ZRANGE hackers 0 -1
7.Hashes (HSET、HMSET、HGET、HGETALL)
> HSET user name "John Smith"
> HSET user email "john.smith@example.com"
> HSET user password "s3cret"
> HGET user name => "John Smith"
> HMSET audience gender "male" name "Percy Bysshe Shelley" email "shelley@example.com" => OK
> HGETALL user
1) "name"
2) "John Smith"
3) "email"
4) "john.smith@example.com"
5) "password"
6) "s3cret"
>Numerical values in hash fields are handled exactly the same as in simple strings and there are operations to increment this value in an atomic way.
HSET user:1000 visits 10
    HINCRBY user:1000 visits 1 => 11
    HINCRBY user:1000 visits 10 => 21
    HDEL user:1000 visits
    HINCRBY user:1000 visits 1 => 1