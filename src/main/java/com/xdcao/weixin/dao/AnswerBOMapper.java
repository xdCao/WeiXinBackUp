package com.xdcao.weixin.dao;

import com.xdcao.weixin.bo.AnswerBO;
import com.xdcao.weixin.bo.AnswerBOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AnswerBOMapper {
    long countByExample(AnswerBOExample example);

    int deleteByExample(AnswerBOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AnswerBO record);

    int insertSelective(AnswerBO record);

    List<AnswerBO> selectByExample(AnswerBOExample example);

    AnswerBO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AnswerBO record, @Param("example") AnswerBOExample example);

    int updateByExample(@Param("record") AnswerBO record, @Param("example") AnswerBOExample example);

    int updateByPrimaryKeySelective(AnswerBO record);

    int updateByPrimaryKey(AnswerBO record);
}