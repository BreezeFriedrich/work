/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.util;

import com.yishu.domain.WechatTokenAndTicket;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;

public class GetJsticket {

	public static String TOKEN="wxtoken";

	//获取jsapi的ticket
	private static String getJS_Ticket(){
		String access_token=GetToken.getAccessToken();
		String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
		String json=HttpUtil.doGet(url);
		try {
			json = new String(json.getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		JSONObject js=null;
		String jsTicket=null;
		try {
			js= JSONObject.fromObject(json);
			jsTicket=js.getString("ticket");
		} catch (JSONException e) {
			return null;
		}
		return jsTicket;
	}

	//判断ticket是否过期，同时将ticket写入数据库
	public static String getJSApiTicket(){
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		WechatTokenAndTicket wat=(WechatTokenAndTicket) servletContext.getAttribute(TOKEN);
		String jsticket=null;
		long create_time=0;
		if(wat!=null){
			jsticket=wat.getJsapi_ticket();
			create_time=wat.getTicket_ctime();
		}else{
			wat=new WechatTokenAndTicket();
		}
		if(jsticket==null){
			jsticket=getJS_Ticket();
			create_time=System.currentTimeMillis();
			wat.setJsapi_ticket(jsticket);
			wat.setTicket_ctime(create_time);
			servletContext.setAttribute(TOKEN, wat);
			return jsticket;
		}
		if ((System.currentTimeMillis()-create_time)/1000 < 5500) {// 有效
			return jsticket;
		} else {
			// 无效
			jsticket=getJS_Ticket();
			create_time=System.currentTimeMillis();
			wat.setJsapi_ticket(jsticket);
			wat.setTicket_ctime(create_time);
			servletContext.setAttribute(TOKEN, wat);
			return jsticket;
		}
	}
}