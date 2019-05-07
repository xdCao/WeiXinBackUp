package com.xdcao.weixin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xdcao.weixin.base.ServiceMultiRet;
import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.bo.*;
import com.xdcao.weixin.dao.ArticleBOMapper;
import com.xdcao.weixin.dao.UserArticleBOMapper;
import com.xdcao.weixin.dao.UserBOMapper;
import com.xdcao.weixin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-04-27 12:43
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserBOMapper userBOMapper;

    @Autowired
    private ArticleBOMapper articleBOMapper;

    @Autowired
    private UserArticleBOMapper userArticleBOMapper;

    @Override
    @Transactional
    public ServiceResult addNewUser(String openId, String name, int department) {

        List<UserBO> userBOList = getUserBOSByOpenId(openId);
        if (userBOList != null && !userBOList.isEmpty()) {
            return new ServiceResult(false, "你已经注册过");
        }

        Date now = new Date();
        UserBO userBO = new UserBO();
        userBO.setDepartment(department);
        userBO.setName(name);
        userBO.setOpenId(openId);
        userBO.setTotalScore(0);
        userBO.setCreateTime(now);
        userBO.setUpdateTime(now);
        userBOMapper.insert(userBO);
        return new ServiceResult(true);
    }



    @Override
    @Transactional
    public ServiceResult addScore(Integer score, String openId, Integer articleId) {
        List<UserBO> boList = getUserBOSByOpenId(openId);
        if (boList == null || boList.isEmpty()) {
            return new ServiceResult(false,"找不到该用户");
        }
        UserBO userBO = boList.get(0);

        ArticleBO articleBO = articleBOMapper.selectByPrimaryKey(articleId);
        if (articleBO == null) {
            return new ServiceResult(false,"找不到该文章");
        }

        /*看用户是否因为这篇文章得过分*/
        UserArticleBOExample example = new UserArticleBOExample();
        example.createCriteria().andArticleIdEqualTo(articleId).andUserIdEqualTo(userBO.getId());
        List<UserArticleBO> userArticleBOS = userArticleBOMapper.selectByExample(example);
        if (userArticleBOS == null || userArticleBOS.isEmpty()) {
            userBO.setTotalScore(userBO.getTotalScore()+score);
            userBOMapper.updateByPrimaryKey(userBO);
            UserArticleBO userArticleBO = new UserArticleBO();
            userArticleBO.setArticleId(articleId);
            userArticleBO.setUserId(userBO.getId());
            userArticleBO.setCreateTime(new Date());
            userArticleBO.setUpdateTime(new Date());
            userArticleBOMapper.insert(userArticleBO);
            return new ServiceResult(true);
        }

        return new ServiceResult(true,"用户已经用这篇文章得过分");

    }

    @Override
    public ServiceMultiRet<UserBO> listUsersByPage(Integer start, Integer size) {
        List<UserBO> result = new ArrayList<>();
        PageHelper.startPage(start, size);
        List<UserBO> userBOS = userBOMapper.selectByExample(new UserBOExample());
        if (userBOS == null || userBOS.isEmpty()) {
            return new ServiceMultiRet<>(0, result);
        }
        PageInfo<UserBO> pageInfo = new PageInfo<>(userBOS);
        result.addAll(pageInfo.getList());
        return new ServiceMultiRet<>(pageInfo.getTotal(), result);
    }

    @Override
    public ServiceResult<Boolean> checkUser(String openId) {
        List<UserBO> userBOSByOpenId = getUserBOSByOpenId(openId);
        if (userBOSByOpenId == null || userBOSByOpenId.isEmpty()) {
            return new ServiceResult<Boolean>(false);
        }
        return new ServiceResult<>(true);
    }

    @Override
    public List<UserBO> findUsersByDepartment(int departmentsValue) {
        UserBOExample example = new UserBOExample();
        example.createCriteria().andDepartmentEqualTo(departmentsValue);
        List<UserBO> userBOS = userBOMapper.selectByExample(example);
        return userBOS;
    }

    @Override
    public List<UserBO> findAllUsers() {
        List<UserBO> userBOS = userBOMapper.selectByExample(new UserBOExample());
        return userBOS;
    }


    @Override
    public List<UserBO> getUserBOSByOpenId(String openId) {
        UserBOExample example = new UserBOExample();
        example.createCriteria().andOpenIdEqualTo(openId);
        return userBOMapper.selectByExample(example);
    }
}
