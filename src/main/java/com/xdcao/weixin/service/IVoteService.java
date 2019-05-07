package com.xdcao.weixin.service;

import com.xdcao.weixin.base.ServiceMultiRet;
import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.web.VoteOptionPair;
import com.xdcao.weixin.web.dto.VoteDTO;

import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-05-06 10:31
 */


public interface IVoteService {

    ServiceResult addNewVote(String title, List<VoteOptionPair> optionPairs);

    ServiceMultiRet<VoteDTO> listAllVotes();

    ServiceResult userVote(String openId, Integer voteOptionId);

    ServiceResult deleteVote(Integer voteId);
}
