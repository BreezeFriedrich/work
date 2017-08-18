package com.hysm.util;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;


import com.hysm.domain.WeChatVO;
import com.hysm.util.wx.common.Secret;
import com.hysm.util.wx.demo.Demo;
import com.hysm.util.wx.utils.RequestHandler;
import com.hysm.util.wx.utils.StringUtil;


public class PayTool {
	
	private static String appid="wx6234fc4a502ef625",appsecret="897c9b5b60804e4c9f4609cd00dd875c",partner="1310611201",partnerkey="Hanjiaguo320102197208222839hanhy",openId;
	private static String notifyurl;
	
	public  static String getNotifyurl() {
		return notifyurl;
	}

	public static void setNotifyurl(String notifyurl) {
		PayTool.notifyurl = notifyurl;
	}

	/**
	 * 微信配置
	 */
	public static void configuration(){
		//微信支付商户开通后 微信会提供appid和appsecret和商户号partner
		appid="wx6234fc4a502ef625";
		appsecret = "897c9b5b60804e4c9f4609cd00dd875c";
		partner = "1310611201";
		//这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
		partnerkey = "Hanjiaguo320102197208222839hanhy";
		
		//微信支付成功后通知地址 必须要求80端口并且地址不能带参数
			
		Secret secret=new Secret();
		secret.configuration(appid, appsecret, partner, partnerkey, openId, notifyurl);
		
	}
	
	/**
	 * 微信支付验证
	 * @return
	 */
	public static WeChatVO regs_pay(String out_trade_no){
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);
		appid="wxbedd9f6ba44c9ec5";	//微信分配的公众账号ID
		String mch_id="1219554501";	//微信支付分配的商户号
		
		Map map=Demo.regs_pay(mch_id,StringUtil.getRandomString(6),out_trade_no);
		System.out.println(map.toString());
		WeChatVO weChatVO;
		String return_code=(String) map.get("return_code");
		if(return_code!=null&&return_code.equals("SUCCESS")){
			weChatVO=new WeChatVO();
			weChatVO.setBank_type((String)map.get("bank_type"));
			weChatVO.setCash_fee((String)map.get("cash_fee"));
			weChatVO.setDevice_info((String)map.get("device_info"));
			weChatVO.setIs_subscribe((String)map.get("is_subscribe"));
			weChatVO.setOpenid((String)map.get("openid"));
			weChatVO.setOut_trade_no((String)map.get("out_trade_no"));
			weChatVO.setTime_end((String)map.get("time_end"));
			weChatVO.setTotal_fee((String)map.get("total_fee"));
			weChatVO.setTrade_state((String)map.get("trade_state"));
			weChatVO.setTrade_state_desc((String)map.get("trade_state_desc"));
			weChatVO.setTrade_type((String)map.get("trade_type"));
			weChatVO.setTransaction_id((String)map.get("transaction_id"));
			return  weChatVO;
			
		}
		
		return null;
		
	}
	
	
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPartnerkey() {
		return partnerkey;
	}

	public void setPartnerkey(String partnerkey) {
		this.partnerkey = partnerkey;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
	
	
}

