package com.xdcao.weixin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xdcao.weixin.base.ServiceMultiRet;
import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.bo.ArticleBO;
import com.xdcao.weixin.bo.ArticleBOExample;
import com.xdcao.weixin.dao.ArticleBOMapper;
import com.xdcao.weixin.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-04-26 20:57
 */

@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleBOMapper articleBOMapper;

    @Override
    @Transactional
    public ServiceResult addNewArticle(String content, String title) {
        Date now = new Date();
        ArticleBO articleBO = new ArticleBO();
        articleBO.setContent(content);
        articleBO.setTitle(title);
        articleBO.setScore(1);
        articleBO.setCreateTime(now);
        articleBO.setUpdateTime(now);
        articleBO.setViewedTimes(0);
        articleBOMapper.insert(articleBO);
        return new ServiceResult(true);
    }

    @Override
    public ServiceMultiRet<ArticleBO> listAllArticlesByPage(Integer start, Integer size) {

        List<ArticleBO> ret = new ArrayList<>();

        PageHelper.startPage(start, size);
        List<ArticleBO> articleBOS = articleBOMapper.selectByExampleWithBLOBs(new ArticleBOExample());

        if (articleBOS == null || articleBOS.isEmpty()) {
            return new ServiceMultiRet<>(0, ret);
        }

        PageInfo<ArticleBO> pageInfo = new PageInfo<>(articleBOS);
        ret.addAll(pageInfo.getList());
        return new ServiceMultiRet<>(pageInfo.getTotal(), ret);

    }

    @Override
    @Transactional
    public ServiceResult deleteArticle(Integer articleId) {
        articleBOMapper.deleteByPrimaryKey(articleId);
        return new ServiceResult(true);
    }
}
