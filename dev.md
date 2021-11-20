## docker镜像
> 参考阅读： https://spring.io/guides/topicals/spring-boot-docker

### 技术选型：
1. 因为还是习惯使用dockerfile，所以放弃了springboot官方docker插件和google的Jib
2. 因为使用的是gradle，所以放弃了Spotify和fabric8io

最终选择了Palantir。很轻，也很简单。缺点是文档写得模糊不清，我自己摸索了好一会儿。
并且参考springboot官方写法，进行解压构造，而非fatjar。

### 命令
docker、dockerPush是对默认tag即docker.name进行操作
dockerTag、dockerTagPush是对所有tag即所有docker.tag项进行操作
dockerTag[具体tag任务名] 则是对指定tag进行操作

```shell
# build（同时构造 latest和version 镜像）
gradle dockerTag
# push（同时推送 latest和version 镜像）
gradle dockerTagPush

```
