package com.hysm.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.interfaces.PBEKey;
import javax.xml.crypto.Data;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.hysm.domain.Collect;
import com.hysm.domain.HouseType;
import com.hysm.domain.HouseUsers;
import com.hysm.domain.Img;
import com.hysm.domain.Order;
import com.hysm.domain.PageBean;
import com.hysm.domain.PayVO;
import com.hysm.domain.Price;
import com.hysm.domain.Property;
import com.hysm.domain.Supporting;
import com.hysm.domain.User;
import com.hysm.domain.WeChatVO;
import com.hysm.service.ISearchService;
import com.hysm.util.CommonUtil;
import com.hysm.util.DataUtil;
import com.hysm.util.GetJsticket;
import com.hysm.util.GetToken;
import com.hysm.util.PayTool;
import com.hysm.util.RandomString;
import com.hysm.util.wx.demo.Demo;
import com.hysm.util.wx.demo.WxPayDto;
import com.hysm.util.wx.demo.WxPayResult;
import com.hysm.util.wx.utils.AddressUtil;

public class SearchAction extends BaseAction {
	Logger logger = Logger.getLogger(HouseAction.class);
	private String city_name, stime, etime, pn, tag_name;
	private int tag_id, ht_id, type, p, price, num, total_price, sch_id,
			two_price;
	private String name, starttime, idcard, idcard1, tel, openid, order_id,
			add_idcard, add_idcard1, ordernum;
	private PayVO payVO;
	private WxPayDto wxPayDto;
	private String createTime, time, body;
	private int pageNum;
	private String result, inputLine;;

	/**
	 * 首页 获取openid
	 * 
	 * @return
	 */
	public String index() {
		String openid = getSessionUser();
		session.setAttribute("openid", openid);
		openid = (String) session.getAttribute("openid");
		if (openid != null && !openid.equals("")) {

		} else {
			openid = request.getParameter("code");
			session.setAttribute("openid", openid);
		}
		System.out.println("action  openid:" + openid);
		return "index";
	}

