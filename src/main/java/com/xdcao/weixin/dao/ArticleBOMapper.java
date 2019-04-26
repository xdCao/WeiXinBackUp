package com.xdcao.weixin.dao;

import com.xdcao.weixin.bo.ArticleBO;
import com.xdcao.weixin.bo.ArticleBOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleBOMapper {
    long countByExample(ArticleBOExample example);

    int deleteByExample(ArticleBOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleBO record);

    int insertSelective(ArticleBO record);

    List<ArticleBO> selectByExampleWithBLOBs(ArticleBOExample example);

    List<ArticleBO> selectByExample(ArticleBOExample example);

    ArticleBO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticleBO record, @Param("example") ArticleBOExample example);

    int updateByExampleWithBLOBs(@Param("record") ArticleBO record, @Param("example") ArticleBOExample example);

    int updateByExample(@Param("record") ArticleBO record, @Param("example") ArticleBOExample example);

    int updateByPrimaryKeySelective(ArticleBO record);

    int updateByPrimaryKeyWithBLOBs(ArticleBO record);

    int updateByPrimaryKey(ArticleBO record);
}