package com.xdcao.weixin.pojo;

import org.springframework.stereotype.Component;

/**
 * @Author: buku.ch
 * @Date: 2019-04-18 12:23
 */

@Component
public class TokenBean {

    private AccessToken accessToken;

    public TokenBean() {
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
