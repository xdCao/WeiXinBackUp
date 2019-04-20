package com.xdcao.weixin;

import com.google.gson.Gson;
import com.xdcao.weixin.pojo.MediaStr;
import com.xdcao.weixin.pojo.resp.*;
import com.xdcao.weixin.utils.MessageUtil;
import com.xdcao.weixin.utils.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-04-17 22:31
 */

@Component
@DependsOn(value = "tokenService")
public class MsgDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgDispatcher.class);

    @Autowired
    private EventDispatcher eventDispatcher;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private Gson gson;

    public String dispatchMsg(Map<String, String> msgMap) {
        String msgType = msgMap.get(MessageUtil.MSG_TYPE);
        if (msgType == null) {
            LOGGER.error("无法解析消息类型 {}", gson.toJson(msgMap));
        }
        switch (msgType) {
            case MessageUtil.REQ_MESSAGE_TYPE_EVENT:
                eventDispatcher.dispatchEvent(msgMap);
                break;
            case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
                // 处理文字消息
                return processTextMsg(msgMap);
            case MessageUtil.REQ_MESSAGE_TYPE_IMAGE:
                // 处理图片消息
                break;
            case MessageUtil.REQ_MESSAGE_TYPE_LINK:
                // 处理连接消息
                break;
            case MessageUtil.REQ_MESSAGE_TYPE_LOCATION:
                // 处理位置消息
                break;
            case MessageUtil.REQ_MESSAGE_TYPE_VOICE:
                // 处理语音消息
                break;
            default:
                LOGGER.error("暂不支持该类型消息: {}", gson.toJson(msgMap));
        }

        return "";
    }

    private String processTextMsg(Map<String, String> msgMap) {
        String openId = msgMap.get(MessageUtil.FROM_USER_NAME); //用户 openId
        String mpId = msgMap.get(MessageUtil.TO_USER_NAME);   //公众号原始 ID

        String reqContent = msgMap.get(MessageUtil.CONTENT);
        if (reqContent.contains(KeyWords.HISTORY)) {
            // 返回历史文章
            NewsRespMessage newsMsg=new NewsRespMessage();
            newsMsg.setToUserName(openId);
            newsMsg.setFromUserName(mpId);
            newsMsg.setCreateTime(new Date().getTime());
            newsMsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

            System.out.println("==============这是图片消息！");
            Article article=new Article();
            article.setDescription("这是图文消息 1"); //图文消息的描述
            article.setPicUrl("http://res.cuiyongzhi.com/2016/03/201603086749_6850.png"); //图文消息图片地址
            article.setTitle("图文消息 1");  //图文消息标题
            article.setUrl("http://www.cuiyongzhi.com");  //图文 url 链接
            List<Article> list=new ArrayList<Article>();
            list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里 list 中加入多个 Article 即可！
            newsMsg.setArticleCount(list.size());
            newsMsg.setArticles(list);
            return MessageUtil.newsMessageToXml(newsMsg);

        }

        if (reqContent.contains(KeyWords.PIC)) {
            PicRespMessage imgMsg = new PicRespMessage();
            imgMsg.setToUserName(openId);
            imgMsg.setFromUserName(mpId);
            imgMsg.setCreateTime(new Date().getTime());
            imgMsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_Image);

            Image image = new Image();
            String filepath="/root/weixinPic/mmexport1517112306686.jpg";
            Map<String, String> textMap = new HashMap<String, String>();
            textMap.put("name", "testname");
            Map<String, String> fileMap = new HashMap<String, String>();
            fileMap.put("userfile", filepath);
            String mediaIdStr = uploadService.formUpload(textMap, fileMap);
            System.out.println(mediaIdStr);
            MediaStr mediaStr = gson.fromJson(mediaIdStr, MediaStr.class);
            LOGGER.info("获取mediaStr: {}",mediaIdStr);
            image.setMediaId(mediaStr.getMediaId());
            imgMsg.setImage(image);
            return MessageUtil.imageMessageToXml(imgMsg);

        }

        //普通文本消息
        TextRespMessage txtMsg = new TextRespMessage();
        txtMsg.setToUserName(openId);
        txtMsg.setFromUserName(mpId);
        txtMsg.setCreateTime(new Date().getTime());
        txtMsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

        txtMsg.setContent("你是小咪~~~~~~~~~~");
        return MessageUtil.textMessageToXml(txtMsg);

    }

}
