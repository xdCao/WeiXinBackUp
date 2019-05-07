package com.xdcao.weixin.dao;

import com.xdcao.weixin.bo.Vote;
import com.xdcao.weixin.bo.VoteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VoteMapper {
    long countByExample(VoteExample example);

    int deleteByExample(VoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Vote record);

    int insertSelective(Vote record);

    List<Vote> selectByExample(VoteExample example);

    Vote selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Vote record, @Param("example") VoteExample example);

    int updateByExample(@Param("record") Vote record, @Param("example") VoteExample example);

    int updateByPrimaryKeySelective(Vote record);

    int updateByPrimaryKey(Vote record);
}