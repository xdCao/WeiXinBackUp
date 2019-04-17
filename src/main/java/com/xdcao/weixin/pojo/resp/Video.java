package com.xdcao.weixin.pojo.resp;

/**
 * @Author: buku.ch
 * @Date: 2019-04-17 23:01
 */


public class Video {

    private String MediaId;
    private String Title;
    private String Description;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

}
