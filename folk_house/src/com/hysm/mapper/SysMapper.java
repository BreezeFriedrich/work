package com.hysm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.hysm.domain.Hotel;
import com.hysm.mapper.sqlpro.HousePro;
import com.hysm.mapper.sqlpro.SysPro;
import com.hysm.pojo.Category;
import com.hysm.pojo.Lock;
import com.hysm.pojo.Manager;
import com.hysm.pojo.Merchants;
import com.hysm.pojo.Order;
import com.hysm.pojo.PageBean;
import com.hysm.pojo.Supporting;
import com.hysm.pojo.Tag;
import com.hysm.pojo.User;

@Repository
public interface SysMapper {
	
	@SelectProvider(method="regs_name",type=SysPro.class )
	Manager regs_name(String name);

	@SelectProvider(method="regs_pwd",type=SysPro.class )
	Manager regs_pwd(String pwd);

	@SelectProvider(method="get_category_list",type=SysPro.class )
	List<Category> get_category_list(@Param("beginNum")int beginNum, @Param("ps")int ps);
	
	@SelectProvider(method="get_category_count",type=SysPro.class )
	int get_category_count();

	@SelectProvider(method="get_categoey_name",type=SysPro.class )
	List<Category> get_categoey_name();

	@InsertProvider(method="add_category",type=SysPro.class )
	int add_category(String c_name);

	@DeleteProvider(method="del_category",type=SysPro.class )
	int del_category(int id);

	@SelectProvider(method="get_category",type=SysPro.class )
	Category get_category(int id);

	@UpdateProvider(method="edit_category",type=SysPro.class )
	int edit_category(@Param("c_name")String c_name,@Param("state") int state,@Param("id") int id);

	@SelectProvider(method="get_tag_count",type=SysPro.class )
	int get_tag_count();

	@SelectProvider(method="get_tag_list",type=SysPro.class )
	List<Tag> get_tag_list(@Param("beginNum")int beginNum, @Param("ps")int ps, @Param("city")String city, @Param("search_id")String search_id);

	@SelectProvider(method="get_tag_z",type=SysPro.class )
	List<Tag> get_tag_z(int id);
	
	@InsertProvider(method="add_tag",type=SysPro.class )
	int add_tag(@Param("tag_name")String tag_name,@Param("parent_id")int parent_id,@Param("domains_id")String domains_id);
	
	@SelectProvider(method="get_tag",type=SysPro.class )
	Tag get_tag(int id);
	
	@SelectProvider(method="get_tag1",type=SysPro.class )
	Tag get_tag1(int id);
	
	@SelectProvider(method="get_tag3",type=SysPro.class )
	Tag get_tag3(int id);

	@DeleteProvider(method="del_tag",type=SysPro.class )
	int del_tag(int id);

	@SelectProvider(method="get_supporting_count",type=SysPro.class )
	int get_supporting_count(int sup_type);

	@SelectProvider(method="get_supporting_list",type=SysPro.class )
	List<Supporting> get_supporting_list(@Param("beginNum")int beginNum, @Param("ps")int ps, @Param("sup_type")int sup_type);
	
	@InsertProvider(method="insert_supporting",type=SysPro.class )
	void insert_supporting(@Param("sup_type")int sup_type, @Param("sup_name")String sup_name, @Param("img_url")String img_url);
	
	@DeleteProvider(method="del_sup",type=SysPro.class )
	int del_sup(int id);

	@SelectProvider(method="get_sup",type=SysPro.class )
	Supporting get_sup(int id);

	@InsertProvider(method="update_sup",type=SysPro.class )
	void update_sup(@Param("sup_name")String sup_name, @Param("img_url")String img_url, @Param("sup_type")int sup_type,
			@Param("state")int state, @Param("sup_id")int id);

	@UpdateProvider(method="edit_tag",type=SysPro.class )
	int edit_tag(@Param("id")int id, @Param("tag_name")String tag_name, @Param("state")int state, @Param("domains_id")String domains_id);

	@SelectProvider(method="get_sup_name",type=SysPro.class )
	List<Supporting> get_sup_name(int type);

	@SelectProvider(method="get_order_count",type=SysPro.class )
	int get_order_count(@Param("stime")String stime, @Param("etime")String etime, @Param("merchants_id")String merchants_id,
			@Param("state")int state,@Param("user_id")int user_id);

	@SelectProvider(method="get_order_list",type=SysPro.class )
	List<Order> get_order_list(@Param("beginNum")int beginNum, @Param("ps")int ps, @Param("stime")String stime, 
				@Param("etime")String etime, @Param("merchants_id")String merchants_id,@Param("state")int state,@Param("user_id")int user_id);

	@SelectProvider(method="get_merchants",type=SysPro.class )
	List<Merchants> get_merchants();

	@SelectProvider(method="get_merchants_count",type=SysPro.class )
	int get_merchants_count( @Param("stime")String stime, 
			@Param("etime")String etime,@Param("m_state")String m_state);

	@SelectProvider(method="get_merchants_list",type=SysPro.class )
	List<Merchants> get_merchants_list(@Param("beginNum")int beginNum, @Param("ps")int ps, @Param("stime")String stime, 
			@Param("etime")String etime,@Param("m_state")String m_state);

	@UpdateProvider(method="edit_merchants",type=SysPro.class )
	int edit_merchants(@Param("id")int id, @Param("m_state")int m_state);

	@SelectProvider(method="get_user_count",type=SysPro.class )
	int get_user_count(@Param("stime")String stime, @Param("etime")String etime);

	@SelectProvider(method="get_user_list",type=SysPro.class )
	List<User> get_user_list(@Param("beginNum")int beginNum, @Param("ps")int ps, @Param("stime")String stime, 
			@Param("etime")String etime);

	@InsertProvider(method="add_lock",type=SysPro.class )
	int add_lock(@Param("lock_name")String lock_name, @Param("is_idcard")String is_idcard, @Param("is_pwd")String is_pwd);

	@SelectProvider(method="get_lock_count",type=SysPro.class )
	int get_lock_count();
	
	@SelectProvider(method="get_lock_list",type=SysPro.class )
	List<Lock> get_lock_list(@Param("beginNum")int beginNum, @Param("ps")int ps);

	@DeleteProvider(method="del_lock",type=SysPro.class )
	int del_lock(int id);

	@SelectProvider(method="get_lock",type=SysPro.class )
	Lock get_lock(int id);

	@UpdateProvider(method="edit_lock",type=SysPro.class )
	int edit_lock(@Param("lock_name")String lock_name, @Param("is_idcard")String is_idcard, @Param("is_pwd")String is_pwd,
			@Param("state")int state, @Param("id")int id);

	@SelectProvider(method="get_hotel_list",type=SysPro.class )
	List<Hotel> get_hotel_list();


}