	/**
	 * 国内搜索
	 * 
	 * @return
	 */
	public String countey_search() {
		try {
			pageNum = 1;
			if (pn != null && !pn.equals("")) {
				if (Integer.parseInt(pn) > 1) {
					pageNum = Integer.parseInt(pn);
				}
			}
			PageBean<HouseType> pb = serachService.get_countey_search(pageNum, city_name, stime + " 12:00:01", etime + "11:59:59", tag_id);
			List<HouseType> list = pb.getList();
			List<String> ht_idList = new ArrayList<String>();
			for (HouseType h : list) {
				ht_idList.add(h.getHt_id() + "");
			}
			String ht_id = CommonUtil.getAuthFiledValueString(CommonUtil .getStringArrayToString(ht_idList));
			List<Price> list_p = serachService.get_price_img(ht_id, stime);
			/*
			 * int
			 * count=serachService.get_house_type_count(city_name,stime,etime
			 * ,tag_id);
			 */
			request.setAttribute("pb", pb);
			request.setAttribute("list", list);
			request.setAttribute("list_p", list_p);
			request.setAttribute("city_name", city_name);
			request.setAttribute("stime", stime);
			request.setAttribute("etime", etime);
			request.setAttribute("tag_id", tag_id);
			/* request.setAttribute("count", count); */
			request.setAttribute("size", list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "country_house";
	}

	/**
	 * 国内加载更多
	 * 
	 * @return
	 */
	public String countey_load() {
		try {
			pageNum = 1;
			if (pn != null && !pn.equals("")) {
				if (Integer.parseInt(pn) > 1) {
					pageNum = Integer.parseInt(pn);
				}
			}
			PageBean<HouseType> pb = serachService.get_countey_search(pageNum, city_name, stime, etime, tag_id);
			List<HouseType> list = pb.getList();
			List<String> ht_idList = new ArrayList<String>();
			for (HouseType h : list) {
				ht_idList.add(h.getHt_id() + "");
			}
			String ht_id = CommonUtil.getAuthFiledValueString(CommonUtil .getStringArrayToString(ht_idList));
			List<Price> list_p = serachService.get_price_img(ht_id, stime);
			/*
			 * int
			 * count=serachService.get_house_type_count(city_name,stime,etime
			 * ,tag_id);
			 */
			request.setAttribute("pb", pb);
			request.setAttribute("list", list);
			request.setAttribute("list_p", list_p);
			request.setAttribute("city_name", city_name);
			request.setAttribute("stime", stime);
			request.setAttribute("etime", etime);
			request.setAttribute("tag_id", tag_id);
			/* request.setAttribute("count", count); */
			request.setAttribute("size", list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "house_load";
	}

	/**
	 * 房屋详情
	 * 
	 * @return
	 */
	public String house_detail() {
		// 房型，价格，酒店信息
		HouseType houseType = serachService.get_hotel_price(ht_id);
		// 图片
		List<Img> list_img = serachService.get_house_img(ht_id);
		// 属性
		List<Property> list_pro = serachService.get_house_pro(ht_id);

		// 配套设施
		List<Supporting> list_sup = serachService.get_house_sup(ht_id);
		JSONArray jsonArray = JSONArray.fromObject(list_sup);
		request.setAttribute("sup_json", jsonArray);
		request.setAttribute("houseType", houseType);
		request.setAttribute("list_img", list_img);
		request.setAttribute("list_pro", list_pro);
		request.setAttribute("list_sup", list_sup);
		request.setAttribute("stime", stime);
		request.setAttribute("etime", etime);
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
		Date d1;
		try {
			d1 = smf.parse(stime);
			Date d2 = smf.parse(etime);
			long intervalMilli = d2.getTime() - d1.getTime();
			int num1 = (int) (intervalMilli / (24 * 60 * 60 * 1000)); // 离店时间跟入住时间相差天数
			Date date = new Date();
			long min = d1.getTime() - date.getTime();
			int num_day = (int) (min / (24 * 60 * 60 * 1000));
			request.setAttribute("num1", num1);
			request.setAttribute("num_day", num_day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "house_detail";
	}

	public void get_house_num() {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			HouseType house = serachService.get_house_num(stime + " 12:00:01",
					etime + " 11:59:59", ht_id);
			if (house != null) {
				SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
				Date d1 = smf.parse(stime);
				Date d2 = smf.parse(etime);
				long intervalMilli = d2.getTime() - d1.getTime();
				int num = (int) (intervalMilli / (24 * 60 * 60 * 1000)); // 离店时间跟入住时间相差天数
				Date date = new Date();
				long min = d1.getTime() - date.getTime();
				int num_day = (int) (min / (24 * 60 * 60 * 1000));
				house.setNum1(num);
				house.setNum_day(num_day);
			} else {
				house.setNum(0);
			}
			JSONArray jsonArray = JSONArray.fromObject(house);
			out.print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 跳转订单填写页面
	 * 
	 * @return
	 */
	public String order_write() {
		try {
			SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = smf.parse(stime);
			Date d2 = smf.parse(etime);
			Date d3 = smf.parse(DataUtil.fromDate24H());
			// 选择入住时间跟离店时间差
			long intervalMilli = d2.getTime() - d1.getTime();
			int num = (int) (intervalMilli / (24 * 60 * 60 * 1000));

			// 选择预订时间跟离店时间差
			long l1 = d1.getTime() - d3.getTime();
			int num2 = (int) (l1 / (24 * 60 * 60 * 1000));
			request.setAttribute("num2", num2);
			request.setAttribute("total_price", (p - price) / 100 * num);
			request.setAttribute("num1", num);
			return "order_write";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "order_write";
	}

	/**
	 * 用户下单
	 * 
	 * @return
	 */
	public String confirm_order() {
		System.out.println("进入支付");
		List<HouseUsers> list1 = (List<HouseUsers>) session .getAttribute("card_list");
		if (list1 == null) {
			List<HouseUsers> list = new ArrayList<HouseUsers>();// 订单里面用户集合
			Order order = new Order();
			Order order1;
			Order order2;
			String time = DataUtil.fromDate24H();
			stime = stime + " 12:00:00";
			etime = etime + " 12:00:00";
			// 父订单
			HouseType h = serachService.get_hotel_info(ht_id);
			List<Order> listnu = new ArrayList<Order>();
			if (add_idcard != null && !add_idcard.equals("")) {

				// 说明是两个房间
				order.setSunOrders(listnu);

				long order_num3 = System.currentTimeMillis();
				order.setAmmount_sum(total_price + h.getCash_pledge() * 2);// 两份押金
				order.setCash_pledge(h.getCash_pledge() * 2);
				order.setOrder_num(order_num3 + "");
				order.setOpenid(openid);
				order.setCreatetime(time);
				order.setTel(tel);
				order.setIs_f_order(2);// 父订单
				serachService.insert_order_info2(order);
				int forderid = order.getOrder_id();

				// 根据入住时间，房型随机出房间号
				HouseType house = serachService.get_house_info(ht_id, stime, etime);
				if (house != null) {
					// 查酒店名称，类型名称
					// 添加订单(房间号，openid，生成时间，订单编号，交易金额)
					long order_num = System.currentTimeMillis();

					order1 = new Order();
					// 加押金
					order1.setAmmount_sum(total_price - two_price + h.getCash_pledge());
					order1.setOrder_num(order_num + "");
					order1.setHouse_id(house.getHouse_id());
					order1.setOpenid(openid);
					order1.setCreatetime(time);
					order1.setTel(tel);
					order1.setIs_f_order(1);// 正常订单
					order1.setF_orderid(forderid);
					order1.setCash_pledge(h.getCash_pledge());
					order1.setStarttime(stime);
					order1.setEndtime(etime);
					serachService.insert_order_info(order1);
					// 添加日程
					int order_id = order1.getOrder_id();
					serachService.insert_house_schedule(house.getHouse_id(), order_id, stime, etime);
					listnu.add(order1);

					HouseUsers hu = new HouseUsers();
					hu.setCardid(idcard);
					hu.setEndtime(etime);
					hu.setStarttime(stime);
					hu.setLockid(house.getKeyNum());
					hu.setHouseid(house.getHouse_id());
					hu.setHouse_num(house.getHouse_num());
					hu.setOrder_id(order_id);
					list.add(hu);
					if (idcard1 != null && !idcard1.equals("")) {
						hu = new HouseUsers();
						hu.setCardid(idcard1);
						hu.setEndtime(etime);
						hu.setStarttime(stime);
						hu.setLockid(house.getKeyNum());
						hu.setHouseid(house.getHouse_id());
						hu.setHouse_num(house.getHouse_num());
						hu.setOrder_id(order_id);
						list.add(hu);
					}

					// 第二个房间
					HouseType house2 = serachService.get_house_info(ht_id, stime, etime);
					if (house2 != null) {
						// 添加订单(房间号，openid，生成时间，订单编号，交易金额)
						long order_num2 = System.currentTimeMillis();

						order2 = new Order();
						// 加押金
						order2.setAmmount_sum(two_price + h.getCash_pledge());
						order2.setOrder_num(order_num2 + "");
						order2.setHouse_id(house2.getHouse_id());
						order2.setOpenid(openid);
						order2.setCreatetime(time);
						order2.setTel(tel);
						order2.setIs_f_order(1);// 正常订单
						order2.setF_orderid(forderid);
						order2.setCash_pledge(h.getCash_pledge());
						order2.setStarttime(stime);
						order2.setEndtime(etime);

						serachService.insert_order_info(order2);

						int order_id2 = order2.getOrder_id();
						serachService.insert_house_schedule( house2.getHouse_id(), order_id2, stime, etime);
						listnu.add(order2);

						// 添加订单(房间号，openid，生成时间，订单编号，交易金额)

						hu = new HouseUsers();
						hu.setCardid(add_idcard);
						hu.setEndtime(etime);
						hu.setStarttime(stime);
						hu.setLockid(house2.getKeyNum());
						hu.setHouseid(house2.getHouse_id());
						hu.setHouse_num(house2.getHouse_num());
						hu.setOrder_id(order_id2);
						list.add(hu);
						if (add_idcard1 != null && !add_idcard1.equals("")) {
							hu = new HouseUsers();
							hu.setCardid(add_idcard1);
							hu.setEndtime(etime);
							hu.setStarttime(stime);
							hu.setLockid(house2.getKeyNum());
							hu.setHouseid(house2.getHouse_id());
							hu.setHouse_num(house2.getHouse_num());
							hu.setOrder_id(order_id2);
							list.add(hu);
						}
					}
				} else {
					return "nohouse";// 没有房间
				}
			} else {
				// 一个房间
				// 根据入住时间，房型随机出房间号
				HouseType house = serachService.get_house_info(ht_id, stime, etime);
				if (house != null) {
					// 查酒店名称，类型名称
					h = serachService.get_hotel_info(ht_id);
					// 添加订单(房间号，openid，生成时间，订单编号，交易金额)
					long order_num = System.currentTimeMillis();

					// 100的押金
					order.setAmmount_sum(total_price + h.getCash_pledge());
					order.setCash_pledge(h.getCash_pledge());
					order.setOrder_num(order_num + "");
					order.setHouse_id(house.getHouse_id());
					order.setOpenid(openid);
					order.setCreatetime(time);
					order.setTel(tel);
					order.setIs_f_order(1);// 正常订单
					order.setStarttime(stime);
					order.setEndtime(etime);
					serachService.insert_order_info(order);
					// 添加日程
					int order_id = order.getOrder_id();
					serachService.insert_house_schedule(house.getHouse_id(), order_id, stime, etime);

					HouseUsers hu = new HouseUsers();
					hu.setCardid(idcard);
					hu.setEndtime(etime);
					hu.setStarttime(stime);
					hu.setLockid(house.getKeyNum());
					hu.setHouseid(house.getHouse_id());
					hu.setHouse_num(house.getHouse_num());
					hu.setOrder_id(order_id);
					list.add(hu);
					if (idcard1 != null && !idcard1.equals("")) {
						hu = new HouseUsers();
						hu.setCardid(idcard1);
						hu.setEndtime(etime);
						hu.setStarttime(stime);
						hu.setLockid(house.getKeyNum());
						hu.setHouseid(house.getHouse_id());
						hu.setHouse_num(house.getHouse_num());
						hu.setOrder_id(order_id);
						list.add(hu);
					}
				} else {
					return "nohouse";// 没有房间
				}
			}
			if (list != null) {
				serachService.insert_house_user(list);
			}
			session.setAttribute("card_list", list);
			session.setAttribute("h", h);
			session.setAttribute("orderinfo", order);
			// 去支付
			String spbillCreateIp = AddressUtil.getRemortIP(request);
			String body = "订单号是" + order.getOrder_num();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
			String notifyUrl = basePath + "/search/WXnotify.do";
			// Double total_fee=(Double.valueOf(order.getAmmount_sum())/100);
			Double total_fee = 0.01;
			String packages = this.getPackage(order.getCreatetime(), body, spbillCreateIp, notifyUrl, total_fee, order.getOrder_num());
			session.setAttribute("result", packages);
		}

		return "confirm_order";
	}

	public String goWxPayAgain() {
		System.out.println("进入支付");
		Order o = serachService.queryOrderByid(order_id);
		String u_openid = getSessionUser();

		if (o != null) {
			String orderno = o.getOrder_num();
			if (o.getState() == 1) {
				WeChatVO w = PayTool.regs_pay(orderno);
				String trade = w.getTrade_state();
				if (w != null && trade != null && (trade).equals("SUCCESS")) {
					// 已支付成功
					// 添加记录
					// 添加微信支付记录
					serachService.inser_wechat_result(w);
					// 记录表
					PayVO p = new PayVO();
					p.setPay_time(DataUtil.fromDate24H());
					p.setResult("success");
					p.setPaymoney(total_price);
					p.setName(u_openid);
					p.setOrderid(order_id);
					p.setTrade_no(w.getOut_trade_no() + "");
					serachService.insert_result_log(p);
					// 更改订单状态：支付成功,//添加房间临时密码
					serachService.update_order_state(order_id, RandomString.randomNumStr(6));
					// 添加订单日志
					serachService.insert_order_log(order_id, DataUtil.fromDate24H());

					// 修改房间日程状态为有效(状态：-1未使用, 1:有效 100:用户取消 101:系统取消 200:用户退房）
					serachService.update_sch_state(Integer.valueOf(order_id), ISearchService.KEYSTATE[1]);

					List<Order> order = serachService.get_order_detail( order_id, u_openid, 0);
					List<HouseUsers> list = serachService.get_card_house( order_id, u_openid, order.get(0).getSch_id());
					order.get(0).setListhu(list);
					request.setAttribute("order", order);

					return "order_detail";
				} else {
					// 去支付
					String spbillCreateIp = AddressUtil.getRemortIP(request);
					String body = "订单号是" + o.getOrder_num();
					String basePath = request.getScheme() + "://"
							+ request.getServerName() + ":"
							+ request.getServerPort()
							+ request.getContextPath();
					String notifyUrl = basePath + "/search/WXnotify.do";
					// Double
					// total_fee=(Double.valueOf(order.getAmmount_sum())/100);
					Double total_fee = 0.01;
					String packages = this.getPackage(o.getCreatetime(), body, spbillCreateIp, notifyUrl, total_fee, o.getOrder_num());

					session.setAttribute("result", packages);
					session.setAttribute("card_list", o.getListhu());
					session.setAttribute("h", o.getHouse());
					session.setAttribute("orderinfo", o);

					return "confirm_order";
				}
			} else {
				return "error";
			}
		}
		return "error";
	}

	/**
	 * 取消订单
	 * 
	 * @return
	 */
	public String giveupOrder() {
		String u_openid = getSessionUser();
		// 改变订单状态h_order
		serachService.update_order_allstate(Integer.valueOf(order_id), 5);

		// 改变房间日程h_house_type//改变用户锁日程HouseUser
		serachService.update_sch_state(Integer.valueOf(order_id), ISearchService.KEYSTATE[2]);

		List<Order> order = serachService.get_order_detail(order_id, u_openid, 0);
		List<HouseUsers> list = serachService.get_card_house(order_id, u_openid, order.get(0).getSch_id());
		order.get(0).setListhu(list);
		request.setAttribute("order", order);

		return "order_detail";
	}

	/**
	 * 下面是V3版本的微信支付 得到微信的prepay_id
	 */
	public String getPackage(String createTime, String body,String spbillCreateIp, String notifyUrl, Double total_fee, String order_id) {

		String u_openid = this.getSessionUser();
		try {
			String order_time = createTime;
			// 配置参数
			PayTool pt = new PayTool();
			PayTool.setNotifyurl(notifyUrl);
			// pt.setOpenId(user.getOpenid());//
			pt.configuration();
			WxPayDto wpd = new WxPayDto();
			// 传入参数openId,orderId,totalFee(以分为单位),spbill_create_ip(订单生成的机器
			// IP),body得到prepay_id
			wpd.setOpenId(u_openid);
			wpd.setBody(body);
			wpd.setNotifyUrl(notifyUrl);
			wpd.setOrderId(order_id);
			wpd.setSpbillCreateIp(spbillCreateIp);
			wpd.setTotalFee("￥" + total_fee);
			String packages = Demo.getPackage(wpd);
			return packages;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 微信支付查看支付结果
	 */
	public String regs_pay() {
		String u_openid = getSessionUser();
		List<Order> orderlist = new ArrayList<Order>();
		WeChatVO w = PayTool.regs_pay(ordernum);
		if (w != null && (w.getTrade_state()).equals("SUCCESS")) {
			// 添加记录

			// 添加微信支付记录
			serachService.inser_wechat_result(w);
			// 记录表
			PayVO p = new PayVO();
			p.setPay_time(DataUtil.fromDate24H());
			p.setResult("success");
			p.setPaymoney(total_price);
			p.setName(u_openid);
			p.setOrderid(order_id);
			p.setTrade_no(w.getOut_trade_no() + "");
			serachService.insert_result_log(p);
			// 添加订单日志
			serachService.insert_order_log(order_id, DataUtil.fromDate24H());
			Order order = (Order) session.getAttribute("orderinfo");
			serachService.update_order_allstate(order.getOrder_id(), 2);
			if (order.getIs_f_order() == 2) {
				// 说明是父订单
				List<Order> listod = order.getSunOrders();
				for (Order oo : listod) {
					// 添加订单日志
					serachService.insert_order_log( String.valueOf(oo.getOrder_id()), DataUtil.fromDate24H());
					// 添加房间临时密码,修改状态
					String h_pwd = RandomString.randomNumStr(6);
					serachService.update_order_state( String.valueOf(oo.getOrder_id()), h_pwd);
					// 修改房间日程状态为有效(状态：-1未使用, 1:有效 100:用户取消 101:系统取消 200:用户退房）
					serachService.update_sch_state(oo.getOrder_id(), ISearchService.KEYSTATE[1]);
				}
			} else {
				// 不是父订单：
				// 更改订单状态：支付成功
				serachService.update_order_state(order_id, RandomString.randomNumStr(6));
				// 修改房间日程状态为有效(状态：-1未使用, 1:有效 100:用户取消 101:系统取消 200:用户退房）
				// 修改用户锁信息
				serachService.update_sch_state(Integer.valueOf(order_id), ISearchService.KEYSTATE[1]);
			}
			session.removeAttribute("orderinfo");
			session.removeAttribute("card_list");
			session.removeAttribute("h");
			session.removeAttribute("result");
			if (order.getIs_f_order() == 2) {
				// 父订单
				List<Order> order2 = serachService.get_order_detail2( order.getOrder_id(), u_openid, 0);
				for (Order ooo : order2) {
					List<HouseUsers> list = serachService.get_card_house( String.valueOf(ooo.getOrder_id()), u_openid, ooo.getSch_id());
					ooo.setListhu(list);
				}
				orderlist = order2;
			} else {
				List<Order> order2 = serachService.get_order_detail( String.valueOf(order.getOrder_id()), u_openid, 0);
				List<HouseUsers> list = serachService.get_card_house(String .valueOf(order.getOrder_id()), u_openid, order2.get(0) .getSch_id());
				order2.get(0).setListhu(list);
				orderlist = order2;
			}
			request.setAttribute("order", orderlist);
			return "order_detail";
		}
		List<Order> order = serachService.get_order_detail(order_id, u_openid, 0);
		List<HouseUsers> list = serachService.get_card_house(order_id, u_openid, order.get(0).getSch_id());
		order.get(0).setListhu(list);
		request.setAttribute("order", order);
		return "order_detail";
	}

	public void WXnotify() {
		System.out.print("微信支付回调数据开始");
		// 示例报文
		// String xml =
		// "<xml><appid><![CDATA[wxb4dc385f953b356e]]></appid><bank_type><![CDATA[CCB_CREDIT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1228442802]]></mch_id><nonce_str><![CDATA[1002477130]]></nonce_str><openid><![CDATA[o-HREuJzRr3moMvv990VdfnQ8x4k]]></openid><out_trade_no><![CDATA[1000000000051249]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[1269E03E43F2B8C388A414EDAE185CEE]]></sign><time_end><![CDATA[20150324100405]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1009530574201503240036299496]]></transaction_id></xml>";

		String notityXml = "";
		String resXml = "";
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("接收到的报文：" + notityXml);
		Map m = parseXmlToList2(notityXml);
		WxPayResult wpr = new WxPayResult();
		if (m != null) {
			if (m.get("appid") != null) {
				wpr.setAppid(m.get("appid").toString());
			}
			if (m.get("bank_type") != null) {
				wpr.setBankType(m.get("bank_type").toString());
			}
			if (m.get("cash_fee") != null) {
				wpr.setCashFee(m.get("cash_fee").toString());
			}
			if (m.get("fee_type") != null) {
				wpr.setFeeType(m.get("fee_type").toString());
			}
			if (m.get("is_subscribe") != null) {
				wpr.setIsSubscribe(m.get("is_subscribe").toString());
			}
			if (m.get("mch_id") != null) {
				wpr.setMchId(m.get("mch_id").toString());
			}
			if (m.get("nonce_str") != null) {
				wpr.setNonceStr(m.get("nonce_str").toString());
			}
			if (m.get("openid") != null) {
				wpr.setOpenid(m.get("openid").toString());
			}
			if (m.get("out_trade_no") != null) {
				wpr.setOutTradeNo(m.get("out_trade_no").toString());// 商户订单号、id
			}
			if (m.get("result_code") != null) {
				wpr.setResultCode(m.get("result_code").toString());
			}
			if (m.get("return_code") != null) {
				wpr.setReturnCode(m.get("return_code").toString());
			}
			if (m.get("sign") != null) {
				wpr.setSign(m.get("sign").toString());
			}
			if (m.get("time_end") != null) {
				wpr.setTimeEnd(m.get("time_end").toString());
			}
			if (m.get("total_fee") != null) {
				wpr.setTotalFee(m.get("total_fee").toString());
			}
			if (m.get("trade_type") != null) {
				wpr.setTradeType(m.get("trade_type").toString());
			}
			if (m.get("transaction_id") != null) {
				wpr.setTransactionId(m.get("transaction_id").toString());// 微信支付订单号
			}
			if ("SUCCESS".equals(wpr.getResultCode())) {
				// 支付成功
				resXml = "<xml>"
						+ "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			} else {
				resXml = "<xml>"
						+ "<return_code><![CDATA[FAIL]]></return_code>"
						+ "<return_msg><![CDATA[报文为空]]></return_msg>"
						+ "</xml> ";
			}
			try {
				this.sentXML(resXml);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * description: 解析微信通知xml
	 * 
	 * @param xml
	 * @return
	 * @author ex_yangxiaoyi
	 * @see
	 */
	private static Map parseXmlToList2(String xml) {
		Map retMap = new HashMap();
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					retMap.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}

	/**
	 * 我的订单
	 * 
	 * @return
	 */
	public void get_order_info() {
		String openid = getSessionUser();
		PrintWriter out = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			out = response.getWriter();
			List<Order> list = serachService.get_order_info(openid);
			JSONArray json = JSONArray.fromObject(list);
			out.print(json);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 订单详细信息
	 * 
	 * @return
	 */
	public String order_detail() {
		System.out.println("进入");
		List<Order> order = serachService.get_order_detail(order_id, openid, sch_id);
		List<HouseUsers> list = serachService.get_card_house(order_id, openid, sch_id);
		order.get(0).setListhu(list);
		request.setAttribute("order", order);

		return "order_detail";
	}

	/**
	 * 收藏
	 * 
	 * @return
	 */
	public void collect() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Collect collect = serachService.get_coll_info(ht_id, openid);
			if (collect != null) {
				out.print("fail");
			} else {
				int i = serachService.insert_collect(ht_id, openid, DataUtil.fromDate24H());
				if (i == 1) {
					out.print("success");
				} else {
					out.print("error");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 取消收藏
	 * 
	 * @return
	 */
	public void remove_collect() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			int id = serachService.get_coll_id(ht_id, openid);
			int i = serachService.del_collect(id);
			if (i == 1) {
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 我的收藏
	 * 
	 * @return
	 */
	public String get_collect_info() {
		if (openid != null && !openid.equals("")) {
			try {
				int pageNum = 1;
				if (pn != null && pn.equals("")) {
					if (Integer.parseInt(pn) > 1) {
						pageNum = Integer.parseInt(pn);
					}
				}
				PageBean<HouseType> pb = serachService.get_collect_info( pageNum, openid);
				List<HouseType> list = pb.getList();
				List<String> ht_idList = new ArrayList<String>();
				for (HouseType h : list) {
					ht_idList.add(h.getHt_id() + "");
				}
				String ht_id;
				ht_id = CommonUtil.getAuthFiledValueString(CommonUtil .getStringArrayToString(ht_idList));
				System.out.println();
				List<Img> imgs = serachService.get_coll_img(ht_id);
				System.out.println(imgs.size());
				request.setAttribute("pb", pb);
				request.setAttribute("list", list);
				request.setAttribute("imgs", imgs);
				request.setAttribute("pn", pageNum);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "collect";
	}

	public String load_coll() {
		if (openid != null && !openid.equals("")) {
			try {
				int pageNum = 1;
				if (pn != null && !pn.equals("")) {
					if (Integer.parseInt(pn) > 1) {
						pageNum = Integer.parseInt(pn);
					}
				}
				PageBean<HouseType> pb = serachService.get_collect_info( pageNum, openid);
				List<HouseType> list = pb.getList();
				List<String> ht_idList = new ArrayList<String>();
				for (HouseType h : list) {
					ht_idList.add(h.getHt_id() + "");
				}
				String ht_id;
				ht_id = CommonUtil.getAuthFiledValueString(CommonUtil .getStringArrayToString(ht_idList));
				System.out.println();
				List<Img> imgs = serachService.get_coll_img(ht_id);
				for (Img i : imgs) {
					System.out.println(i.getImg_url());
				}
				request.setAttribute("pb", pb);
				request.setAttribute("list", list);
				request.setAttribute("imgs", imgs);
				request.setAttribute("pn", pageNum);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "load_coll";
	}

	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}

	public int getTag_id() {
		return tag_id;
	}
	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}

	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public int getHt_id() {
		return ht_id;
	}
	public void setHt_id(int ht_id) {
		this.ht_id = ht_id;
	}

	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
	}

	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getIdcard1() {
		return idcard1;
	}
	public void setIdcard1(String idcard1) {
		this.idcard1 = idcard1;
	}

	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getTotal_price() {
		return total_price;
	}
	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}

	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public PayVO getPayVO() {
		return payVO;
	}
	public void setPayVO(PayVO payVO) {
		this.payVO = payVO;
	}

	public WxPayDto getWxPayDto() {
		return wxPayDto;
	}
	public void setWxPayDto(WxPayDto wxPayDto) {
		this.wxPayDto = wxPayDto;
	}

	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	public String getAdd_idcard() {
		return add_idcard;
	}
	public void setAdd_idcard(String add_idcard) {
		this.add_idcard = add_idcard;
	}

	public String getAdd_idcard1() {
		return add_idcard1;
	}
	public void setAdd_idcard1(String add_idcard1) {
		this.add_idcard1 = add_idcard1;
	}

	public int getSch_id() {
		return sch_id;
	}
	public void setSch_id(int sch_id) {
		this.sch_id = sch_id;
	}

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}

	public String getInputLine() {
		return inputLine;
	}
	public void setInputLine(String inputLine) {
		this.inputLine = inputLine;
	}

	public int getTwo_price() {
		return two_price;
	}
	public void setTwo_price(int two_price) {
		this.two_price = two_price;
	}

	/** 测试*****测试 ************************************************************************************/

	public String goWxPayAgainTest() {

		String u_openid = getSessionUser();
		System.out.println("进入支付");
		Order order = (Order) session.getAttribute("orderinfo");
		List<Order> orderlist = new ArrayList<Order>();
		if (order != null) {
			// 添加微信支付记录
			// &&order.getState()==1
			/* serachService.inser_wechat_result(w); */

			// 记录表

			PayVO p = new PayVO();
			p.setPay_time(DataUtil.fromDate24H());
			p.setResult("success");
			p.setPaymoney(order.getAmmount_sum());
			p.setName(u_openid);
			p.setOrderid(order_id);
			p.setTrade_no(111 + "");
			serachService.insert_result_log(p);
			serachService.update_order_allstate(order.getOrder_id(), 2);
			if (order.getIs_f_order() == 2) {
				// 说明是父订单
				List<Order> listod = order.getSunOrders();
				for (Order oo : listod) {
					// 添加房间临时密码,修改状态
					String h_pwd = RandomString.randomNumStr(6);
					serachService.update_order_state(String.valueOf(oo.getOrder_id()), h_pwd);
					// 修改房间日程状态为有效(状态：-1未使用, 1:有效 100:用户取消 101:系统取消 200:用户退房）
					serachService.update_sch_state(oo.getOrder_id(), ISearchService.KEYSTATE[1]);
				}
			} else {
				// 不是父订单：
				// 更改订单状态：支付成功
				serachService.update_order_state(order_id, RandomString.randomNumStr(6));
				// 修改房间日程状态为有效(状态：-1未使用, 1:有效 100:用户取消 101:系统取消 200:用户退房）
				// 修改用户锁信息
				serachService.update_sch_state(Integer.valueOf(order_id), ISearchService.KEYSTATE[1]);
			}

			session.removeAttribute("orderinfo");
			session.removeAttribute("card_list");
			session.removeAttribute("h");
			session.removeAttribute("result");

			if (order.getIs_f_order() == 2) {
				// 父订单
				List<Order> order2 = serachService.get_order_detail2(
						order.getOrder_id(), u_openid, 0);
				for (Order ooo : order2) {
					List<HouseUsers> list = serachService.get_card_house(
							String.valueOf(ooo.getOrder_id()), u_openid,
							ooo.getSch_id());
					ooo.setListhu(list);
				}
				orderlist = order2;
			} else {

				List<Order> order2 = serachService.get_order_detail(
						String.valueOf(order.getOrder_id()), u_openid, 0);
				List<HouseUsers> list = serachService.get_card_house(String
						.valueOf(order.getOrder_id()), u_openid, order2.get(0)
						.getSch_id());
				order2.get(0).setListhu(list);
				orderlist = order2;
			}

			request.setAttribute("order", orderlist);
			return "order_detail";
		} else {
			Order o = serachService.queryOrderByid(order_id);
			if (o != null && o.getState() == 1) {
				// 已支付成功
				// 添加记录
				// 添加微信支付记录
				/* serachService.inser_wechat_result(w); */

				// 记录表
				PayVO p = new PayVO();
				p.setPay_time(DataUtil.fromDate24H());
				p.setResult("success");
				p.setPaymoney(o.getAmmount_sum());
				p.setName(u_openid);
				p.setOrderid(order_id);
				p.setTrade_no(111 + "");
				serachService.insert_result_log(p);
				// 更改订单状态：支付成功,//添加房间临时密码
				serachService.update_order_state(order_id, RandomString.randomNumStr(6));
				// 添加订单日志
				serachService .insert_order_log(order_id, DataUtil.fromDate24H());
				// 修改房间日程状态为有效(状态：-1未使用, 1:有效 100:用户取消 101:系统取消 200:用户退房）
				serachService.update_sch_state(Integer.valueOf(order_id), ISearchService.KEYSTATE[1]);

				session.removeAttribute("orderinfo");
				session.removeAttribute("card_list");
				session.removeAttribute("h");
				session.removeAttribute("result");
			}
		}
		orderlist = serachService.get_order_detail(order_id, u_openid, 0);
		List<HouseUsers> list = serachService.get_card_house(order_id, u_openid, orderlist.get(0).getSch_id());
		orderlist.get(0).setListhu(list);
		request.setAttribute("order", orderlist);

		return "order_detail";
	}
}
