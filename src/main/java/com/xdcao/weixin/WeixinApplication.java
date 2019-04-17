package com.xdcao.weixin;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@SpringBootApplication
@Controller
public class WeixinApplication {

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Autowired
    private Gson gson;

    @Autowired
    private MsgDispatcher msgDispatcher;

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinApplication.class);

    @GetMapping(value = "/wx")
    @ResponseBody
    public String hello(HttpServletRequest request, @RequestParam("signature") String signature,
                        @RequestParam("echostr") String echoStr,
                        @RequestParam("timestamp") String timestamp,
                        @RequestParam("nonce") String nonce ) {

        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                LOGGER.info("success");
            } else {
                LOGGER.info("这里存在非法请求！");
            }
        } catch (Exception e) {
            LOGGER.error("",e);
        }
        return echoStr;
    }

    @PostMapping(value = "/wx")
    // post 方法用于接收微信服务端消息
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> stringStringMap = MessageUtil.parseXml(request);
            String jsonMsg = gson.toJson(stringStringMap);
            LOGGER.info("接收到用户的消息: "+ jsonMsg);
            String msgType = stringStringMap.get(MessageUtil.MSG_TYPE);
            if (msgType == null) {
                LOGGER.error("无法解析消息类型 {}", jsonMsg);
            }
            msgDispatcher.dispatchMsg(msgType);
        } catch (Exception e) {
            LOGGER.error("解析微信xml消息失败", e);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(WeixinApplication.class, args);
    }

}
