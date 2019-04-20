package com.xdcao.weixin.pojo.resp;

/**
 * @Author: buku.ch
 * @Date: 2019-04-17 22:59
 */


public class PicRespMessage extends BaseRespMessage {

    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        this.Image = image;
    }
}
