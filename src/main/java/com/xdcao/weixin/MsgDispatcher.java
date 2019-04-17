package com.xdcao.weixin;

import com.google.gson.Gson;
import com.xdcao.weixin.pojo.resp.TextRespMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Author: buku.ch
 * @Date: 2019-04-17 22:31
 */

@Component
public class MsgDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgDispatcher.class);

    @Autowired
    private EventDispatcher eventDispatcher;

    @Autowired
    private Gson gson;

    public String dispatchMsg(Map<String, String> msgMap) {
        String msgType = msgMap.get(MessageUtil.MSG_TYPE);
        if (msgType == null) {
            LOGGER.error("无法解析消息类型 {}", gson.toJson(msgMap));
        }
        switch (msgType) {
            case MessageUtil.REQ_MESSAGE_TYPE_EVENT:
                eventDispatcher.dispatchEvent(msgMap);
                break;
            case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
                // 处理文字消息
                return processTextMsg(msgMap);
            case MessageUtil.REQ_MESSAGE_TYPE_IMAGE:
                // 处理图片消息
                break;
            case MessageUtil.REQ_MESSAGE_TYPE_LINK:
                // 处理连接消息
                break;
            case MessageUtil.REQ_MESSAGE_TYPE_LOCATION:
                // 处理位置消息
                break;
            case MessageUtil.REQ_MESSAGE_TYPE_VOICE:
                // 处理语音消息
                break;
            default:
                LOGGER.error("暂不支持该类型消息: {}", gson.toJson(msgMap));
        }

        return "";
    }

    private String processTextMsg(Map<String, String> msgMap) {
        String openid = msgMap.get(MessageUtil.FROM_USER_NAME); //用户 openid
        String mpid = msgMap.get(MessageUtil.TO_USER_NAME);   //公众号原始 ID

        //普通文本消息

        TextRespMessage txtmsg = new TextRespMessage();
        txtmsg.setToUserName(openid);
        txtmsg.setFromUserName(mpid);
        txtmsg.setCreateTime(new Date().getTime());
        txtmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

        txtmsg.setContent("你是小咪~~~~~~~~~~");
        return MessageUtil.textMessageToXml(txtmsg);

    }

}
