package com.xdcao.weixin.web.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * @Author: buku.ch
 * @Date: 2019-04-25 22:10
 */


public class OptionDTO {

    private Integer id;

    private String content;

    private Boolean correct;

    private Integer indexNum;

    public OptionDTO() {
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

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Integer getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Integer indexNum) {
        this.indexNum = indexNum;
    }
}
