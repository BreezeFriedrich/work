package com.hysm.mapper.sqlpro;

import java.util.List;
import java.util.Map;

import com.hysm.domain.HouseUsers;
import com.hysm.domain.Order;

public class SearchPro {

	/**
	 * 国内搜索
	 * @return
	 */
	public String get_countey_search(Map<String, Object> map){
		String city_name=(String) map.get("city_name");
		String stime=(String) map.get("stime");
		String etime=(String) map.get("etime");
		int tag_id=(Integer) map.get("tag_id");
		System.out.println(city_name);
		String sql =" SELECT  a.ht_id,b.ht_name, COUNT(a.house_id) as num,c.*";
		       sql+=" FROM h_house a,  m_hotel c,  h_house_type b ";
		       sql+=" WHERE a.house_id IN (SELECT  house_id FROM h_house_schedule WHERE";
		       sql+="  '"+etime+"' < endtime   OR '"+stime+"' >starttime  union all  ";
		       sql+=" (SELECT  house_id FROM h_house WHERE  house_id NOT IN (SELECT   house_id FROM  h_house_schedule))) ";
		       sql+=" AND a.house_state = 2 ";
		       sql+=" AND a.ht_id IN (SELECT  ht_id FROM  h_house_type ";
		       sql+=" WHERE hotel_id IN (SELECT a.id ";
		       sql+=" FROM  m_hotel a,h_tag b,h_hotel_tag c,h_domains d ";
		       sql+=" WHERE a.city=d.id ";
		if(city_name!=null && !city_name.equals("")){
			   sql+=" and d.name='"+city_name+"'";
		}
			   sql+=" and a.id=c.hotel_id and b.tag_id=c.tag_id ";
	    if(tag_id!=0){
	    	   sql+=" and c.tag_id="+tag_id+"";
	    }
	    	   sql+=" ) AND state = 1) ";
	    	   sql+=" and a.ht_id=b.ht_id and b.hotel_id=c.id ";
	    	   sql+=" GROUP BY a.ht_id desc";
	    	   sql+=" limit #{beginNum},#{ps} ";
		return sql;
	}
	
	
	public String get_house_type_count(Map<String, Object> map){
		String city_name=(String) map.get("city_name");
		String stime=(String) map.get("stime");
		String etime=(String) map.get("etime");
		int tag_id=(Integer) map.get("tag_id");
		System.out.println(city_name);
		String sql =" SELECT  count(a.ht_id) ";
		       sql+=" FROM h_house a,h_house_type b,m_hotel c ";
		       sql+=" WHERE a.house_id IN (SELECT  house_id FROM h_house_schedule WHERE";
		       sql+="  '"+etime+"' < endtime   OR '"+stime+"' >starttime  union all  ";
		       sql+=" (SELECT  house_id FROM h_house WHERE  house_id NOT IN (SELECT   house_id FROM  h_house_schedule))) ";
		       sql+=" AND a.house_state = 2 ";
		       sql+=" AND a.ht_id IN (SELECT  ht_id FROM  h_house_type ";
		       sql+=" WHERE hotel_id IN (SELECT a.id ";
		       sql+=" FROM  m_hotel a,h_tag b,h_hotel_tag c,h_domains d ";
		       sql+=" WHERE a.city=d.id ";
		if(city_name!=null && !city_name.equals("")){
			   sql+=" and d.name='"+city_name+"'";
		}
			   sql+=" and a.id=c.hotel_id and b.tag_id=c.tag_id ";
	    if(tag_id!=0){
	    	   sql+=" and c.tag_id="+tag_id+"";
	    }
	    	   sql+=" ) AND state = 1) ";
	    	   sql+=" and a.ht_id=b.ht_id and b.hotel_id=c.id ";
	    	   sql+=" GROUP BY a.ht_id desc";
		return sql;
	}
	
	
	/**
	 * 根据类型查询图片与价格
	 * @return
	 */
	public String get_price_img(Map<String, String> map){
		String stime=map.get("stime");
		String ht_id=map.get("ht_id");
		System.out.println(ht_id);
		String sql =" SELECT  a.img_url,c.*,d.addprice FROM h_img a,h_price c, ";
		       sql+=" h_house_type b left join  h_holiday_price d on d.ht_id=b.ht_id and d.begintime<'"+stime+"'<d.endtime  ";
		       sql+=" WHERE a.ht_id = b.ht_id and c.ht_id=b.ht_id ";
		if(ht_id!=null && !ht_id.equals("")){
			   sql+=" and b.ht_id in("+ht_id+" )";
		}
			   sql+=" group by a.ht_id ";
		return sql;
	}
	
	
	/**
	 * 房屋价格，酒店信息
	 * @return
	 */
	public String get_hotel_price(){
		String sql =" select a.ht_name,b.*,c.* from h_house_type a,h_price b,m_hotel c ";
		       sql+=" where a.ht_id=b.ht_id and a.hotel_id=c.id and a.ht_id=#{ht_id}";
		return sql;
	}
	
