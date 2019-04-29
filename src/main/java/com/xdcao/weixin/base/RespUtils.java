package com.xdcao.weixin.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: buku.ch
 * @Date: 2019-04-29 09:51
 */


public class RespUtils {

    public static void crossover(HttpServletRequest req, HttpServletResponse resp) {
        resp.addHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        //允许跨域请求中携带cookie
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        // 如果存在自定义的header参数，需要在此处添加，逗号分隔
        resp.addHeader("Access-Control-Allow-Headers", "authorization,Origin, No-Cache, X-Requested-With, "
                + "If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, " + "Content-Type, X-E4M-With");
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
    }

}
