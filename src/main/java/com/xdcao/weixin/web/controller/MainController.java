package com.xdcao.weixin.web.controller;

import com.google.gson.Gson;
import com.xdcao.weixin.web.deprecate.MsgDispatcher;
import com.xdcao.weixin.utils.MessageUtil;
import com.xdcao.weixin.utils.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: buku.ch
 * @Date: 2019-04-25 22:34
 */

@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private Gson gson;

    @Autowired
    private MsgDispatcher msgDispatcher;


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

    @RequestMapping(value = "/")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @PostMapping(value = "/wx")
    @ResponseBody
    // post 方法用于接收微信服务端消息
    public String doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> stringStringMap = MessageUtil.parseXml(request);
            String jsonMsg = gson.toJson(stringStringMap);
            LOGGER.info("接收到用户的消息: "+ jsonMsg);

            String respMsg = msgDispatcher.dispatchMsg(stringStringMap);
            return respMsg;
        } catch (Exception e) {
            LOGGER.error("解析微信xml消息失败", e);
            return "";
        }
    }

}
