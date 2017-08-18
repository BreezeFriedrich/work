package com.hysm.domain;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class PageBean<T> {
	private int pageSize;
	private int pageNum;
	private int pageCount;
	private int rowCount;
	private List<T> list;
	private List<List> plist;
	private JSONArray json;
	private JSONArray json1;
	
	
	public JSONArray getJson1() {
		return json1;
	}
	public void setJson1(JSONArray json1) {
		this.json1 = json1;
	}
	public JSONArray getJson() {
		return json;
	}
	public void setJson(JSONArray j) {
		this.json = j;
	}
	public List<List> getPlist() {
		return plist;
	}
	public void setPlist(List<List> plist) {
		this.plist = plist;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		setPageCount();
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount() {
		if(rowCount%pageSize==0){
			this.pageCount = rowCount/pageSize;
		}else{
			this.pageCount = (rowCount/pageSize)+1;
		}
	}
	
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
}
