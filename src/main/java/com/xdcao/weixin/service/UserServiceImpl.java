package com.xdcao.weixin.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xdcao.weixin.base.ServiceMultiRet;
import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.bo.UserBO;
import com.xdcao.weixin.bo.UserBOExample;
import com.xdcao.weixin.dao.UserBOMapper;
import org.apache.tomcat.jni.User;
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
    public ServiceResult addScore(Integer score, String openId) {
        List<UserBO> boList = getUserBOSByOpenId(openId);
        if (boList == null || boList.isEmpty()) {
            return new ServiceResult(false,"找不到该用户");
        }
        UserBO userBO = boList.get(0);
        userBO.setTotalScore(userBO.getTotalScore()+score);
        userBOMapper.updateByPrimaryKey(userBO);
        return new ServiceResult(true);
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


    private List<UserBO> getUserBOSByOpenId(String openId) {
        UserBOExample example = new UserBOExample();
        example.createCriteria().andOpenIdEqualTo(openId);
        return userBOMapper.selectByExample(example);
    }
}
