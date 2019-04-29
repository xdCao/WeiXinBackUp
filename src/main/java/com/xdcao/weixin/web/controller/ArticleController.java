package com.xdcao.weixin.web.controller;

import com.xdcao.weixin.base.ApiResponse;
import com.xdcao.weixin.base.RespUtils;
import com.xdcao.weixin.base.ServiceMultiRet;
import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.bo.ArticleBO;
import com.xdcao.weixin.service.IArticleService;
import com.xdcao.weixin.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @Author: buku.ch
 * @Date: 2019-04-26 15:35
 */

@Controller
@CrossOrigin(origins = "*",allowCredentials="true",allowedHeaders = "*",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.OPTIONS,RequestMethod.PUT,RequestMethod.DELETE})
@RequestMapping("/article")
public class ArticleController {

    @Value("${web.upload-path}")
    private String uploadPath;

    @Value("${site.prefix}")
    private String site_prefix;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IUserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);


    @GetMapping("/list")
    @ResponseBody
    public ApiResponse listArticles(@RequestParam("start") Integer start,
                                    @RequestParam("size") Integer size,
                                    @RequestParam("open_id") String openId,
                                    HttpServletRequest request,
                                    HttpServletResponse resp) {

//        RespUtils.crossover(request,resp);

        ServiceResult<Boolean> checkRet = userService.checkUser(openId);
        if (!checkRet.isSuccess()) {
            return new ApiResponse(ApiResponse.Status.NOT_LOGIN);
        }

        ServiceMultiRet<ArticleBO> ret = articleService.listAllArticlesByPage(start,size);
        return new ApiResponse(ret);
    }





}
