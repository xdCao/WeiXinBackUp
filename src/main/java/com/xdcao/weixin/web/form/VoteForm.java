package com.xdcao.weixin.web.form;

import com.xdcao.weixin.web.VoteOptionPair;

import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-05-06 10:57
 */


public class VoteForm {

    String title;

    List<VoteOptionPair> optionPairs;

    public VoteForm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<VoteOptionPair> getOptionPairs() {
        return optionPairs;
    }

    public void setOptionPairs(List<VoteOptionPair> optionPairs) {
        this.optionPairs = optionPairs;
    }
}
