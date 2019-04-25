package com.xdcao.weixin.web.dto;

import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-04-25 22:09
 */


public class QuestionDTO {

    private Integer id;

    private String content;

    private Integer muliti;

    private Integer score;

    private Integer indexNum;

    private List<OptionDTO> optionDTOS;

    public QuestionDTO() {
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

    public Integer getMuliti() {
        return muliti;
    }

    public void setMuliti(Integer muliti) {
        this.muliti = muliti;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Integer indexNum) {
        this.indexNum = indexNum;
    }

    public List<OptionDTO> getOptionDTOS() {
        return optionDTOS;
    }

    public void setOptionDTOS(List<OptionDTO> optionDTOS) {
        this.optionDTOS = optionDTOS;
    }
}
