package com.hysm.pojo;

import java.util.List;

public class Tag {
	private int tag_id,parent_id,state,ftag_id,fparent_id;
	private String tag_name,d_name,ftag_name,data,domains_id;
	private List<Tag> list1;
	private List<Tag> list2;
	
	
	
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getFtag_id() {
		return ftag_id;
	}
	public void setFtag_id(int ftag_id) {
		this.ftag_id = ftag_id;
	}
	public int getFparent_id() {
		return fparent_id;
	}
	public void setFparent_id(int fparent_id) {
		this.fparent_id = fparent_id;
	}
	public String getFtag_name() {
		return ftag_name;
	}
	public void setFtag_name(String ftag_name) {
		this.ftag_name = ftag_name;
	}
	public List<Tag> getList1() {
		return list1;
	}
	public void setList1(List<Tag> list1) {
		this.list1 = list1;
	}
	public List<Tag> getList2() {
		return list2;
	}
	public void setList2(List<Tag> list2) {
		this.list2 = list2;
	}
	public String getD_name() {
		return d_name;
	}
	public void setD_name(String d_name) {
		this.d_name = d_name;
	}
	public int getTag_id() {
		return tag_id;
	}
	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	
	public String getDomains_id() {
		return domains_id;
	}
	public void setDomains_id(String domains_id) {
		this.domains_id = domains_id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}
	
	
	
	
}
