
## 项目说明

## 使用方法
不管哪种方法，都支持环境变量和yaml配置，本质一样，yaml更自由一些。
### docker
1. 准备外部持久化目录
```shell
mkdir -p /opt/haloSyncServer/config
mkdir -p /opt/haloSyncServer/logs

```
2. 提供自定义yaml配置
```shell
cp ./application.yaml /opt/haloSyncServer/config/

```

3. 启动
```shell
docker run -it -d --name haloSyncServer \
-v /opt/haloSyncServer/config:/application/config \
-v /opt/haloSyncServer/logs:/application/logs \
--network host \
-e JVM_OPTS="-Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=7890" \
--restart=always \
linshen/halo-sync-server

```
4. 查看日志
```shell
docker logs -f haloSyncServer
```
5. 更新镜像
```shell
docker rm -f haloSyncServer
docker pull linshen/halo-sync-server 
docker restart haloSyncServer

```

## 配置说明