	/**
	 * 房屋图片
	 * @return
	 */
	public String get_house_img(){
		String sql="select b.* from h_house_type a,h_img b where a.ht_id=b.ht_id and a.ht_id=#{ht_id}";
		return sql;
	}
	
	/**
	 * 属性
	 * @return
	 */
	public String get_house_pro(){
		String sql =" select b.* from h_house_type a,h_property_value b,h_house_type_property c ";
		       sql+=" where a.ht_id=c.ht_id and c.pro_id=b.v_id and a.ht_id=#{ht_id} ";
		return sql;
	}
	
	
	/**
	 * 配套设施
	 * @return
	 */
	public String get_house_sup(){
		String sql =" select a.* from h_supporting a,h_house_type_supporting b,h_house_type c ";
		       sql+=" where a.sup_id=b.sup_id and b.ht_id=c.ht_id and c.ht_id=#{ht_id} ";
		return sql;
	}
	
	
	//随机房间
	public String get_house_info(){
		String sql =" SELECT  a.house_id,a.house_num,a.keyNum FROM h_house a ";
		       sql+="where a.house_id NOT IN (select distinct(house_id) FROM h_house_schedule where #{stime}>=starttime and #{etime}<=endtime and state in (-1,1))";
		       sql+=" AND a.house_state = 2 AND a.ht_id=#{ht_id} ";
		       sql+=" ORDER BY RAND() LIMIT 1 ";
		return sql;
	}
	
	//酒店信息
	public String get_hotel_info(){
		String sql="select a.ht_name,c.name name1,d.name name2,b.address,e.M_tel,b.starttime,b.cash_pledge from h_house_type a,m_hotel b,h_domains c,h_domains d,h_merchants e where a.hotel_id=b.id and a.ht_id=#{ht_id} and b.city=c.id and b.county=d.id  and b.merchants_id =e.merchants_id";
		return sql;
	}
	
	//订单记录
	public String insert_order_info(){
	
		String sql =" insert into h_order(House_id,openid,ammount_sum,createtime,order_num,tel,is_f_order,f_orderid,cash_pledge,starttime,endtime) values(#{house_id},#{openid},#{ammount_sum},#{createtime},#{order_num},#{tel},#{is_f_order},#{f_orderid},#{cash_pledge},#{starttime},#{endtime})";
		 
		return sql;
	}
	//订单记录
		public String insert_order_info2(){
		
			String sql =" insert into h_order(openid,ammount_sum,createtime,order_num,tel,is_f_order,cash_pledge) values(#{openid},#{ammount_sum},#{createtime},#{order_num},#{tel},#{is_f_order},#{cash_pledge})";
			 
			return sql;
		}
	
	//日程记录
	public String insert_house_schedule(){
		String sql =" insert into h_house_schedule(house_id,STARTTIME,ENDTIME,ORDERID)";
		       sql+=" values(#{house_id},#{stime},#{etime},#{order_id}) ";
		return sql;
	}
	
	
	/**
	 * 微信支付结果
	 * @return
	 */
	public String inser_wechat_result(){
		String sql =" insert into wechat_result(device_info,openid,is_subscribe,trade_type,trade_state,bank_type,total_fee,cash_fee,transaction_id,out_trade_no,time_end,trade_state_desc) ";
			   sql+=" values(#{device_info},#{openid},#{is_subscribe},#{trade_type},#{trade_state},#{bank_type},#{total_fee},#{cash_fee},#{},#{transaction_id},#{out_trade_no},#{time_end},#{trade_state_desc})";
		return sql;
	}
	
	
	/**
	 * 保存支付记录
	 * @return
	 */
	public String insert_result_log(){
		String sql =" insert into h_order_pay_result(order_id,trade_no,rtime,result,pay_money,openid) ";
			   sql+=" values(#{orderid},#{trade_no},#{pay_time},1,#{paymoney},#{name})";
		return sql;
	}
	
	
	/**
	 * 支付成功更新状态
	 * @return
	 */
	public String update_order_state(){
		String sql=" update h_order set state=2,h_pwd=#{h_pwd} where order_id=#{order_id}";
		return sql;
	}
	
