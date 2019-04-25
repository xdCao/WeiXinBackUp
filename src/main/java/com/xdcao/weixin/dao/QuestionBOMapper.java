package com.xdcao.weixin.dao;

import java.util.List;

import com.xdcao.weixin.bo.QuestionBO;
import com.xdcao.weixin.bo.QuestionBOExample;
import org.apache.ibatis.annotations.Param;

public interface QuestionBOMapper {
    long countByExample(QuestionBOExample example);

    int deleteByExample(QuestionBOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuestionBO record);

    int insertSelective(QuestionBO record);

    List<QuestionBO> selectByExample(QuestionBOExample example);

    QuestionBO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QuestionBO record, @Param("example") QuestionBOExample example);

    int updateByExample(@Param("record") QuestionBO record, @Param("example") QuestionBOExample example);

    int updateByPrimaryKeySelective(QuestionBO record);

    int updateByPrimaryKey(QuestionBO record);
}