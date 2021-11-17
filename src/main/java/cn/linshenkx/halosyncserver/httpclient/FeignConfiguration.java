package cn.linshenkx.halosyncserver.httpclient;

import cn.linshenkx.halosyncserver.manager.HaloManager;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

public class FeignConfiguration {

    @Resource
    private HaloManager haloManager;

    @Bean
    Logger.Level feignLoggerLevel() {
        //这里记录所有，根据实际情况选择合适的日志level
        return Logger.Level.BASIC;
    }

    @Bean
    RequestInterceptor getRequestInterceptor() {
        return template -> {
            if (template.path().startsWith("/api/admin/login")) {
                return;
            }
            template.header("ADMIN-Authorization", haloManager.getLoginToken(false));
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new HaloErrorDecoder(haloManager);
    }


}
