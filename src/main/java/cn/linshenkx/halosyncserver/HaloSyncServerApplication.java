package cn.linshenkx.halosyncserver;

import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@EnableFeignClients
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class HaloSyncServerApplication {


    public static void main(String[] args) {
        log.info("########## HaloSyncServer 启动 ############");
        log.info("\n" + SystemUtil.getOsInfo().toString());
        log.info("\n" + SystemUtil.getRuntimeInfo().toString());
        log.info("\n" + SystemUtil.getJavaRuntimeInfo().toString());
        log.info("\n" + SystemUtil.getJvmInfo().toString());
        String proxyHost = System.getenv("PROXY_HOST");
        String proxyPort = System.getenv("PROXY_PORT");
        if (StringUtils.isNotBlank(proxyHost) && StringUtils.isNotBlank(proxyPort)) {
            System.setProperty("proxyHost", proxyHost);
            System.setProperty("proxyPort", proxyPort);
        }
        System.getenv().entrySet().forEach(entry -> log.info("  {}:{}", entry.getKey(), entry.getValue()));
        SpringApplication.run(HaloSyncServerApplication.class, args);
    }


}
