/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.assist;
import com.yishu.domain.Button;
import com.yishu.domain.ClickButton;
import com.yishu.domain.Menu;
import com.yishu.domain.ViewButton;
import com.yishu.pojo.OperationalError;
import com.yishu.util.HttpComponent;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-28 14:56 admin
 * @since JDK1.7
 */
@Component("wechatUtil")
public class WechatUtil {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(WechatUtil.class);

    private static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";


    private static String APPID;
    private static String redirectURL;

    public String getAPPID() {
        return APPID;
    }

    @Value("${APPID:wx6234fc4a502ef625}")
    public void setAPPID(String APPID) {
        WechatUtil.APPID = APPID;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    @Value("${redirectURL:https://lockwx.manxing1798.com/lockWechat/login/wxLogin.action}")
    public void setRedirectURL(String redirectURL) {
        WechatUtil.redirectURL = redirectURL;
    }

    public void printConfig(){
        LOG.info("APPID:"+APPID);
        LOG.info("redirectURL:"+redirectURL);
    }

    /**
     * 组装菜单
     * @return
     */
    public static Menu initMenu(){
        Menu menu = new Menu();
        ClickButton button11=new ClickButton();
        button11.setName("click菜单");
        button11.setType("click");
        button11.setKey("11");

        ViewButton button21=new ViewButton();
        button21.setName("view菜单");
        button21.setType("view");
//        button21.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6234fc4a502ef625&redirect_uri=https://lockwx.manxing1798.com/lockWechat/login/wxLogin.action&response_type=code&scope=snsapi_base#wechat_redirect");
        button21.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+redirectURL+"&response_type=code&scope=snsapi_base#wechat_redirect");

        ClickButton button31=new ClickButton();
        button31.setName("扫码事件");
        button31.setType("scancode_push");
        button31.setKey("31");

        ClickButton button32=new ClickButton();
        button32.setName("地理位置");
        button32.setType("location_select");
        button32.setKey("32");

        Button button=new Button();
        button.setName("菜单");
        button.setSub_button(new Button[]{button31,button32});

        menu.setButton(new Button[]{button11,button21,button});
        return menu;
    }

    public static OperationalError createMenu(String token,String menu){
        String url=CREATE_MENU_URL.replace("ACCESS_TOKEN",token);
        JSONObject jsonObject = HttpComponent.doPostStr(url,menu);
        OperationalError error=new OperationalError();
        if (null != jsonObject){
            error.setErrcode(jsonObject.getInt("errcode"));
            error.setErrmsg(jsonObject.getString("errmsg"));
        }
        return error;
    }
}
