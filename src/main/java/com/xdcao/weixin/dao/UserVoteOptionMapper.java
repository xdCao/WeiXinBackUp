package com.xdcao.weixin.dao;

import com.xdcao.weixin.bo.UserVoteOption;
import com.xdcao.weixin.bo.UserVoteOptionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserVoteOptionMapper {
    long countByExample(UserVoteOptionExample example);

    int deleteByExample(UserVoteOptionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserVoteOption record);

    int insertSelective(UserVoteOption record);

    List<UserVoteOption> selectByExample(UserVoteOptionExample example);

    UserVoteOption selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserVoteOption record, @Param("example") UserVoteOptionExample example);

    int updateByExample(@Param("record") UserVoteOption record, @Param("example") UserVoteOptionExample example);

    int updateByPrimaryKeySelective(UserVoteOption record);

    int updateByPrimaryKey(UserVoteOption record);
}