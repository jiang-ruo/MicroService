```shell
[services/package]$ wget https://download.redis.io/releases/redis-6.2.3.tar.gz
[services/package]$ tar -zxf redis-6.2.3.tar.gz
[services/package]$ mv redis-6.2.3.tar.gz ../redis
[services/package]$ cd ../redis
[services/redis]$ make
# 启动redis，默认端口6379
[services/redis]$ ./src/redis-server
# 使用指定配置文件启动redis
[services/redis]$ ./redis-server [file]
# 后台启动，日志输出到log文件
[services/redis]$ nohup ./src/redis-server > ../log/redis.log

# redis中一些文件的作用
[services/redis/src]$ ./redis-benchmark //用于进行redis性能测试的工具
[services/redis/src]$ ./redis-check-dump //用于修复出问题的dump.rdb文件
[services/redis/src]$ ./redis-cli //redis的客户端
[services/redis/src]$ ./redis-server //redis的服务端
[services/redis/src]$ ./redis-check-aof //用于修复出问题的AOF文件
[services/redis/src]$ ./redis-sentinel //用于集群管理

https://blog.csdn.net/liqingtx/article/details/60330555
```

