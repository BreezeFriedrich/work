package com.hysm.util.wx.utils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;



public class Refund {
	public static String wechatRefund(String out_refund_no,String out_trade_no,String total_fee,String refund_fee,String nonce_str) {
//		String out_refund_no// 退款单号
//		String out_trade_no // 订单号
//		String total_fee // 总金额
//		String refund_fee // 退款金额
//		String refund_fee// 随机字符串
		try {
		String appid = "wxbedd9f6ba44c9ec5";
		String appsecret = "2ebc7c9945bb4eef16b0886837e14de8";
		String mch_id = "1219554501";
		String op_user_id = "1219554501";//就是MCHID
		String partnerkey = "813eec86368d923c0077b6ca4db2bddb";//商户平台上的那个KEY
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		packageParams.put("op_user_id", op_user_id);

		RequestHandler reqHandler = new RequestHandler(
				null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>"
				+ "<out_trade_no>" + out_trade_no + "</out_trade_no>"
				+ "<out_refund_no>" + out_refund_no + "</out_refund_no>"
				+ "<total_fee>" + total_fee + "</total_fee>"
				+ "<refund_fee>" + refund_fee + "</refund_fee>"
				+ "<op_user_id>" + op_user_id + "</op_user_id>" + "</xml>";
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		
			String s= ClientCustomSSL.doRefund(createOrderURL, xml);
			
			return s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		Refund refund=new Refund();
		String s=refund.wechatRefund("tkb1456925819s0208b7564","b1457066867s8690","1","1","0578443");
		System.out.println(s);
	}
}
