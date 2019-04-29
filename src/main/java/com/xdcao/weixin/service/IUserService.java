package com.xdcao.weixin.service;

import com.xdcao.weixin.base.ServiceMultiRet;
import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.bo.UserBO;

import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-04-27 12:18
 */


public interface IUserService {

    ServiceResult addNewUser(String openId, String name, int department);

    ServiceResult addScore(Integer score, String openId, Integer articleId);

    ServiceMultiRet<UserBO> listUsersByPage(Integer start, Integer size);

    ServiceResult<Boolean> checkUser(String openId);

    List<UserBO> findUsersByDepartment(int departmentsValue);
}
