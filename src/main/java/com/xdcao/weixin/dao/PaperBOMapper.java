package com.xdcao.weixin.dao;

import com.xdcao.weixin.bo.PaperBO;
import com.xdcao.weixin.bo.PaperBOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PaperBOMapper {
    long countByExample(PaperBOExample example);

    int deleteByExample(PaperBOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PaperBO record);

    int insertSelective(PaperBO record);

    List<PaperBO> selectByExample(PaperBOExample example);

    PaperBO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PaperBO record, @Param("example") PaperBOExample example);

    int updateByExample(@Param("record") PaperBO record, @Param("example") PaperBOExample example);

    int updateByPrimaryKeySelective(PaperBO record);

    int updateByPrimaryKey(PaperBO record);
}