	/**
	 * 添加房间身份证锁对应记录
	 * @return
	 */
	public String insert_house_user(Map<String,List<HouseUsers>> map){
		List<HouseUsers> hu=map.get("houseusers");
		String sql ="insert into m_house_users(HOUSEID,LOCKID,CARDID,STARTTIME,ENDTIME,order_id) values ";
		    for(int i=0;i<hu.size();i++){
		    	if(i==hu.size()-1){
		    		sql+=" ("+hu.get(i).getHouseid()+",'"+hu.get(i).getLockid()+"','"+hu.get(i).getCardid()+"','"+hu.get(i).getStarttime()+"','"+hu.get(i).getEndtime()+"',"+hu.get(i).getOrder_id()+") ";
		    	}else{
		    		sql+=" ("+hu.get(i).getHouseid()+",'"+hu.get(i).getLockid()+"','"+hu.get(i).getCardid()+"','"+hu.get(i).getStarttime()+"','"+hu.get(i).getEndtime()+"',"+hu.get(i).getOrder_id()+"),";
		    	}
		    	
		    }   
		
		
		return sql;
	}
	
	/**
	 * 订单日志
	 * @return
	 */
	public String insert_order_log(){
		String sql =" insert into h_order_log(order_id,from_order,to_order,operate,result,rtime) ";
		       sql+=" values(#{order_id},1,1,1,1,#{time}) ";
		return sql;
	}
	
	/**
	 * 我的订单
	 * @return
	 */
	public String get_order_info(Map<String, String> map){
		String openid=map.get("openid");
		String sql =" SELECT f.sch_id,b.order_id,b.order_num,d.ht_name,c.house_num,b.createtime,f.STARTTIME,f.ENDTIME,b.ammount_sum ,b.order_id,b.state,b.cash_pledge,b.openid";
		       sql+=" FROM  h_user a,h_order b,h_house c ,  ";
		       sql+=" h_house_type d,m_hotel e,h_house_schedule f ";
		       sql+=" where a.openid=b.openid ";
		       sql+=" and c.ht_id=d.ht_id and d.hotel_id=e.id and b.order_id=f.ORDERID and a.openid='"+openid+"'";
		       sql+=" and c.house_id=f.house_id and b.is_f_order=1 order by b.order_id desc";
		return sql;
	}
	
	/**
	 * 订单详细信息
	 * @return
	 */
	public String get_order_detail(){
		String sql =" SELECT b.state,b.order_id, b.order_num,e.name,d.ht_name,c.house_num,b.createtime,f.STARTTIME,f.ENDTIME,b.ammount_sum,g.M_tel,k.Name name1,m.Name name2 ,e.starttime time1,e.address,b.tel,b.cash_pledge,b.h_pwd";
		       sql+=" FROM h_user a,h_order b,h_house c , ";
		       sql+=" h_house_type d,m_hotel e,h_house_schedule f,h_merchants g,h_domains k,h_domains m ";
		       sql+=" where a.openid=b.openid   ";
		       sql+=" and c.ht_id=d.ht_id and d.hotel_id=e.id and b.order_id=f.ORDERID  ";
		       sql+=" and a.openid=#{openid} and e.merchants_id=g.merchants_id and e.city=k.id and e.county=m.id and b.order_id=#{order_id} and f.sch_id=#{sch_id} and f.house_id=c.house_id ";
		return sql;
	}
	public String get_order_detail2(){
		String sql =" SELECT b.state,b.order_id, b.order_num,e.name,d.ht_name,c.house_num,b.createtime,f.STARTTIME,f.ENDTIME,b.ammount_sum,g.M_tel,k.Name name1,m.Name name2 ,e.starttime time1,e.address,b.tel,f.sch_id,b.cash_pledge,b.h_pwd";
		       sql+=" FROM h_user a,h_order b,h_house c , ";
		       sql+=" h_house_type d,m_hotel e,h_house_schedule f,h_merchants g,h_domains k,h_domains m ";
		       sql+=" where a.openid=b.openid   ";
		       sql+=" and c.ht_id=d.ht_id and d.hotel_id=e.id and b.order_id=f.ORDERID  ";
		       sql+=" and a.openid=#{openid} and e.merchants_id=g.merchants_id and e.city=k.id and e.county=m.id and b.order_id=#{order_id}  and f.house_id=c.house_id ";
		return sql;
	}
	public String get_order_detail3(){
		String sql =" SELECT b.state,b.order_id, b.order_num,e.name,d.ht_name,c.house_num,b.createtime,f.STARTTIME,f.ENDTIME,b.ammount_sum,g.M_tel,k.Name name1,m.Name name2 ,e.starttime time1,e.address,b.tel,f.sch_id,b.cash_pledge,b.h_pwd";
		       sql+=" FROM h_user a,h_order b,h_house c , ";
		       sql+=" h_house_type d,m_hotel e,h_house_schedule f,h_merchants g,h_domains k,h_domains m ";
		       sql+=" where a.openid=b.openid   ";
		       sql+=" and c.ht_id=d.ht_id and d.hotel_id=e.id and b.order_id=f.ORDERID ";
		       sql+=" and a.openid=#{openid} and e.merchants_id=g.merchants_id and e.city=k.id and e.county=m.id and b.f_orderid=#{order_id}  and f.house_id=c.house_id ";
		return sql;
	}
	
