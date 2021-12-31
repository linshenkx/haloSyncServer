package cn.linshenkx.halosyncserver.httpclient;

import cn.linshenkx.halosyncserver.model.AuthToken;
import cn.linshenkx.halosyncserver.model.params.LoginParam;
import cn.linshenkx.halosyncserver.model.support.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "haloAuth", url = "${halo-sync.halo.url}")
public interface HaloAuthHttpClient {

    @PostMapping("/api/admin/login")
    BaseResponse<AuthToken> auth(@RequestBody LoginParam loginParam);

}
