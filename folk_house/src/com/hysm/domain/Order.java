package com.hysm.domain;

import java.util.List;

import com.hysm.service.ISearchService;

public class Order {
	//状态：-1未使用 1:正常 100:用户取消 101:系统取消 200:用户退房
	private int order_id,house_id,ammount_sum,payed_sum,state,sch_id;
	private String name,openid,createtime,pay_time,cancel_time,order_num,ht_name,starttime,endtime,house_num,m_tel,name1,name2,time1,address,tel;
	private int f_orderid;//父订单id
	private int is_f_order;//1正常订单，2父订单
	private List<Order> sunOrders;
	
	private List<HouseUsers> listhu;
	private HouseType house;
	private int  ht_id;
	
	private int cash_pledge;//押金，精确到分
	
	private int h_pwd;//房间临时密码；
	
	private String lockid;
	
	
	public String getLockid() {
		return lockid;
	}
	public void setLockid(String lockid) {
		this.lockid = lockid;
	}
	public int getH_pwd() {
		return h_pwd;
	}
	public void setH_pwd(int h_pwd) {
		this.h_pwd = h_pwd;
	}
	public int getCash_pledge() {
		return cash_pledge;
	}
	public void setCash_pledge(int cash_pledge) {
		this.cash_pledge = cash_pledge;
	}
	public int getHt_id() {
		return ht_id;
	}
	public void setHt_id(int ht_id) {
		this.ht_id = ht_id;
	}
	public HouseType getHouse() {
		return house;
	}
	public void setHouse(HouseType house) {
		this.house = house;
	}
	public List<HouseUsers> getListhu() {
		return listhu;
	}
	public void setListhu(List<HouseUsers> listhu) {
		this.listhu = listhu;
	}
	public int getSch_id() {
		return sch_id;
	}
	public void setSch_id(int sch_id) {
		this.sch_id = sch_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTime1() {
		return time1;
	}
	public void setTime1(String time1) {
		this.time1 = time1;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getHouse_id() {
		return house_id;
	}
	public void setHouse_id(int house_id) {
		this.house_id = house_id;
	}
	public int getAmmount_sum() {
		return ammount_sum;
	}
	public void setAmmount_sum(int ammount_sum) {
		this.ammount_sum = ammount_sum;
	}
	public int getPayed_sum() {
		return payed_sum;
	}
	public void setPayed_sum(int payed_sum) {
		this.payed_sum = payed_sum;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getPay_time() {
		return pay_time;
	}
	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}
	public String getCancel_time() {
		return cancel_time;
	}
	public void setCancel_time(String cancel_time) {
		this.cancel_time = cancel_time;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	public String getHt_name() {
		return ht_name;
	}
	public void setHt_name(String ht_name) {
		this.ht_name = ht_name;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getHouse_num() {
		return house_num;
	}
	public void setHouse_num(String house_num) {
		this.house_num = house_num;
	}
	public String getM_tel() {
		return m_tel;
	}
	public void setM_tel(String m_tel) {
		this.m_tel = m_tel;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public int getF_orderid() {
		return f_orderid;
	}
	public void setF_orderid(int f_orderid) {
		this.f_orderid = f_orderid;
	}
	public int getIs_f_order() {
		return is_f_order;
	}
	public void setIs_f_order(int is_f_order) {
		this.is_f_order = is_f_order;
	}
	public List<Order> getSunOrders() {
		return sunOrders;
	}
	public void setSunOrders(List<Order> sunOrders) {
		this.sunOrders = sunOrders;
	}
	
	
	
	
}
