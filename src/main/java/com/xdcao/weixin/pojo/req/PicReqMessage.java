package com.xdcao.weixin.pojo.req;

/**
 * @Author: buku.ch
 * @Date: 2019-04-17 22:27
 */


public class PicReqMessage extends BaseReqMessage {

    // 图片链接
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

}
