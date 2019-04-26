package com.xdcao.weixin.web.form;

import com.xdcao.weixin.web.dto.QuestionDTO;

import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-04-25 22:06
 */


public class PaperForm {

    private Integer id;

    private String name;

    private List<QuestionDTO> questionDTOs;

    public PaperForm() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QuestionDTO> getQuestionDTOs() {
        return questionDTOs;
    }

    public void setQuestionDTOs(List<QuestionDTO> questionDTOs) {
        this.questionDTOs = questionDTOs;
    }
}
