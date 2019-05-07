package com.xdcao.weixin.web.dto;

import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-05-06 15:52
 */


public class VoteDTO {

    private Integer voteId;

    private String title;

    private List<VoteOptionDTO> voteOptionDTOS;

    public VoteDTO() {
    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<VoteOptionDTO> getVoteOptionDTOS() {
        return voteOptionDTOS;
    }

    public void setVoteOptionDTOS(List<VoteOptionDTO> voteOptionDTOS) {
        this.voteOptionDTOS = voteOptionDTOS;
    }
}
