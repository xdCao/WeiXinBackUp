package com.xdcao.weixin.dao;

import com.xdcao.weixin.bo.UserArticleBO;
import com.xdcao.weixin.bo.UserArticleBOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserArticleBOMapper {
    long countByExample(UserArticleBOExample example);

    int deleteByExample(UserArticleBOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserArticleBO record);

    int insertSelective(UserArticleBO record);

    List<UserArticleBO> selectByExample(UserArticleBOExample example);

    UserArticleBO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserArticleBO record, @Param("example") UserArticleBOExample example);

    int updateByExample(@Param("record") UserArticleBO record, @Param("example") UserArticleBOExample example);

    int updateByPrimaryKeySelective(UserArticleBO record);

    int updateByPrimaryKey(UserArticleBO record);
}