package com.xdcao.weixin.pojo.resp;

/**
 * @Author: buku.ch
 * @Date: 2019-04-17 22:59
 */


public class PicRespMessage extends BaseRespMessage {

    private Picture picture;

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
