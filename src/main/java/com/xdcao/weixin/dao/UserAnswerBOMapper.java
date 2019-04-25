package com.xdcao.weixin.dao;

import com.xdcao.weixin.bo.UserAnswerBO;
import com.xdcao.weixin.bo.UserAnswerBOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAnswerBOMapper {
    long countByExample(UserAnswerBOExample example);

    int deleteByExample(UserAnswerBOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserAnswerBO record);

    int insertSelective(UserAnswerBO record);

    List<UserAnswerBO> selectByExample(UserAnswerBOExample example);

    UserAnswerBO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserAnswerBO record, @Param("example") UserAnswerBOExample example);

    int updateByExample(@Param("record") UserAnswerBO record, @Param("example") UserAnswerBOExample example);

    int updateByPrimaryKeySelective(UserAnswerBO record);

    int updateByPrimaryKey(UserAnswerBO record);
}