package com.xdcao.weixin.pojo.resp;

/**
 * @Author: buku.ch
 * @Date: 2019-04-17 22:57
 */


public class TextRespMessage extends BaseRespMessage {

    // 回复的消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
