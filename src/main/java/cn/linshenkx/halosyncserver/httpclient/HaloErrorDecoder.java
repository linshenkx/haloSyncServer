package cn.linshenkx.halosyncserver.httpclient;

import cn.linshenkx.halosyncserver.manager.HaloManager;
import com.alibaba.fastjson.JSON;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HaloErrorDecoder implements ErrorDecoder {

    private HaloManager haloManager;

    public HaloErrorDecoder(HaloManager haloManager) {
        this.haloManager = haloManager;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            // 获取原始的返回内容
            String json = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            try {
                HaloErrorResult errorResult = JSON.parseObject(json, HaloErrorResult.class);
                if (errorResult.getStatus().equals(401)) {
                    //强制更新token
                    log.warn("将执行强制更新token");
                    haloManager.getLoginToken(true);
                    return new RuntimeException(errorResult.toString());
                }
                return new RuntimeException(errorResult.toString());
            } catch (Exception e) {
                // 将返回内容反序列化为Result，这里应根据自身项目作修改
                // 业务异常抛出简单的 RuntimeException，保留原来错误信息
                return new RuntimeException(response.status() + ":" + methodKey + ":" + json);
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            return ex;
        }
    }
}