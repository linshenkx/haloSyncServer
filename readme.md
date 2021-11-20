Halo博客同步器，支持定时检测Hexo博客仓库并同步变化到Halo博客。
## 项目说明
自动检测其他使用静态网站生成器（支持Git Pages、使用markdown语法）的博客系统的内容变化，并同步到Halo博客中。

目前支持Hexo。

设计思想参考：
https://linshenkx.github.io/my_blog_system_design/
或
https://www.linshenkx.cn/archives/myblogsystemdesign

## TODO
1. 支持deploy key(ssh)认证
目前是用户密码/token方式认证，权限粒度过大。实际只需要对特定仓库拥有读权限即可。
2. 支持webhook
目前是定时同步，每分钟1次获取git数据。后面应支持webhook方式触发获取，定时同步作为补充。
3. 支持hugo

## 使用方法
建议使用docker部署

1. 准备外部持久化目录
```shell
mkdir -p /opt/haloSyncServer/config
mkdir -p /opt/haloSyncServer/logs

```
2. 提供自定义yaml配置
写法参考application-template.yaml
yaml里的配置项大多可以从环境变量中读取，可以直接通过环境变量提供，跳过这一步
```shell
cp ./application.yaml /opt/haloSyncServer/config/

```

3. 启动
因为涉及git操作，建议配置代理，否则可能拉取失败

/application/config 用于传递yaml配置文件，并存放mapdb本地文件commit.db，需持久化。
commit.db用来记录当前跟踪的git commit信息

logs用来存放日志，可不挂载出来。
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
# 然后再次执行启动命令
```

## 配置说明
参考 application-template.yaml
以下变量都可通过环境变量注入

| 变量名 | 默认值 | 说明 |
| --- | --- | --- | 
| HALO_SYNC_SERVER_PORT|12996|服务端口，暂时没用，不重复就行，后面可能会用于git hook回调|
| HALO_URL| https://linshenkx.cn| HALO网址
| HALO_USERNAME | 123456@qq.com | HALO用户
| HALO_PASSWORD | 123456 | HALO密码
| HEXO_GIT_IS_PRIVATE | true |hexo-git仓库：是否为私有仓库，非私有则不需要配置git用户名密码
| HEXO_GIT_URL | https://github.com/you/blog.git | hexo-git仓库地址
| HEXO_GIT_USERNAME | PRIVATE-TOKEN | hexo-git仓库：用户名。注意：GitHub不支持用户账号登录，必须使用token，此时应使用固定值 PRIVATE-TOKEN
| HEXO_GIT_PASSWORD | your-token |hexo-git仓库：密码。注意：GitHub不支持用户账号登录，必须使用token
| HEXO_GIT_BRANCH | refs/heads/master | 要跟踪的git分支
| HEXO_GIT_SOURCE_DIR | source/_posts | posts所在的位置，一般为固定值，不需要改
| HEXO_GIT_INIT | true | 第一次运行时有效，是否将hexo-git仓库上的文章补全到halo上

