package com.xdcao.weixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: buku.ch
 * @Date: 2019-04-17 22:31
 */

@Component
public class MsgDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgDispatcher.class);

    @Autowired
    private EventDispatcher eventDispatcher;

    public void dispatchMsg(String msg) {
        switch (msg) {
            case MessageUtil.REQ_MESSAGE_TYPE_EVENT:
                eventDispatcher.dispatchEvent(msg);
                break;
            case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
                // 处理文字消息
                break;
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
                    LOGGER.error("暂不支持该类型消息: {}", msg);
        }
    }

}
