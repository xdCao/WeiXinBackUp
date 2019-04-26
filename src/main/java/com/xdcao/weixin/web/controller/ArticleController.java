package com.xdcao.weixin.web.controller;

import com.xdcao.weixin.base.ApiResponse;
import com.xdcao.weixin.base.ServiceMultiRet;
import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.bo.ArticleBO;
import com.xdcao.weixin.service.IArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @Author: buku.ch
 * @Date: 2019-04-26 15:35
 */

@Controller
@CrossOrigin("*")
@RequestMapping("/article")
public class ArticleController {

    @Value("${web.upload-path}")
    private String uploadPath;

    @Value("${site.prefix}")
    private String site_prefix;

    @Autowired
    private IArticleService articleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);


    @GetMapping("/list")
    @ResponseBody
    public ApiResponse listArticles(@RequestParam("start") Integer start, @RequestParam("size") Integer size) {
        ServiceMultiRet<ArticleBO> ret = articleService.listAllArticlesByPage(start,size);
        return new ApiResponse(ret);
    }

    @PostMapping("/add")
    @ResponseBody
    public ApiResponse addNewArticle(HttpServletRequest request,
                                     @RequestParam("data") String content,
                                     @RequestParam("title") String title) {

        if (content == null || content.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        if (title == null || title.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        ServiceResult serviceResult = articleService.addNewArticle(content,title);

        if (serviceResult.isSuccess()) {
            return new ApiResponse(ApiResponse.Status.SUCCESS);
        }

        return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
    }


    @PostMapping(value = "/upload/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse uploadPhoto(@RequestParam MultipartFile file) {

        if (file.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.NON_VALID_PARAM);
        }

        String fileName = +System.currentTimeMillis() + file.getOriginalFilename();
        /*本地上传*/
        File target = new File(uploadPath + fileName);
        try {
            file.transferTo(target);
        } catch (Exception e) {
            LOGGER.error("fileUpload error:  fileName: {}", fileName, e);
            return new ApiResponse(ApiResponse.Status.INTERNAL_SERVER_ERROR);
        }
        LOGGER.info("图片上传成功");
        return new ApiResponse(site_prefix + "static/" + fileName);
    }



}
