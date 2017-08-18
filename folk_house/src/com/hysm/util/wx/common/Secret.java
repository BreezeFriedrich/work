package com.hysm.util.wx.common;

import com.hysm.util.wx.demo.Demo;

public class Secret {

	public static void configuration(String appid, String appsecret,
			String partner, String partnerkey, String openId, String notifyurl) {
		Demo.setAppid(appid);
		Demo.setAppsecret(appsecret);
		Demo.setNotifyurl(notifyurl);
		Demo.setOpenId(openId);
		Demo.setPartner(partner);
		Demo.setPartnerkey(partnerkey);
	}
}
