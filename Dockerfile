FROM adoptopenjdk:11-jre-hotspot
ENV APP_HOME=/application
WORKDIR application
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
COPY /dependency/ ./

ENV JVM_XMS="256m" \
    JVM_XMX="256m" \
    JVM_OPTS="-Xmx256m -Xms256m"

ENTRYPOINT java -Xms${JVM_XMS} -Xmx${JVM_XMX} ${JVM_OPTS}  org.springframework.boot.loader.JarLauncher