package com.yishu.util;

import com.yishu.pojo.WxtokenAndTicket;
import net.sf.json.JSONObject;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;

/**
 * 管理 access_token
 *
 * @author unascribed
 */
public class GetToken
{
    private static String appid = "wx6234fc4a502ef625";// 微信公众号下的AppID
    private static String secret = "897c9b5b60804e4c9f4609cd00dd875c";// 微信公众号下的secret
    public static String TOKEN="wxtoken";

    /**
     * 从微信获取新 access_token.
     * @return
     */
    private static String gettoken()
    {
        JSONObject jsonObject = null;
        String access_token = null;
        String json = null;
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;

        json = HttpUtil.getUrl(url);
        try
        {
            json = new String(json.getBytes("ISO-8859-1"), "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        jsonObject = JSONObject.fromObject(json);
        access_token = jsonObject.getString("access_token");
        System.out.println("access_token==============" + access_token);
        return access_token;
    }

    /**
     * 在spring的 ServletContext 中维护 access_token.
     * @return
     */
    public static String getAccessToken()
    {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        WxtokenAndTicket tokenAndTicket=(WxtokenAndTicket)servletContext.getAttribute(TOKEN);
        String access_token=null;
        long create_time=0;

        if(tokenAndTicket!=null){
            access_token=tokenAndTicket.getToken();
            create_time=tokenAndTicket.getToken_ctime();
        }else{
            tokenAndTicket=new WxtokenAndTicket();
        }
        if (access_token == null)
        {
            access_token = GetToken.gettoken();
            create_time = System.currentTimeMillis();
            tokenAndTicket.setToken(access_token);
            tokenAndTicket.setToken_ctime(create_time);
            servletContext.setAttribute(TOKEN, tokenAndTicket);
            return access_token;
        }
        // 判断是否有效
        if ((System.currentTimeMillis() - create_time)/1000 < 5500)
        {
            return access_token;
        }
        else
        {
            access_token = GetToken.gettoken();
            create_time = System.currentTimeMillis();
            tokenAndTicket.setToken(access_token);
            tokenAndTicket.setToken_ctime(create_time);
            servletContext.setAttribute(TOKEN, tokenAndTicket);
            return access_token;
        }
    }

}
