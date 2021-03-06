package com.xdcao.weixin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xdcao.weixin.base.*;
import com.xdcao.weixin.bo.UserBO;
import com.xdcao.weixin.service.IUserService;
import com.xdcao.weixin.service.IVoteService;
import com.xdcao.weixin.web.dto.DepartmentPair;
import org.omg.CORBA.BAD_CONTEXT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-04-27 12:08
 */

@Controller
@CrossOrigin(origins = "*",allowCredentials="true",allowedHeaders = "*",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.OPTIONS,RequestMethod.PUT,RequestMethod.DELETE})
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private IVoteService voteService;

    @Value("${appID}")
    private String appId;

    @Value("${appsecret}")
    private String appSecret;

    @GetMapping("/open_id")
    @ResponseBody
    public ApiResponse getOpenId(@RequestParam("code") String code) {
        if (code == null || code.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }
        try {
            String openIdJson = OpenIdUtil.getopenid(appId,  code, appSecret);
            LOGGER.info("获取用户的open_id: {}  code: {}",openIdJson,code);

            JSONObject jsonObject = JSONObject.parseObject(openIdJson);
            String openId=jsonObject.getString("openid");
            return new ApiResponse(openId);
        }catch (Exception e) {
            LOGGER.error("获取用户openId失败: code:  {}", code, e);
            return new ApiResponse(ApiResponse.Status.NOT_FOUND);
        }
    }

    @GetMapping("/get")
    @ResponseBody
    public ApiResponse getUser(@RequestParam("open_id") String openId) {

        if (openId == null || openId.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        List<UserBO> userBOSByOpenId = userService.getUserBOSByOpenId(openId);
        if (userBOSByOpenId == null || userBOSByOpenId.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }
        return new ApiResponse(userBOSByOpenId.get(0));
    }

    @PostMapping("/update")
    @ResponseBody
    public ApiResponse updateUser(@RequestParam("open_id") String openId,
                                  @RequestParam("name") String name,
                                  @RequestParam("department") Integer department,
                                  @RequestParam("age") Integer age,
                                  @RequestParam("gender") Integer gender,
                                  @RequestParam(value = "work_id", required = false) String workId) {
        if (openId == null || openId.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        if (name == null || name.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        if (Departments.of(department).getValue() == Departments.OTHERS.getValue()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        if (age == null || age <= 0) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        if (gender == null || gender < 0 || gender > 1) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        ServiceResult ret = userService.updateUserByOpenId(openId,name,department,age,gender,workId);

        if (ret.isSuccess()) {
            return new ApiResponse(ApiResponse.Status.SUCCESS);
        }

        return new ApiResponse(ApiResponse.Status.BAD_REQUEST,ret.getMessage());

    }


    @PostMapping("/register")
    @ResponseBody
    public ApiResponse register(@RequestParam("open_id") String openId,
                                @RequestParam("name") String name,
                                @RequestParam("department") Integer department,
                                @RequestParam("age") Integer age,
                                @RequestParam("gender") Integer gender,
                                @RequestParam(value = "work_id", required = false) String workId,
                                HttpServletRequest request,
                                HttpServletResponse resp) {

//        RespUtils.crossover(request,resp);

        if (openId == null || openId.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        if (name == null || name.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        if (Departments.of(department).getValue() == Departments.OTHERS.getValue()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        if (age == null || age <= 0) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        if (gender == null || gender < 0 || gender > 1) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        ServiceResult ret = userService.addNewUser(openId,name,department,age,gender,workId);

        if (ret.isSuccess()) {
            return new ApiResponse(ApiResponse.Status.SUCCESS);
        }

        return new ApiResponse(ApiResponse.Status.BAD_REQUEST,ret.getMessage());

    }

    @PostMapping("/score/add")
    @ResponseBody
    public ApiResponse addScore(@RequestParam("score") Integer score,
                                @RequestParam("open_id") String openId,
                                @RequestParam("article_id") Integer articleId,
                                HttpServletRequest request,
                                HttpServletResponse resp) {

//        RespUtils.crossover(request,resp);

        if (score < 1) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        ServiceResult result = userService.addScore(score,openId,articleId);
        if (result.isSuccess()) {
            return new ApiResponse(ApiResponse.Status.SUCCESS,result.getMessage());
        }

        return new ApiResponse(ApiResponse.Status.BAD_REQUEST,result.getMessage());
    }


    @GetMapping("/departments")
    @ResponseBody
    public ApiResponse getDepartments() {
        List<DepartmentPair> ret = new ArrayList<>();
        for (Departments departments : Departments.values()) {
            if (departments.getValue() == Departments.OTHERS.getValue()) {
                continue;
            }

            DepartmentPair pair = new DepartmentPair(departments.getName(), departments.getValue());
            ret.add(pair);
        }
        return new ApiResponse(ret);
    }

    @PostMapping("/vote/incr")
    @ResponseBody
    public ApiResponse userVote(@RequestParam("open_id") String openId,
                                @RequestParam("vote_option_id") Integer voteOptionId) {

        if (openId == null || openId.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        if (voteOptionId == null || voteOptionId < 0) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        ServiceResult result = voteService.userVote(openId,voteOptionId);
        if (result.isSuccess()) {
            return new ApiResponse(ApiResponse.Status.SUCCESS);
        }
        return new ApiResponse(ApiResponse.Status.BAD_REQUEST,result.getMessage());
    }







}
