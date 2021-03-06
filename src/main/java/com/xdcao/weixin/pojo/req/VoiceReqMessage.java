package com.xdcao.weixin.pojo.req;

/**
 * @Author: buku.ch
 * @Date: 2019-04-17 22:29
 */


public class VoiceReqMessage extends BaseReqMessage {

    // 媒体 ID
    private String MediaId;
    // 语音格式
    private String Format;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

}
