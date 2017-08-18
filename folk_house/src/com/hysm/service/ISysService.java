package com.hysm.service;

import java.util.List;

import com.hysm.domain.Hotel;
import com.hysm.pojo.Category;
import com.hysm.pojo.Lock;
import com.hysm.pojo.Manager;
import com.hysm.pojo.Merchants;
import com.hysm.pojo.Order;
import com.hysm.pojo.PageBean;
import com.hysm.pojo.Supporting;
import com.hysm.pojo.Tag;
import com.hysm.pojo.User;

public interface ISysService {

	Manager regs_name(String name);

	Manager regs_pwd(String pwd);

	PageBean<Category> get_category_list(int pageNum);

	List<Category> get_categoey_name();

	int add_category(String c_name);

	int del_category(int id);

	Category get_category(int id);

	int edit_category(String c_name, int state, int id);

	PageBean<Tag> get_tag_list(int pageNum, String city, String search_id);

	List<Tag> get_tag_z(int parseInt);

	int add_tag(String tag_name, int parent_id, String domains_id);

	Tag get_tag(int id, String type);

	int del_tag(int id);

	PageBean<Supporting> get_supporting_list(int pageNum, int sup_type);

	void insert_supporting(int sup_type, String sup_name, String img_url);

	int del_sup(int id);

	Supporting get_sup(int id);

	void update_sup(String sup_name, String img_url, int sup_type,
			int state, int id);

	int edit_tag(int parseInt, String tag_name, int state, String domains_id);

	List<Supporting> get_sup_name(int type);

	PageBean<Order> get_order_list(int pageNum, String stime, String etime,
			String merchants_id, int state, int user_id);

	List<Merchants> get_merchants();

	PageBean<Merchants> get_merchants_list(int pageNum, String stime,
			String etime, String m_state);

	int edit_merchants(int id, int m_state);

	PageBean<User> get_user_list(int pageNum, String stime, String etime);

	int add_tag(String lock_name, String is_idcard, String is_pwd);

	PageBean<Lock> lock_list(int pageNum);

	int del_lock(int parseInt);

	Lock get_lock(int parseInt);

	int edit_lock(String lock_name, String is_idcard, String is_pwd,
			int state, int parseInt);

	List<Hotel> get_hotel_list();



	
}
