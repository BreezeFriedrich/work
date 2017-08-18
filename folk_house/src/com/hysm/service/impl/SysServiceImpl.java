package com.hysm.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.domain.Hotel;
import com.hysm.mapper.SysMapper;
import com.hysm.pojo.Category;
import com.hysm.pojo.Lock;
import com.hysm.pojo.Manager;
import com.hysm.pojo.Merchants;
import com.hysm.pojo.Order;
import com.hysm.pojo.PageBean;
import com.hysm.pojo.Supporting;
import com.hysm.pojo.Tag;
import com.hysm.pojo.User;
import com.hysm.service.ISysService;

@Service
public class SysServiceImpl implements ISysService{
	
	@Autowired
	private SysMapper sysMapper;

	public Manager regs_name(String name) {
		return sysMapper.regs_name(name);
	}

	public Manager regs_pwd(String pwd) {
		return sysMapper.regs_pwd(pwd);
	}

	public PageBean<Category> get_category_list(int pn) {
		PageBean<Category> pb=new PageBean<Category>();
		int ps=10;
		int beginNum=(pn-1)*ps;
		pb.setPageSize(ps);
		pb.setPageNum(pn);
		int rowCount=sysMapper.get_category_count();
		pb.setRowCount(rowCount);
		List<Category> list=sysMapper.get_category_list(beginNum, ps);
		pb.setList(list);
		return pb;
	}

	public List<Category> get_categoey_name() {
		return sysMapper.get_categoey_name();
	}

	public int add_category(String c_name) {
		return sysMapper.add_category(c_name);
	}

	public int del_category(int id) {
		return sysMapper.del_category(id);
	}

	public Category get_category(int id) {
		return sysMapper.get_category(id);
	}

	public int edit_category(String c_name, int state, int id) {
		return sysMapper.edit_category(c_name, state, id);
	}

	public PageBean<Tag> get_tag_list(int pn, String city, String search_id) {
		PageBean<Tag> pb=new PageBean<Tag>();
		int ps=20;
		int beginNum=(pn-1)*ps;
		pb.setPageSize(ps);
		pb.setPageNum(pn);
		int rowCount=sysMapper.get_tag_count();
		pb.setRowCount(rowCount);
		List<Tag> list=sysMapper.get_tag_list(beginNum, ps,city,search_id);
		pb.setList(list);
		return pb;
	}

	public List<Tag> get_tag_z(int id) {
		return sysMapper.get_tag_z(id);
	}

	public int add_tag(String tag_name, int parent_id, String domains_id){
		return sysMapper.add_tag(tag_name,parent_id,domains_id);
	}

	public Tag get_tag(int id,String type) {
		if(type!=null && !type.equals("")){
			if(Integer.parseInt(type)==1){
				return sysMapper.get_tag1(id);
			}else if(Integer.parseInt(type)==2){
				return sysMapper.get_tag(id);
			}else if(Integer.parseInt(type)==3){
			}
		}
		return sysMapper.get_tag3(id);
	}

	public int del_tag(int id) {
		return sysMapper.del_tag(id);
	}

	public PageBean<Supporting> get_supporting_list(int pn, int sup_type) {
		PageBean<Supporting> pb=new PageBean<Supporting>();
		int ps=20;
		int beginNum=(pn-1)*ps;
		pb.setPageSize(ps);
		pb.setPageNum(pn);
		int rowCount=sysMapper.get_supporting_count(sup_type);
		pb.setRowCount(rowCount);
		List<Supporting> list=sysMapper.get_supporting_list(beginNum, ps,sup_type);
		pb.setList(list);
		return pb;
	}

	public void insert_supporting(int sup_type, String sup_name, String img_url) {
		sysMapper.insert_supporting(sup_type, sup_name, img_url);
	}

	public int del_sup(int id) {
		return sysMapper.del_sup(id);
	}

	public Supporting get_sup(int id) {
		return sysMapper.get_sup(id);
	}

	public void update_sup(String sup_name, String img_url, int sup_type,
			int state, int id) {
		sysMapper.update_sup(sup_name, img_url, sup_type, state, id);
	}

	public int edit_tag(int id, String tag_name, int state,
			String domains_id) {
		return sysMapper.edit_tag(id,tag_name,state,domains_id);
	}

	public List<Supporting> get_sup_name(int type) {
		return sysMapper.get_sup_name(type);
	}

	public PageBean<Order> get_order_list(int pn, String stime,
			String etime, String merchants_id,int state, int user_id) {
		PageBean<Order> pb=new PageBean<Order>();
		int ps=10;
		int beginNum=(pn-1)*ps;
		pb.setPageSize(ps);
		pb.setPageNum(pn);
		int rowCount=sysMapper.get_order_count(stime,etime,merchants_id,state,user_id);
		pb.setRowCount(rowCount);
		List<Order> list=sysMapper.get_order_list(beginNum, ps,stime,etime,merchants_id,state,user_id);
		pb.setList(list);
		return pb;
	}

	public List<Merchants> get_merchants() {
		return sysMapper.get_merchants();
	}

	public PageBean<Merchants> get_merchants_list(int pn, String stime,
			String etime, String m_state) {
		PageBean<Merchants> pb=new PageBean<Merchants>();
		int ps=10;
		int beginNum=(pn-1)*ps;
		pb.setPageSize(ps);
		pb.setPageNum(pn);
		int rowCount=sysMapper.get_merchants_count(stime,etime,m_state);
		pb.setRowCount(rowCount);
		List<Merchants> list=sysMapper.get_merchants_list(beginNum, ps,stime,etime,m_state);
		pb.setList(list);
		return pb;
	}

	public int edit_merchants(int id, int m_state) {
		return sysMapper.edit_merchants(id, m_state);
	}

	public PageBean<User> get_user_list(int pn, String stime,
			String etime) {
		PageBean<User> pb=new PageBean<User>();
		int ps=10;
		int beginNum=(pn-1)*ps;
		pb.setPageSize(ps);
		pb.setPageNum(pn);
		int rowCount=sysMapper.get_user_count(stime,etime);
		pb.setRowCount(rowCount);
		List<User> list=sysMapper.get_user_list(beginNum, ps,stime,etime);
		pb.setList(list);
		return pb;
	}

	public int add_tag(String lock_name, String is_idcard, String is_pwd) {
		return sysMapper.add_lock(lock_name, is_idcard, is_pwd);
	}

	public PageBean<Lock> lock_list(int pn) {
		PageBean<Lock> pb=new PageBean<Lock>();
		int ps=10;
		int beginNum=(pn-1)*ps;
		pb.setPageSize(ps);
		pb.setPageNum(pn);
		int rowCount=sysMapper.get_lock_count();
		pb.setRowCount(rowCount);
		List<Lock> list=sysMapper.get_lock_list(beginNum, ps);
		pb.setList(list);
		return pb;
	}

	public int del_lock(int id) {
		return sysMapper.del_lock(id);
	}

	public Lock get_lock(int id) {
		return sysMapper.get_lock(id);
	}

	public int edit_lock(String lock_name, String is_idcard, String is_pwd,
			int state, int id) {
		return sysMapper.edit_lock(lock_name, is_idcard, is_pwd, state, id);
	}

	public List<Hotel> get_hotel_list() {
		return sysMapper.get_hotel_list();
	}

	

	
}
