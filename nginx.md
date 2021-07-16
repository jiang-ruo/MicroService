```shell
[services/package]# yum -y install gcc pcre pcre-devel yum-utils zlib-devel
[services/package]$ wget http://nginx.org/download/nginx-1.18.0.tar.gz
[services/package]$ tar -zxf nginx-1.18.0.tar.gz
[services/package]$ mv nginx-1.18.0 nginx
[services/package]$ cd nginx
# --prefix=[指定安装位置，不然会默认安装到系统路径下]
# prefix后面的参数最好使用绝对路径，因为该参数会被原原本本的写入配置中，而nginx会在安装的位置使用该相对路径，就会造成错误，如果是make前后在同一位置的软件使用相对路径就不会造成错误
[services/package/nginx]$ ./configure --prefix=../../nginx/1.18
[services/package/nginx]$ make
[services/package/nginx]$ make install
# linux中普通用户无法使用1024以下端口
# 切换到root用户，给nginx执行文件赋予使用80端口的能力
[sergices/nginx//sbin]# setcap cap_net_bind_service=+eip nginx
# 清除权限
[sergices/nginx/1.18/sbin]# setcap -r nginx
# 启动nginx，默认会使用conf/nginx.conf配置文件
[sergices/nginx/1.18/sbin]$ ./nginx

# 软链接
[.local_ms] ln -s ../services/nginx/1.18/sbin/nginx nginx
```

```shell
配置文件统一管理
将services/nginx/1.18/conf/nginx.conf修改为
include /home/ms/services/conf/nginx.conf
```



https://blog.csdn.net/qq_37345604/article/details/90034424

https://www.bilibili.com/video/BV1uT4y137vq?from=search&seid=12164215329571172087



```
nginx目录结构说明
/-nginx
 |-conf/	配置文件存放位置
 |-html/	网页文件存放位置
 |-logs/	日志文件存放位置
 |-sbin/	可执行文件位置
```



### nginx指令

```shell
$ nginx -h
		-v	//查看版本信息
		-V	//查看版本及安装的模块
		-t	//测试配置文件是否正确书写
		-T
		-q
		-s [stop|quit|reopen|reload]
			stop	//直接停止
			quit	//处理完当前请求再退出
			reopen	//重新生成日志文件
			reload	//重新载入配置文件，当前正在执行的请求使用旧的线程处理，新的请求使用重载的线程执行，处理完所有旧线程后关闭掉旧线程
		-p
		-c
		-g
```



### nginx.conf

```shell
# 主线程的配置
# 主线程的配置
# 主线程的配置
# 创建的工作线程的数量，auto --几个cput则几个工作线程
worker_processes auto;

events {
	# 定义nginx使用哪种时间驱动类型，在Redhat/CentOs中性能最好的是epoll
	use [method];
	# 定义每个worker进程可以同时处理的连接数，并发数量
	worker_connections [num];
	# 处理新连接的方法，
	# on是指由各个worker进程轮流处理，由主线程指派
	# off每个worker都会接到通知，但是只有一个worker进程获得处理连接的权限
	# off: Linux3.9+将使用reuseport，会有更好的性能
	accept_mutex [on|off];
}

# http配置
# http配置
# http配置
http {
	...
	# server配置虚拟主机，可以配置多个虚拟主机
	server{
		# 监听指定端口，基于端口监听，当为80时可以忽略
		listen [port];
		# 该主机的默认html文件位置
		root [path];
		# 默认主页
		index [file]
	}
	server {
		# 监听指定端口，基于ip(一台及其可以不止一个ip)
		listen [ip:port];
	}
	server {
		# 基于域名
		listen [port];
		server_name [域名1] [域名2] [www.*.com];
	}
}


```



### 负载均衡

```shell
1、轮询
upstream [name] {
	server [ip:port];
	server [ip:port];
}

2、权重
upstream [name] {
	# 在time秒内请求失败count次，认为该服务器宕机
	server [ip] weight=[weight] max_fails=[count] fail_timeout=[time]s;
	server [ip] weight=[weight];
}

3、ip hash
upstream [name] {
	ip_hash;
	server [ip:port];
	server [ip:port];
}

# 一下为第三方
4、fair
upstream [name] {
	server [server1];
	server [server2];
	fair;
}

5、url_hash
upstream [name] {
	server squid1:3128;
	server squid2:3128;
	hash $request_uri;
	hash_method crc32;
}

server {
	location [url] {
		# 下面的name指负载均衡中的name
		proxy_pass http://[name]
	}
}
```

