package com.xdcao.weixin.dao;

import com.xdcao.weixin.bo.OptionBO;
import com.xdcao.weixin.bo.OptionBOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OptionBOMapper {
    long countByExample(OptionBOExample example);

    int deleteByExample(OptionBOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OptionBO record);

    int insertSelective(OptionBO record);

    List<OptionBO> selectByExample(OptionBOExample example);

    OptionBO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OptionBO record, @Param("example") OptionBOExample example);

    int updateByExample(@Param("record") OptionBO record, @Param("example") OptionBOExample example);

    int updateByPrimaryKeySelective(OptionBO record);

    int updateByPrimaryKey(OptionBO record);
}