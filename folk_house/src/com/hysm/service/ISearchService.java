package com.hysm.service;

import java.util.List;

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
import com.hysm.domain.WeChatVO;

public interface ISearchService {

	//(状态：-1未使用 1:有效 100:用户取消 101:系统取消 200:用户退房）
	public static final int[] KEYSTATE={-1,1,100,101,200};
	
	

	List<Price> get_price_img(String ht_id, String stime);

	PageBean<HouseType> get_countey_search(int pn, String city_name, String stime,
			String etime, int tag_id);

	int get_house_type_count(String city_name, String stime,
			String etime, int tag_id);
	
	HouseType get_hotel_price(int ht_id);

	List<Img> get_house_img(int ht_id);

	List<Property> get_house_pro(int ht_id);

	List<Supporting> get_house_sup(int ht_id);

	HouseType get_house_info(int ht_id, String stime, String etime);

	HouseType get_hotel_info(int ht_id);

	void insert_order_info(Order order);

	void insert_house_schedule(int house_id, int order_id, String stime, String etime);

	void inser_wechat_result(WeChatVO w);

	void insert_result_log(PayVO payVO);

	void update_order_state(String order_id,String pwd);

	void insert_house_user(List<HouseUsers> h1);

	void insert_order_log(String order_id, String time);

	List<Order> get_order_info(String openid);

	List<Order> get_order_detail(String order_id, String openid, int sch_id);

	List<HouseUsers> get_card_house(String order_id, String openid, int sch_id);

	HouseType get_house_num(String stime, String etime, int ht_id);

	int insert_collect(int ht_id, String openid, String time);


	int get_coll_id(int ht_id, String openid);

	int del_collect(int id);

	PageBean<HouseType> get_collect_info(int pn, String openid);

	List<Img> get_coll_img(String ht_id);

	Collect get_coll_info(int ht_id, String openid);

	Order queryOrderByid(String order_id);

	void insert_order_info2(Order order);

	void update_sch_state(int order_id, int i);

	void update_order_allstate(int order_id, int i);

	List<Order> get_order_detail2(int order_id, String openid, int i);

	




}
