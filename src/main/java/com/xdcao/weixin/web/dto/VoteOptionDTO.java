package com.xdcao.weixin.web.dto;

/**
 * @Author: buku.ch
 * @Date: 2019-05-06 15:52
 */


public class VoteOptionDTO {

    private Integer id;

    private String content;

    private Integer index;

    private Integer count;

    public VoteOptionDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
