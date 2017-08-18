package com.hysm.domain;

public class HouseUsers{
	private int rid,houseid,state;//状态：-1:未使用 1:正常 100:用户取消 101:系统取消 200:用户退房
	private String lockid,cardid,starttime,endtime,house_num;
	private int order_id;
	private int h_pwd;
	
	 
	
	public int getH_pwd() {
		return h_pwd;
	}
	public void setH_pwd(int h_pwd) {
		this.h_pwd = h_pwd;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getHouse_num() {
		return house_num;
	}
	public void setHouse_num(String house_num) {
		this.house_num = house_num;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getHouseid() {
		return houseid;
	}
	public void setHouseid(int houseid) {
		this.houseid = houseid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getLockid() {
		return lockid;
	}
	public void setLockid(String lockid) {
		this.lockid = lockid;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
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
	
	
}
