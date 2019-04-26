package com.xdcao.weixin.service;

import com.xdcao.weixin.base.ServiceMultiRet;
import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.bo.ArticleBO;

/**
 * @Author: buku.ch
 * @Date: 2019-04-26 20:55
 */


public interface IArticleService {

    ServiceResult addNewArticle(String content, String title);

    ServiceMultiRet<ArticleBO> listAllArticlesByPage(Integer start, Integer size);
}
