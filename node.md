```shell
[services/package]$ wget https://nodejs.org/dist/v14.17.3/node-v14.17.3-linux-x64.tar.gz
[services/package]$ tar -zxf [file]
[services]$ mv package/[node_file] node/14.17
[.local_ms]$ ln -s ../services/node/14.17/bin/node node
[.local_ms]$ ln -s ../services/node/14.17/bin/npm npm
[.local_ms]$ ln -s ../services/node/14.17/bin/npx npx
```

