package com.hysm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.hysm.mapper.SearchMapper;
import com.hysm.pojo.Category;
import com.hysm.service.ISearchService;

@Service
public class SearchServiceImpl implements ISearchService{

	@Autowired
	private SearchMapper searchMapper;

	public List<Price> get_price_img(String ht_id, String stime) {
		return searchMapper.get_price_img(ht_id,stime);
	}

	public PageBean<HouseType> get_countey_search(int pn,String city_name, String stime, String etime, int tag_id) {
		PageBean<HouseType> pb=new PageBean<HouseType>();
		int ps=10;
		int beginNum=(pn-1)*ps;
		pb.setPageSize(ps);
		pb.setPageNum(pn);
		/*int rowCount=searchMapper.get_house_type_count(city_name,stime,etime,tag_id);
		pb.setRowCount(rowCount);*/
		List<HouseType> list=searchMapper.get_countey_search(beginNum, ps,city_name,stime,etime,tag_id);
		pb.setList(list);
		return pb;
	}

	public HouseType get_hotel_price(int ht_id) {
		return searchMapper.get_hotel_price(ht_id);
	}

	public List<Img> get_house_img(int ht_id) {
		return searchMapper.get_house_img(ht_id);
	}

	public List<Property> get_house_pro(int ht_id) {
		return searchMapper.get_house_pro(ht_id);
	}

	public List<Supporting> get_house_sup(int ht_id) {
		return searchMapper.get_house_sup(ht_id);
	}

	public int get_house_type_count(String city_name, String stime, String etime, int tag_id) {
		return searchMapper.get_house_type_count(city_name,stime,etime,tag_id);
	}

	public HouseType get_house_info(int ht_id, String stime, String etime) {
		return searchMapper.get_house_info(ht_id, stime, etime);
	}

	public HouseType get_hotel_info(int ht_id) {
		return searchMapper.get_hotel_info(ht_id);
	}

	public void insert_order_info(Order order) {
		searchMapper.insert_order_info(order);
	}

	public void insert_house_schedule(int house_id, int order_id, String stime, String etime) {
		searchMapper.insert_house_schedule(house_id, order_id, stime, etime);
	}

	public void inser_wechat_result(WeChatVO w) {
		searchMapper.inser_wechat_result(w);
	}

	public void insert_result_log(PayVO payVO) {
		searchMapper.insert_result_log(payVO);
	}

	public void update_order_state(String order_id,String pwd) {
		//在订单里修改
		searchMapper.update_order_state(order_id,pwd);
		//在houseUser里修改
		searchMapper.update_hu_pwd(Integer.valueOf(order_id),Integer.valueOf(pwd));
	}

	public void update_order_allstate(int order_id,int state) {
		//在订单里修改
		searchMapper.update_order_allstate(order_id,state);
		if(state==5){
			searchMapper.updateOrderCanclTime(order_id);		
		}
		
	}

	public void insert_house_user(List<HouseUsers> h1) {
		Map ss=new HashMap<String,List<HouseUsers>>();
		ss.put("houseusers", h1);
		searchMapper.insert_house_user(ss);
	}

	public void insert_order_log(String order_id, String time) {
		searchMapper.insert_order_log(order_id, time);
	}

	public List<Order> get_order_info(String openid) {
		return searchMapper.get_order_info(openid);
	}

	public List<Order> get_order_detail(String order_id, String openid,int sch_id) {
		if(sch_id==0){
			return searchMapper.get_order_detail2(order_id, openid);
		}else{
			return searchMapper.get_order_detail(order_id, openid,sch_id);
		}
	}

	public List<Order> get_order_detail2(int order_id, String openid,int sch_id) {
		return searchMapper.get_order_detail3(order_id, openid);
	}

	public List<HouseUsers> get_card_house(String order_id, String openid,int sch_id) {
		return searchMapper.get_card_house(order_id, openid,sch_id);
	}

	public HouseType get_house_num(String stime, String etime, int ht_id) {
		return searchMapper.get_house_num(stime, etime, ht_id);
	}

	public int insert_collect(int ht_id, String openid, String time) {
		return searchMapper.insert_collect(ht_id, openid, time);
	}

	public int get_coll_id(int ht_id, String openid) {
		return searchMapper.get_coll_id(ht_id, openid);
	}

	public int del_collect(int id) {
		return searchMapper.del_collect(id);
	}

	public PageBean<HouseType> get_collect_info(int pn,String openid) {
		System.out.println("openid:"+openid);
		PageBean<HouseType> pb=new PageBean<HouseType>();
		int ps=10;
		int beginNum=(pn-1)*ps;
		pb.setPageSize(ps);
		pb.setPageNum(pn);
		int rowCount=searchMapper.get_collect_count(openid);
		pb.setRowCount(rowCount);
		pb.setPageSize(10);
		List<HouseType> list=searchMapper.get_collect_info(openid, beginNum, ps);
		pb.setList(list);
		return pb;
	}

	public List<Img> get_coll_img(String ht_id) {
		return searchMapper.get_coll_img(ht_id);
	}

	public Collect get_coll_info(int ht_id, String openid) {
		return searchMapper.get_coll_info(ht_id, openid);
	}

	@Override
	public Order queryOrderByid(String order_id) {
		return searchMapper.queryOrderByid(Integer.valueOf(order_id));
	}

	@Override
	public void insert_order_info2(Order order) {
		searchMapper.insert_order_info2(order);
	}

	@Override
	public void update_sch_state(int order_id, int i) {
		//修改日程表
		searchMapper.update_sch_state(order_id,i);
		//修改houseuser
		searchMapper.update_hu_state(order_id,i);
	}

}
