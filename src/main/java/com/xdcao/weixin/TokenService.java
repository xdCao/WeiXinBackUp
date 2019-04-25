package com.xdcao.weixin;

import com.google.gson.Gson;
import com.xdcao.weixin.pojo.AccessToken;
import com.xdcao.weixin.pojo.TokenBean;
import com.xdcao.weixin.utils.HttpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: buku.ch
 * @Date: 2019-04-18 11:54
 */

@Service
public class TokenService implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

    @Value("${appID}")
    private String appID;

    @Value("${appsecret}")
    private String appsecret;

    @Value("${tokenUrl}")
    private String tokenUrl;

    @Autowired
    private Gson gson;

    @Autowired
    private TokenBean tokenBean;

    @Scheduled(fixedRate = 3600000)
    public void getToken_getTicket() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "client_credential");
        params.put("appid", appID);
        params.put("secret",appsecret);
        try {
            String jsToken = HttpUtil.sendGet(tokenUrl, params);
            AccessToken accessToken = gson.fromJson(jsToken, AccessToken.class);
            tokenBean.setAccessToken(accessToken);
            LOGGER.info("获取access_token: {}",accessToken.getAccessToken());
        } catch (Exception e) {
            LOGGER.error("获取accessToken失败", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        getToken_getTicket();
    }

}
