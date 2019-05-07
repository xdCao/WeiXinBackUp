package com.xdcao.weixin.web;

/**
 * @Author: buku.ch
 * @Date: 2019-05-06 10:30
 */


public class VoteOptionPair {

    private Integer index;

    private String content;

    public VoteOptionPair() {
    }

    public VoteOptionPair(Integer index, String content) {
        this.index = index;
        this.content = content;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
