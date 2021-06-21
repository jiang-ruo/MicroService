## mysql 非root用户安装、非系统路径安装、自定义mysql安装路径

https://blog.csdn.net/justdoit_potato/article/details/110039338

```shell
[services/package]# yum install libaio*
# minimal删除了debug部分
# 打包好的安装包默认安装到系统目录下，必须使用root用户，无法make install因此需要下载源码安装包
# https://cdn.mysql.com/archives/mysql-8.0/mysql-8.0.24-linux-glibc2.17-x86_64-minimal.tar.xz
# https://cdn.mysql.com/archives/mysql-8.0/mysql-8.0.24-linux-glibc2.12-x86_64.tar.xz
[services/package]$ wget https://cdn.mysql.com/archives/mysql-8.0/mysql-8.0.24-linux-glibc2.12-x86_64.tar.xz
[services/package]$ tar -xf https://cdn.mysql.com/archives/mysql-8.0/mysql-8.0.24-linux-glibc2.12-x86_64.tar.xz
[services/package]$ mv mysql-8.0.24-linux-glibc2.12-x86_64/ ../mysql/8.0
[services/package]$ cd ../mysql/8.0

[services/mysql/8.0]$ cp /etc/my.cnf my.cnf
[services/mysql/8.0]$ vim my.cnf
# my.cnf修改如下
```

```shell
[mysqld]
basedir=/home/ms/services/mysql/8.0
datadir=/home/ms/services/mysql/8.0/data
socket=/home/ms/services/mysql/8.0/tmp/mysqld.sock
# 如果采用默认端口，下行可省略
port=3306

[client]
socket=/home/ms/services/mysql/8.0/tmp/mysql.sock

[mysqld_safe]
log-error=/home/ms/services/mysql/8.0/log/error.log
# 此处采用默认值mariadb.pid和mariadb.log会报错，原因未知
pid-file=/home/ms/services/mysql/8.0/data/mysql.pid
```

```shell
[services/mysql/8.0]$ mkdir data tmp log
[services/mysql/8.0]$ cd support-files
[services/mysql/8.0/support-files]$ vim mysql.server
# mysql.server修改如下
```

```shell
# 1、删除conf=/etc/my.cnf
# /conf=->回车->dd

# 2、给basedir和conf赋值
# 50->gg
# basedir=/home/ms/services/mysql/8.0
# conf=$basedir/my.cnf

# 3、添加extra_args="-c $conf"
# 查找到
# extra_args=""
# if test -r "$basedir/my.cnf"
# then
# 	extra_args="-e $basedir/my.cnf"
# fi
# 在此处添加extra_args="-c $conf"

# 4、修改$bindir/mysqld_safe  --datadir="$datadir" --pid-file="$mysqld_pid_file_path" $other_args >/dev/null &
# 为(添加--defaults-file="$conf")
# $bindir/mysqld_safe --defaults-file="$conf" --datadir="$datadir" --pid-file="$mysqld_pid_file_path" $other_args >/dev/null &
```

```shell
[services/mysql/8.0/support-files]$ cd ..
# 初始化mysql
# bin/mysqld --defaults-file=[配置文件] --initialize --user=[用户名]
[services/mysql/8.0]$ bin/mysqld --defaults-file=my.cnf --initialize --user=lxj
# 启动
[services/mysql/8.0]$ support-files/mysql.server start
```

```shell
# 进入mysql并修改用户密码，初始密码在初始化mysql时随机生成
[services/mysql/8.0]$ bin/mysql -uroot -p -h 127.0.0.1 --socket=tmp/mysqld.sock --port=3306
mysql>
# 正常修改密码流程
```

```shell
# 进入环境变量目录，将mysql部分命令设置为环境变量
[]$ cd .local_ms
# 不知道为什么，只有进入这个目录才能成功设置软链接
[.local_ms]$ ln -s ../services/mysql/8.0/bin/mysql ./
[.local_ms]$ ln -s ../services/mysql/8.0/support-files/mysql.server ./
[.local_ms]$ ln -s ../services/mysql/8.0/tmp/mysqld.sock ./
# 此时可以在任意路径通过mysql -uroot -p -h 127.0.0.1 --socket=mysql.sock登陆，当端口为3306时，可以忽略--port=3306
```





// 3306

```mysql
# 创建数据库
> CREATE DATABASE MS;
# 创建用户
> CREATE USER "ms"@"%" IDENTIFIED WITH mysql_native_password BY "microservice_mysql";
# 赋予权限
> GRANT ALL PRIVILEGES ON MS.* TO "ms"@"%" WITH GRANT OPTION;
> FLUSH PRIVILEGES;
```



\# 定时备份数据库

https://www.cnblogs.com/eternal1025/p/8554225.html