```shell
[services/package]# sudo yum -y install gcc pcre pcre-devel yum-utils
[services/package]$ wget http://nginx.org/download/nginx-1.18.0.tar.gz
[services/package]$ tar -zxvf nginx-1.18.0.tar.gz nginx
[services/package]$ cd nginx
# --prefix=[指定安装位置，不然会默认安装到系统路径下]
[services/package/nginx]$ ./configure --prefix=../../nginx
[services/package/nginx]$ make
[services/package/nginx]$ make install
# linux中普通用户无法使用1024以上端口
# 给ms/services目录赋予启使用1024以下端口的权限
# 切换到root用户，给nginx执行文件赋予80端口权限
[sergices/nginx/sbin]# setcap cap_net_bind_service=+eip nginx
# 清除权限
[sergices/nginx/sbin]# setcap -r nginx
# 启动nginx
[sergices/nginx/sbin]$ ./nginx
# 创建日志软链接
[sergices/nginx/sbin]$ cd ../..
[sergices]$ ln -s nginx/log log/nginx.log
```

