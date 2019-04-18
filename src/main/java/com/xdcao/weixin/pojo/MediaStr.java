package com.xdcao.weixin.pojo;

import com.google.gson.annotations.SerializedName;
import org.springframework.stereotype.Service;

/**
 * @Author: buku.ch
 * @Date: 2019-04-18 12:59
 */


public class MediaStr {

    @SerializedName("media_id")
    private String mediaId;

    public MediaStr() {
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
