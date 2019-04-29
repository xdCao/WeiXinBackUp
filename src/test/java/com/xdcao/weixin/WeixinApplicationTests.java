package com.xdcao.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.xdcao.weixin.base.ApiResponse;
import com.xdcao.weixin.base.OpenIdUtil;
import com.xdcao.weixin.dao.AnswerBOMapper;
import com.xdcao.weixin.pojo.AccessToken;
import com.xdcao.weixin.pojo.TokenBean;
import com.xdcao.weixin.service.TokenService;
import com.xdcao.weixin.utils.HttpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static com.xdcao.weixin.web.deprecate.MsgDispatcher.KEFU_API_CREATE;

@RunWith(SpringRunner.class)
@SpringBootTest(value = "application.properties")
public class WeixinApplicationTests {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenBean tokenBean;

    @Autowired
    private AnswerBOMapper answerBOMapper;


    @Value("${appID}")
    private String appID;

    @Value("${appsecret}")
    private String appsecret;

    @Value("${tokenUrl}")
    private String tokenUrl;

    @Autowired
    private Gson gson;

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
        cbtMap.put("name", "小咪在这");
        cbtMap.put("type","click");
        JSONObject cbtJson = new JSONObject(cbtMap);


        Map<String,Object> vbtMap = new HashMap<>();
        vbtMap.put("url", "https://juejin.im/user/5ca63f4a51882543d3780776/posts");
        vbtMap.put("name", "铲屎官的博客");
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
//        button.add(buttonOne);
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

    @Test
    public void kefuCreate() throws Exception {

        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "client_credential");
        params.put("appid", appID);
        params.put("secret",appsecret);

        String jsToken = HttpUtil.sendGet(tokenUrl, params);
        AccessToken accessToken = gson.fromJson(jsToken, AccessToken.class);
        String url = KEFU_API_CREATE+accessToken;

        Map<String,String> paramsCrea = new HashMap<>();
        paramsCrea.put("kf_account", "oiUNl5zMP0Fa4-2bfcI8PS4c0FdI");
        paramsCrea.put("nickname", "小咪的老公");
        paramsCrea.put("password", "705083979123");

        String sendPost = HttpUtil.sendPost(url, paramsCrea);
        System.out.println(sendPost);

    }


    @Test
    public void testOpenIdGet() {
        String code = "001naB892VJI8L0jJM992vzE892naB8-";
        String getopenid = OpenIdUtil.getopenid(appID, code, appsecret);
        System.out.println(getopenid);
        JSONObject jsonObject = JSONObject.parseObject(getopenid);
        String openId=jsonObject.getString("openid");
        System.out.println(" openId : "+ openId);
    }


}
