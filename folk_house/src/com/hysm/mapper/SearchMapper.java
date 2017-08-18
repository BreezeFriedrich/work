package com.hysm.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.UpdateProvider;

import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

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
import com.hysm.mapper.sqlpro.SearchPro;

@Repository
public interface SearchMapper {

	@SelectProvider(method="get_price_img",type=SearchPro.class )
	List<Price> get_price_img(@Param("ht_id")String ht_id, @Param("stime")String stime);


	@SelectProvider(method="get_countey_search",type=SearchPro.class )
	List<HouseType> get_countey_search(@Param("beginNum")int beginNum, @Param("ps")int ps,@Param("city_name") String city_name,
			@Param("stime")String stime, @Param("etime")String etime, @Param("tag_id")int tag_id);
	
	@SelectProvider(method="get_house_type_count",type=SearchPro.class )
	int get_house_type_count(@Param("city_name") String city_name,
			@Param("stime")String stime, @Param("etime")String etime, @Param("tag_id")int tag_id);
	
	
	@SelectProvider(method="get_hotel_price",type=SearchPro.class )
	HouseType get_hotel_price(int ht_id);

	@SelectProvider(method="get_house_img",type=SearchPro.class )
	List<Img> get_house_img(int ht_id);

	@SelectProvider(method="get_house_pro",type=SearchPro.class )
	List<Property> get_house_pro(int ht_id);
	
	@SelectProvider(method="get_house_sup",type=SearchPro.class )
	List<Supporting> get_house_sup(int ht_id);

	
	@SelectProvider(method="get_house_info",type=SearchPro.class )
	HouseType get_house_info(@Param("ht_id")int ht_id, @Param("stime")String stime, @Param("etime")String etime);

	@SelectProvider(method="get_hotel_info",type=SearchPro.class )
	HouseType get_hotel_info(int ht_id);

	

	@InsertProvider(method="insert_house_schedule",type=SearchPro.class )
	void insert_house_schedule(@Param("house_id")int house_id,@Param("order_id")int order_id, @Param("stime")String stime, @Param("etime")String etime);
	
	@InsertProvider(method="inser_wechat_result",type=SearchPro.class )
	void inser_wechat_result(WeChatVO w);

	@InsertProvider(method="insert_result_log",type=SearchPro.class )
	void insert_result_log(PayVO payVO);
	
	@InsertProvider(method="update_order_state",type=SearchPro.class )
	void update_order_state(@Param("order_id")String order_id,@Param("h_pwd")String pwd);

	@InsertProvider(method="insert_house_user",type=SearchPro.class )
	void insert_house_user(Map<String,List<HouseUsers>> map);
	
	@InsertProvider(method="insert_order_log",type=SearchPro.class )
	void insert_order_log(@Param("order_id")String order_id, @Param("time")String time);
	
	@SelectProvider(method="get_order_info",type=SearchPro.class )
	List<Order> get_order_info(@Param("openid")String openid);
	
	@SelectProvider(method="get_order_detail",type=SearchPro.class )
	List<Order> get_order_detail(@Param("order_id")String order_id, @Param("openid")String openid,@Param("sch_id")int sch_id);

	@SelectProvider(method="get_card_house",type=SearchPro.class )
	List<HouseUsers> get_card_house(@Param("order_id")String order_id, @Param("openid")String openid,@Param("sch_id")int sch_id);
	
	@SelectProvider(method="get_house_num",type=SearchPro.class )
	HouseType get_house_num(@Param("stime")String stime, @Param("etime")String etime,@Param("ht_id") int ht_id);
	
	@InsertProvider(method="insert_collect",type=SearchPro.class )
	int insert_collect(@Param("ht_id")int ht_id, @Param("openid")String openid, @Param("time")String time);

	@SelectProvider(method="get_coll_id",type=SearchPro.class )
	int get_coll_id(@Param("ht_id")int ht_id, @Param("openid")String openid);

	@InsertProvider(method="del_collect",type=SearchPro.class )
	int del_collect(int id);
	
	@SelectProvider(method="get_collect_info",type=SearchPro.class )
	List<HouseType> get_collect_info(@Param("openid")String openid,@Param("beginNum")int beginNum, @Param("ps")int ps);
	
	@SelectProvider(method="get_collect_count",type=SearchPro.class )
	int get_collect_count(String openid);

	@SelectProvider(method="get_coll_img",type=SearchPro.class )
	List<Img> get_coll_img(@Param("ht_id")String ht_id);
	
	@SelectProvider(method="get_coll_info",type=SearchPro.class )
	Collect get_coll_info(@Param("ht_id")int ht_id, @Param("openid")String openid);


	@SelectProvider(method="get_order_detail2",type=SearchPro.class )
	List<Order> get_order_detail2(@Param("order_id")String order_id, @Param("openid")String openid);
	@SelectProvider(method="get_order_detail3",type=SearchPro.class )
	List<Order> get_order_detail3(@Param("order_id")int order_id, @Param("openid")String openid);
	
	
	
	@SelectProvider(method="queryOrderByid",type=SearchPro.class )
	@Results({
		@Result(property="house_id",column="House_id"),
		@Result(property="ht_id",column="ht_id"),
		@Result(property="house",column="ht_id",one=@One(select="com.hysm.mapper.SearchMapper.get_hotel_info")),
		@Result(property="listhu",column="House_id",many=@Many(select="com.hysm.mapper.SearchMapper.queryhouseuserByhid"))
		
	})
	Order queryOrderByid(@Param("orderid")Integer orderid);

	@SelectProvider(method="queryhouseuserByhid",type=SearchPro.class )
	@Results({
		
		@Result(property="cardid",column="CARDID"),
		
	})
	public List<HouseUsers> queryhouseuserByhid(@Param("houseid")int houseid);

	@InsertProvider(method="insert_order_info",type=SearchPro.class )
	@SelectKey(before=false,keyProperty="order_id",resultType=Integer.class,statementType=StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS order_id")
	void insert_order_info(Order order);

	@InsertProvider(method="insert_order_info2",type=SearchPro.class )
	@SelectKey(before=false,keyProperty="order_id",resultType=Integer.class,statementType=StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS order_id")
	void insert_order_info2(Order order);

	@UpdateProvider(method="update_sch_state",type=SearchPro.class )
	void update_sch_state(@Param("orderid")int order_id,@Param("state") int i);

	@UpdateProvider(method="update_hu_state",type=SearchPro.class )
	void update_hu_state(@Param("orderid")int order_id,@Param("state") int i);

	@UpdateProvider(method="update_hu_pwd",type=SearchPro.class )
	void update_hu_pwd(@Param("orderid")int order_id, @Param("pwd")int pwd);

	@UpdateProvider(method="update_order_allstate",type=SearchPro.class )
	void update_order_allstate(@Param("orderid")Integer valueOf, @Param("state")int state);

	@UpdateProvider(method="updateOrderCanclTime",type=SearchPro.class )
	void updateOrderCanclTime(@Param("orderid")int order_id);






	
	
	

}