	/**
	 * 身份证
	 * @return
	 */
	public String get_card_house(){
		String sql=" SELECT   b.STARTTIME, b.ENDTIME, b.CARDID FROM  m_house_users b, h_house_schedule c WHERE b.houseid=c.house_id AND c.sch_id = #{sch_id} and b.order_id=#{order_id}";
		return sql;
	}
	
	
	
	public String get_house_num(){
		String sql =" SELECT   a.ht_id, b.ht_name, COUNT(a.house_id) AS num ";
		       sql+=" FROM  h_house a, h_house_type b ";
		       sql+=" WHERE a.house_id NOT IN (SELECT  house_id FROM h_house_schedule where  #{etime} < endtime and #{stime} > starttime and state in(-1,1))";
		
		       sql+=" AND a.house_state = 2 AND a.ht_id =#{ht_id}   AND a.ht_id = b.ht_id ";
		       sql+=" GROUP BY a.ht_id DESC ";
		       
		return sql;
	}
	
	/**
	 * 房间收藏
	 * @return
	 */
	public String insert_collect(){
		String sql="insert into h_house_collect(ht_id,openid,createtime) values(#{ht_id},#{openid},#{time})";
		return sql;
	}
	
	
	/**
	 * 房间收藏
	 * @return
	 */
	public String del_collect(){
		String sql=" delete from h_house_collect where id=#{id}";
		return sql;
	}
	
	/**
	 * 收藏id
	 * @return
	 */
	public String get_coll_id(){
		String sql="select id from h_house_collect where ht_id=#{ht_id} and openid=#{openid}";
		return sql;
	}
	
	
	/**
	 * 我的收藏
	 */
	public String get_collect_info(){
		String sql =" SELECT d.ht_id, f.Name,d.ht_name,b.address,b.name name1,c.pricing,c.single_price,h.addprice  FROM h_house_collect a,m_hotel b,h_price c,h_domains f, h_house_type d left join h_holiday_price h on h.ht_id=d.ht_id ";
			   sql+=" where a.ht_id=d.ht_id and d.hotel_id=b.id and d.ht_id=c.ht_id and b.city=f.ID and a.openid=#{openid} ";
			   sql+="order by a.createtime desc limit #{beginNum},#{ps} ";
		return sql;
	}
	
	public String get_collect_count(){
		String sql =" SELECT  count(a.id) FROM h_house_collect a,m_hotel b,h_price c, h_house_type d,h_domains f ";
			   sql+=" where a.ht_id=d.ht_id and d.hotel_id=b.id and d.ht_id=c.ht_id and b.city=f.ID and a.openid=#{openid} ";
		return sql;
	}
	
	
	public String get_coll_img(Map<String, String> map){
		String ht_id=map.get("ht_id");
		String sql=" select *  from h_img where ht_id in("+ht_id+") group by ht_id";
		return sql;
	}
	
	public String get_coll_info(){
		String sql="select * from h_house_collect where ht_id=#{ht_id} and openid=#{openid}";
		return sql; 
	}
	
	
	
	
	public String queryOrderByid(){
		String sql="select a.*,b.ht_id from h_order a left join h_house b on a.House_id=b.house_Id where a.is_f_order=1 and order_id=#{orderid}";
		return sql;
		
	}
	
	public String queryhouseuserByhid(){
		
		String sql="select a.CARDID,b.house_num from m_house_users a left join h_house b on a.HOUSEID=B.house_Id where a.HOUSEID=#{houseid}";
		return sql;
	}
	
	public String update_sch_state(){
		
		String sql="update h_house_schedule set STATE=#{state} where ORDERID=#{orderid}";
		return sql;
	}
	
	public String update_hu_state(){
		String sql="update m_house_users set STATE=#{state} where order_id=#{orderid}";
		return sql;
		
	}
	public String update_hu_pwd(){
		
		String sql="update m_house_users set h_pwd=#{pwd} where order_id=#{orderid}";
		return sql;
	}
	
	public String update_order_allstate(){
		String sql="update h_order set state=#{state} where order_id=#{orderid}";
		return sql;
		
	}
	
	public String updateOrderCanclTime(){
		
		String sql="update h_order set cancel_time=now() where order_id=#{orderid}";
		return sql;
	}
}
