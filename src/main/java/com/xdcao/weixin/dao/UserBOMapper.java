package com.xdcao.weixin.dao;

import com.xdcao.weixin.bo.UserBO;
import com.xdcao.weixin.bo.UserBOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserBOMapper {
    long countByExample(UserBOExample example);

    int deleteByExample(UserBOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserBO record);

    int insertSelective(UserBO record);

    List<UserBO> selectByExample(UserBOExample example);

    UserBO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserBO record, @Param("example") UserBOExample example);

    int updateByExample(@Param("record") UserBO record, @Param("example") UserBOExample example);

    int updateByPrimaryKeySelective(UserBO record);

    int updateByPrimaryKey(UserBO record);
}