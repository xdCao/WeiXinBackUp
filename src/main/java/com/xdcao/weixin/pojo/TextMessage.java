package com.xdcao.weixin.pojo;

/**
 * @Author: buku.ch
 * @Date: 2019-04-17 22:29
 */


public class TextMessage extends BaseMessage{

    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
