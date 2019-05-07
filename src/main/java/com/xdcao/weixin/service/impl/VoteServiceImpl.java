package com.xdcao.weixin.service.impl;

import com.xdcao.weixin.base.ServiceMultiRet;
import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.bo.*;
import com.xdcao.weixin.dao.UserVoteOptionMapper;
import com.xdcao.weixin.dao.VoteMapper;
import com.xdcao.weixin.dao.VoteOptionMapper;
import com.xdcao.weixin.service.IUserService;
import com.xdcao.weixin.service.IVoteService;
import com.xdcao.weixin.web.VoteOptionPair;
import com.xdcao.weixin.web.dto.VoteDTO;
import com.xdcao.weixin.web.dto.VoteOptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-05-06 10:35
 */

@Service
public class VoteServiceImpl implements IVoteService {

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private VoteOptionMapper voteOptionMapper;

    @Autowired
    private UserVoteOptionMapper userVoteOptionMapper;

    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public ServiceResult addNewVote(String title, List<VoteOptionPair> optionPairs) {
        Vote vote = new Vote();
        vote.setTitle(title);
        vote.setCreateTime(new Date());
        vote.setUpdateTime(new Date());
        voteMapper.insert(vote);

        optionPairs.forEach(optionPair -> {
            VoteOption voteOption = new VoteOption();
            voteOption.setContent(optionPair.getContent());
            voteOption.setVoteCount(0);
            voteOption.setVoteIndex((byte)(int)optionPair.getIndex());
            voteOption.setCreateTime(new Date());
            voteOption.setUpdateTime(new Date());
            voteOption.setVoteId(vote.getId());
            voteOptionMapper.insert(voteOption);
        });

        return new ServiceResult(true);

    }

    @Override
    public ServiceMultiRet<VoteDTO> listAllVotes() {

        List<VoteDTO> ret = new ArrayList<>();

        List<Vote> votes = voteMapper.selectByExample(new VoteExample());
        if (votes == null || votes.isEmpty()) {
            return new ServiceMultiRet<>(0, ret);
        }

        votes.forEach(vote -> {
            VoteOptionExample example = new VoteOptionExample();
            example.createCriteria().andVoteIdEqualTo(vote.getId());
            List<VoteOption> voteOptions = voteOptionMapper.selectByExample(example);
            VoteDTO voteDTO = new VoteDTO();
            voteDTO.setTitle(vote.getTitle());
            voteDTO.setVoteId(vote.getId());
            List<VoteOptionDTO> dtos = new ArrayList<>();
            voteOptions.forEach(voteOption -> {
                VoteOptionDTO dto = new VoteOptionDTO();
                dto.setContent(voteOption.getContent());
                dto.setCount(voteOption.getVoteCount());
                dto.setId(voteOption.getId());
                dto.setIndex((int)voteOption.getVoteIndex());
                dtos.add(dto);
            });
            voteDTO.setVoteOptionDTOS(dtos);
            ret.add(voteDTO);
        });

        return new ServiceMultiRet<>(ret.size(), ret);



    }

    @Override
    @Transactional
    public ServiceResult userVote(String openId, Integer voteOptionId) {
        List<UserBO> userBOSByOpenId = userService.getUserBOSByOpenId(openId);
        if (userBOSByOpenId == null || userBOSByOpenId.isEmpty()) {
            return new ServiceResult(false,"没有该用户的信息");
        }

        VoteOption voteOption = voteOptionMapper.selectByPrimaryKey(voteOptionId);
        if (voteOption == null) {
            return new ServiceResult(false,"没有该选项的信息");
        }

        UserBO userBO = userBOSByOpenId.get(0);
        UserVoteOptionExample example = new UserVoteOptionExample();
        example.createCriteria().andUserIdEqualTo(userBO.getId()).andVoteOptionIdEqualTo(voteOptionId);
        List<UserVoteOption> userVoteOptions = userVoteOptionMapper.selectByExample(example);

        if (userVoteOptions == null || userVoteOptions.isEmpty()) {
            UserVoteOption userVoteOption = new UserVoteOption();
            userVoteOption.setVoteId(voteOption.getVoteId());
            userVoteOption.setUserId(userBO.getId());
            userVoteOption.setVoteOptionId(voteOptionId);
            userVoteOption.setCreateTime(new Date());
            userVoteOption.setUpdateTime(new Date());
            userVoteOptionMapper.insert(userVoteOption);
            return new ServiceResult(true);
        }

        return new ServiceResult(false,"该用户已经投过票");

    }

    @Override
    @Transactional
    public ServiceResult deleteVote(Integer voteId) {
        VoteOptionExample example1 = new VoteOptionExample();
        example1.createCriteria().andVoteIdEqualTo(voteId);
        voteOptionMapper.deleteByExample(example1);
        voteMapper.deleteByPrimaryKey(voteId);
        return new ServiceResult(true);
    }
}
