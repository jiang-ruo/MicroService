// 3306

```mysql
# 创建数据库
> CREATE DATABASE MS;
# 创建用户
> CREATE USER "ms"@"%" IDENTIFIED BY "microservice_mysql";
> CREATE USER "ms"@"localhost" IDENTIFIED BY "microservice_mysql";
# 赋予权限
> GRANT ALL PRIVILEGES ON MS.* TO "ms"@"localhost" WITH GRANT OPTION;
> GRANT ALL PRIVILEGES ON MS.* TO "ms"@"%" WITH GRANT OPTION;
```



\# 定时备份数据库

https://www.cnblogs.com/eternal1025/p/8554225.html