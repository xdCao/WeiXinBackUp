package com.xdcao.weixin.web.controller;

import com.xdcao.weixin.base.ApiResponse;
import com.xdcao.weixin.base.ServiceMultiRet;
import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.bo.ArticleBO;
import com.xdcao.weixin.bo.UserBO;
import com.xdcao.weixin.service.IArticleService;
import com.xdcao.weixin.service.IExcelService;
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
import javax.servlet.http.HttpSession;
import java.io.*;

import static com.xdcao.weixin.base.RespUtils.crossover;

/**
 * @Author: buku.ch
 * @Date: 2019-04-27 13:10
 */

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = "*",allowCredentials="true",allowedHeaders = "*",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.OPTIONS,RequestMethod.PUT,RequestMethod.DELETE})
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    private static final String LOGIN_SESSION_KEY = "login_user";

    @Value("${web.upload-path}")
    private String uploadPath;

    @Value("${site.prefix}")
    private String site_prefix;

    @Autowired
    private IUserService userService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IExcelService excelService;

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.pwd}")
    private String adminPwd;


    @RequestMapping("/login")
    @ResponseBody
    public ApiResponse adminLogin(@RequestParam("name") String name,
                                  @RequestParam("password") String pwd, HttpSession session, HttpServletRequest req, HttpServletResponse resp) {

//        crossover(req, resp);
        if (name == null || name.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        if (pwd == null || pwd.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        if (name.equals(adminName) && pwd.equals(adminPwd)) {
            session.setAttribute(LOGIN_SESSION_KEY,name);
            return new ApiResponse(ApiResponse.Status.SUCCESS);
        }

        return new ApiResponse(ApiResponse.Status.NON_VALID_PARAM);

    }



    @GetMapping("/list/users")
    @ResponseBody
    public ApiResponse listUsers(@RequestParam("start") Integer start,
                                 @RequestParam("size") Integer size,
                                 HttpSession session,
                                 HttpServletRequest request,
                                 HttpServletResponse resp) {
//        crossover(request, resp);
        ApiResponse response = checkAdminLogin(session);
        if (response != null) {
            return response;
        }

        ServiceMultiRet<UserBO> users = userService.listUsersByPage(start,size);



        return new ApiResponse(users);
    }



    @GetMapping("/list/articles")
    @ResponseBody
    public ApiResponse listArticles(@RequestParam("start") Integer start,
                                    @RequestParam("size") Integer size,
                                    HttpSession session,
                                    HttpServletRequest request,
                                    HttpServletResponse resp) {

        ApiResponse response = checkAdminLogin(session);
        if (response != null) {
            return response;
        }
        ServiceMultiRet<ArticleBO> ret = articleService.listAllArticlesByPage(start,size);
        return new ApiResponse(ret);
    }


    @PostMapping("/add/article")
    @ResponseBody
    public ApiResponse addNewArticle(HttpServletRequest request,
                                     @RequestParam("data") String content,
                                     @RequestParam("title") String title,
                                     HttpServletResponse resp) {

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
    public ApiResponse uploadPhoto(@RequestParam MultipartFile file,
                                   HttpServletRequest request,
                                   HttpServletResponse resp) {

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



    private ApiResponse checkAdminLogin(HttpSession session) {
        String userName = (String)session.getAttribute(LOGIN_SESSION_KEY);
        if (userName == null || userName.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.NOT_LOGIN);
        }

        if (!userName.equals(adminName)) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }
        return null;
    }


    @GetMapping("/download/departments")
    @ResponseBody
    public String summaryExcelSDepartments(HttpServletResponse response) {
        try {

            ServiceResult<File> excelFile = excelService.summaryByDepartment();

            if (!excelFile.isSuccess()) {
                return null;
            }
            File result = excelFile.getResult();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(result));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(result.getName().getBytes("utf-8"), "ISO-8859-1"));
            response.addHeader("Content-Length", "" + result.length());
            response.setContentType("application/octet-stream");
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            os.write(buffer);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




}
