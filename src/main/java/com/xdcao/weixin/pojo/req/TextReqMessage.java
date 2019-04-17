package com.xdcao.weixin.pojo.req;

/**
 * @Author: buku.ch
 * @Date: 2019-04-17 22:29
 */


public class TextReqMessage extends BaseReqMessage {

    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
