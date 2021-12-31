package cn.linshenkx.halosyncserver.manager.impl;

import cn.hutool.core.date.DateTime;
import cn.linshenkx.halosyncserver.httpclient.HaloAuthHttpClient;
import cn.linshenkx.halosyncserver.manager.HaloAuthManager;
import cn.linshenkx.halosyncserver.model.AuthToken;
import cn.linshenkx.halosyncserver.model.params.LoginParam;
import cn.linshenkx.halosyncserver.prop.HaloGitProp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class HaloAuthManagerImpl implements HaloAuthManager {

    @Resource
    private HaloGitProp haloGitProp;
    @Resource
    private HaloAuthHttpClient haloAuthHttpClient;

    private String token;
    private Long tokenExpiredTime;
    
    @Override
    public String getLoginToken(boolean force) {
        if (force || StringUtils.isBlank(token) || tokenExpiredTime < System.currentTimeMillis()) {
            LoginParam loginParam = new LoginParam();
            loginParam.setUsername(haloGitProp.getUsername());
            loginParam.setPassword(haloGitProp.getPassword());
            AuthToken auth = haloAuthHttpClient.auth(loginParam).getData();
            token = auth.getAccessToken();
            tokenExpiredTime = System.currentTimeMillis() + (auth.getExpiredIn() - 60) * 1000L;
            log.info("获取token成功，tokenExpiredTime为：{}", new DateTime(tokenExpiredTime));
        }
        return token;
    }

}
