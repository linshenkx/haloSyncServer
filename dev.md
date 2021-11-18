## 构造镜像
> 因为使用了分层打包，所以不能用springboot的镜像打包插件。还是基于Dockerfile来手动构造。

```shell
# 获取可执行jar包
gradle bootjar
# build
docker build --build-arg JAR_FILE=build/libs/\*.jar -t linshen/halo-sync-server  .
# push
docker push linshen/halo-sync-server

```

docker rm -f haloSyncServer && docker pull linshen/halo-sync-server && docker run -it -d --name haloSyncServer -v /opt/haloSyncServer/config:/application/config --network host -e JVM_OPTS="-Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=7890" --restart=always linshen/halo-sync-server


curl  -H 'ADMIN-Authorization:token'  -H 'Content-Type: application/json'  -v -X DELETE   http://linshenkx.cn/api/admin/posts -d '[139]'
