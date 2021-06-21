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
# linux中普通用户无法使用1024以上端口
# 切换到root用户，给nginx执行文件赋予使用80端口的能力
[sergices/nginx//sbin]# setcap cap_net_bind_service=+eip nginx
# 清除权限
[sergices/nginx/1.18/sbin]# setcap -r nginx
# 启动nginx
[sergices/nginx/1.18/sbin]$ ./nginx

# 软链接
[.local_ms] ln -s ../services/nginx/1.18/sbin/nginx nginx
```

https://blog.csdn.net/qq_37345604/article/details/90034424