package com.xdcao.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.JsonObject;
import com.xdcao.weixin.pojo.ClickButton;
import com.xdcao.weixin.pojo.TokenBean;
import com.xdcao.weixin.pojo.ViewButton;
import com.xdcao.weixin.utils.HttpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeixinApplicationTests {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenBean tokenBean;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testFetchToken() {
        tokenService.getToken_getTicket();
    }

    @Test
    public void testImgUpload() {

    }


    /*创建自定义菜单*/
    @Test
    public void mkMenu() {

        Map<String,Object> cbtMap = new HashMap<>();
        cbtMap.put("key", "image");
        cbtMap.put("name", "回复图片");
        cbtMap.put("type","click");
        JSONObject cbtJson = new JSONObject(cbtMap);


        Map<String,Object> vbtMap = new HashMap<>();
        vbtMap.put("url", "http://www.cuiyongzhi.com");
        vbtMap.put("name", "博客");
        vbtMap.put("type","view");
        JSONObject vbtJson = new JSONObject(vbtMap);

        JSONArray subButton=new JSONArray();
        subButton.add(cbtJson);
        subButton.add(vbtJson);

        JSONObject buttonOne=new JSONObject();
        buttonOne.put("name", "菜单");
        buttonOne.put("sub_button", subButton);

        JSONArray button=new JSONArray();
        button.add(vbtJson);
        button.add(buttonOne);
        button.add(cbtJson);

        JSONObject menujson=new JSONObject();
        menujson.put("button", button);

        String jsonString = JSON.toJSONString(menujson, SerializerFeature.DisableCircularReferenceDetect);
        //这里为请求接口的 url   +号后面的是 token，这里就不做过多对 token 获取的方法解释
        String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+tokenBean.getAccessToken().getAccessToken();

        System.out.println("-------------------token:"+tokenBean.getAccessToken().getAccessToken());

        try{
            String rs= HttpUtil.sendPostBuffer(url, jsonString);
            System.out.println(rs);
        }catch(Exception e){
            System.out.println("请求错误！");
        }
    }

}
