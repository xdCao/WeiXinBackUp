package com.xdcao.weixin.web.controller;

import com.xdcao.weixin.base.ApiResponse;
import com.xdcao.weixin.base.RespUtils;
import com.xdcao.weixin.base.ServiceMultiRet;
import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.bo.ArticleBO;
import com.xdcao.weixin.bo.UserBO;
import com.xdcao.weixin.service.IArticleService;
import com.xdcao.weixin.service.IExcelService;
import com.xdcao.weixin.service.IUserService;
import com.xdcao.weixin.service.IVoteService;
import com.xdcao.weixin.web.VoteOptionPair;
import com.xdcao.weixin.web.dto.VoteDTO;
import com.xdcao.weixin.web.form.VoteForm;
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
import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-04-27 13:10
 */

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.DELETE})
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

    @Autowired
    private IVoteService voteService;

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
            session.setAttribute(LOGIN_SESSION_KEY, name);
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

        ServiceMultiRet<UserBO> users = userService.listUsersByPage(start, size);


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
        ServiceMultiRet<ArticleBO> ret = articleService.listAllArticlesByPage(start, size);
        return new ApiResponse(ret);
    }

    @PostMapping("/delete/article")
    @ResponseBody
    public ApiResponse deleteArticle(@RequestParam("article_id") Integer articleId) {
        ServiceResult result = articleService.deleteArticle(articleId);
        if (result.isSuccess()) {
            return new ApiResponse(ApiResponse.Status.SUCCESS);
        }
        return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
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

        ServiceResult serviceResult = articleService.addNewArticle(content, title);

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

    @PostMapping("/add/vote")
    @ResponseBody
    public ApiResponse addNewVote(@RequestBody VoteForm voteForm) {

        if (voteForm == null) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        String voteTitle = voteForm.getTitle();
        List<VoteOptionPair> optionPairs = voteForm.getOptionPairs();


        if (voteTitle == null || voteTitle.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        if (optionPairs == null || optionPairs.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }

        ServiceResult result = voteService.addNewVote(voteTitle, optionPairs);

        if (result.isSuccess()) {
            return new ApiResponse(ApiResponse.Status.SUCCESS);
        }

        return new ApiResponse(ApiResponse.Status.BAD_REQUEST, result.getMessage());

    }

    @PostMapping("/delete/vote")
    @ResponseBody
    public ApiResponse deleteVote(@RequestParam("vote_id") Integer voteId) {
        ServiceResult result = voteService.deleteVote(voteId);
        if (result.isSuccess()) {
            return new ApiResponse(ApiResponse.Status.SUCCESS);
        }
        return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
    }


    @GetMapping("/list/votes")
    @ResponseBody
    public ApiResponse listVotesAndOptions() {
        ServiceMultiRet<VoteDTO> votes = voteService.listAllVotes();
        return new ApiResponse(votes.getResult());
    }


    @GetMapping("/download/departments")
    @ResponseBody
    public String summaryExcelSDepartments(HttpServletResponse response, HttpServletRequest request) {
        try {

            ServiceResult<File> excelFile = excelService.summaryByDepartment();

            if (!excelFile.isSuccess()) {
                return null;
            }
            outputFile(request, response, excelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("/download/users")
    @ResponseBody
    public String userExcel(HttpServletResponse response, HttpServletRequest request) {
        try {

            ServiceResult<File> excelFile = excelService.summaryByUsers();

            if (!excelFile.isSuccess()) {
                return null;
            }
            outputFile(request, response, excelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/download/votes/{voteId}")
    @ResponseBody
    public String voteExcel(HttpServletResponse response, HttpServletRequest request, @PathVariable("voteId") Integer voteId) {

        try {
            ServiceResult<File> excelFile = excelService.summaryVotesByUsers(voteId);
            if (!excelFile.isSuccess()) {
                return null;
            }
            outputFile(request, response, excelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void outputFile(HttpServletRequest request, HttpServletResponse response, ServiceResult<File> excelFile) throws IOException {
        File result = excelFile.getResult();
        // 以流的形式下载文件。
        InputStream fis = new BufferedInputStream(new FileInputStream(result));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        // 清空response
        response.reset();

        RespUtils.crossover(request, response);

        response.addHeader("Content-Disposition",
                "attachment;filename=" + new String(result.getName().getBytes("utf-8"), "ISO-8859-1"));
        response.addHeader("Content-Length", "" + result.length());
        response.setContentType("application/octet-stream");
        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        os.write(buffer);
        os.flush();
        os.close();
    }


    private ApiResponse checkAdminLogin(HttpSession session) {
        String userName = (String) session.getAttribute(LOGIN_SESSION_KEY);
        if (userName == null || userName.isEmpty()) {
            return new ApiResponse(ApiResponse.Status.NOT_LOGIN);
        }

        if (!userName.equals(adminName)) {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }
        return null;
    }


}
