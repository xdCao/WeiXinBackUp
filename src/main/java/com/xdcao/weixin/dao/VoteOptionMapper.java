package com.xdcao.weixin.dao;

import com.xdcao.weixin.bo.VoteOption;
import com.xdcao.weixin.bo.VoteOptionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VoteOptionMapper {
    long countByExample(VoteOptionExample example);

    int deleteByExample(VoteOptionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VoteOption record);

    int insertSelective(VoteOption record);

    List<VoteOption> selectByExample(VoteOptionExample example);

    VoteOption selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VoteOption record, @Param("example") VoteOptionExample example);

    int updateByExample(@Param("record") VoteOption record, @Param("example") VoteOptionExample example);

    int updateByPrimaryKeySelective(VoteOption record);

    int updateByPrimaryKey(VoteOption record);
}