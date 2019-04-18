package com.xdcao.weixin;

import com.xdcao.weixin.pojo.resp.PicRespMessage;
import com.xdcao.weixin.pojo.resp.Picture;
import com.xdcao.weixin.utils.MessageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeixinApplicationTests {

    @Autowired
    private TokenService tokenService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testFetchToken() {
        tokenService.getToken_getTicket();
    }

    @Test
    public void testImgUpload() {

    }

}
