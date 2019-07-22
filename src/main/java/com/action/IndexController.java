package com.action;

import java.io.IOException;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.service.*;
import com.entity.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Value("${spring.datasource.url}")
    private String url ;
    private Integer suiji;
    @Autowired
    private IEmpService es;
    @Autowired
    private IDeptService ds;

    @RequestMapping(value = "/index")
    public String index(){

        System.out.println("--------index------url ="+ url);
        List<Dept> list = ds.selectAll();
        System.out.println("list =" + list);
        return "index" ;
    }

    @RequestMapping(value = "/testAction")
    public String testAction(HttpServletRequest request){
        String content = request.getParameter("editorValue");
        System.out.println("editorValue: \n"+ content);
        request.setAttribute("content", content);

        return "testAction" ;
    }

    //用注解的方式处理(新方式)
    @RequestMapping(value="/sendsms",produces="application/json;charset=UTF-8")
    @ResponseBody  //会自动的把js往json流里面放
    public String sendsms(String phonenum) throws Exception{
        System.out.println("----进入sendsms");
        System.out.println("----phonenum ="+phonenum);
        // 短信应用 SDK AppID
        int appid = 1400219291; // SDK AppID 以1400开头
        // 短信应用 SDK AppKey
        String appkey = "1fc9a0714e16ccee865bbaf082e030c9";
        // 需要发送短信的手机号码
        String[] phoneNumbers = {phonenum};
        // 短信模板 ID，需要在短信应用中申请
        int templateId = 348566; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
        // 签名
        String smsSign = "游戏配置大全公众号"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请

        suiji = (int)((Math.random()*9+1)*1000);
        System.out.println(suiji);
        SmsSingleSenderResult result = null;

        try {
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            result = ssender.send(0, "86", phoneNumbers[0],
                    suiji+"为您的登录验证码，请于5分钟内填写。如非本人操作，请忽略本短信。", "", "");
            System.out.println(result);
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }
        String js = JSON.toJSONString(result);
        return js;
    }

    @RequestMapping(value="/getcode",produces="application/json;charset=UTF-8")
    @ResponseBody  //会自动的把js往json流里面放
    public String getcode(String phonecode){
        System.out.println("----进入getcode");
        System.out.println("----phonecode ="+phonecode);
        int issame = 0;
        if(suiji == Integer.parseInt(phonecode)){
            issame = 1;
        }
        String js = JSON.toJSONString(issame);
        return js;
    }
}
