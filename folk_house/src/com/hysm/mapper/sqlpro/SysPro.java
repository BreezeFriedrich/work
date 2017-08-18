package com.hysm.mapper.sqlpro;

import java.util.Map;

public class SysPro {

	/**
	 * 验证用户名是否存在
	 * @return
	 */
	public String regs_name(){
		String sql=" select name,manager_id from h_manager where name=#{name}";
		return sql;
	}
	
	/**
	 * 验证密码
	 * @return
	 */
	public String regs_pwd(){
		String sql=" select * from h_manager where pwd=#{pwd}";
		return sql;
	}
	
	/**
	 * 类目
	 * @return
	 */
	public String get_category_list(){
		String sql=" select * from h_category order by category_id desc limit #{beginNum},#{ps}";
		return sql;
	}
	
	/**
	 * 类目行数
	 * @return
	 */
	public String get_category_count(){
		String sql=" select count(category_id) from h_category";
		return sql;
	}
	
	/**
	 * 已有类目的名称
	 * @return
	 */
	public String get_categoey_name(){
		String sql=" select c_name from h_category";
		return sql;
	}
	
	/**
	 * 添加类目
	 * @return
	 */
	public String add_category(){
		String sql="insert into h_category(c_name) values(#{c_name})";
		return sql;
	}
	
	/**
	 * 删除类目
	 * @return
	 */
	public String del_category(){
		String sql="delete from h_category where category_id=#{id}";
		return sql;
	}
	
	/**
	 * 得到类目
	 * @return
	 */
	public String get_category(){
		String sql=" select * from h_category where category_id=#{id}";
		return sql;
	}
	
	/**
	 * 更新类目
	 * @return
	 */
	public String edit_category(){
		String sql="update h_category set c_name=#{c_name},state=#{state} where category_id=#{id}";
		return sql;
	}
	
	/**
	 * 标签
	 * @return
	 */
	public String get_tag_list(Map<String, String> map){
		String tag_id=map.get("search_id");
		String domains_id=map.get("city");
		System.out.println(tag_id);
		System.out.println(domains_id);
		String sql ="select a.*,b.name d_name from h_tag a left join h_domains b on a.domains_id=b.id where 1=1 ";
		if(tag_id!=null && !domains_id.equals("") && tag_id.equals("-99")){
			sql+=" and a.tag_id="+Integer.parseInt(tag_id);
		}
		if(domains_id!=null && !domains_id.equals("") && tag_id.equals("-99")){
			sql+=" and a.domains_id=#{city}";
		}
		
			//sql+="order by a.tag_id  limit #{beginNum},#{ps}";
		    sql+="order by a.tag_id";
		return sql;
	}
	
	/**
	 * 标签
	 * @return
	 */
	public String get_tag_count(){
		String sql="select count(tag_id) from h_tag";
		return sql;
	}


	public String get_tag_z(){
		String sql="select * from h_tag where parent_id=#{id}";
		return sql;
	}

	/**
	 * 添加标签
	 * @return
	 */
	public String add_tag(){
		String sql="insert into h_tag(tag_name,parent_id,domains_id) values(#{tag_name},#{parent_id},#{domains_id})";
		return sql;
	}

	
	public String get_tag(){
		String sql="select a.tag_id ftag_id,a.tag_name ftag_name,c.name d_name,b.* from h_tag a,h_tag b,h_domains c where b.tag_id=#{id} and b.parent_id=a.tag_id and b.domains_id=c.id";
		return sql;
	}

	public String get_tag1(){
		String sql="select * from h_tag where parent_id=0";
		return sql;
	}

	public String get_tag3(){
		String sql="select a.tag_id ftag_id,a.tag_name ftag_name,b.* from h_tag a,h_tag b where b.tag_id=#{id} and b.parent_id=a.tag_id ";
		return sql;
	}

	/**
	 * 更新标签
	 * @return
	 */
	public String edit_tag(){
		String sql=" update h_tag set tag_name=#{tag_name},domains_id=#{domains_id},state=#{state} where tag_id=#{id}";
		return sql;
	}
	public String del_tag(){
		String sql="delete from h_tag where tag_id=#{id}";
		return sql;
	}

	/**
	 * 配套设施
	 * @return
	 */
	public String get_supporting_list(){
		String sql="select * from h_supporting where sup_type=#{sup_type} order by sup_id desc limit #{beginNum},#{ps}";
		return sql;
	}
	
	public String get_supporting_count(){
		String sql="select count(sup_id) from h_supporting where sup_type=#{sup_type}";
		return sql;
	}
	
	
	/**
	 * 添加配套设施
	 * @return
	 */
	public String insert_supporting(){
		String sql="insert into h_supporting(sup_name,img_url,sup_type) values(#{sup_name},#{img_url},#{sup_type})";
		return sql;
	}
	
	
	/**
	 * 删除配套设施
	 * @return
	 */
	public String del_sup(){
		String sql="delete from h_supporting where sup_id=#{id}";
		return sql;
	}
	
	
	public String get_sup(){
		String sql="select * from h_supporting where sup_id=#{id}";
		return sql;
	}
	
	/**
	 * 更新配套设施
	 * @return
	 */
	public String update_sup(){
		String sql="update h_supporting set sup_name=#{sup_name},sup_type=#{sup_type},img_url=#{img_url},state=#{state} where sup_id=#{sup_id}";
		return sql;
	}
	
	
	
	public String get_sup_name(){
		String sql="select * from h_supporting where sup_type=#{type}";
		return sql;
	}
	
	/**
	 * 订单管理
	 * @param map
	 * @return
	 */
	public String get_order_list(Map<String, Object> map){
		String stime=(String) map.get("stime");
		String etime=(String) map.get("etime");
		String merchants_id=(String) map.get("merchants_id");
		int state=(Integer) map.get("state");
		int user_id=(Integer) map.get("user_id");
		String sql =" SELECT b.name,c.nickname,f.ht_name,d.house_num,e.CARDID, ";
		       sql+=" b.address,b.rule,b.safeguard,e.STARTTIME,e.ENDTIME,a.* ";
		       sql+=" FROM h_order a,m_hotel b,h_user c,h_house d,m_house_users e,h_house_type f ";
		       sql+=" where a.House_id=d.house_Id and d.ht_id=f.ht_id and f.hotel_id=b.id   ";
		       sql+=" and a.openid=c.openid and e.HOUSEID=a.House_id ";
		if(stime!=null && !stime.equals("")){
			   sql+=" and e.STARTTIME>='"+stime+"' ";
		}
		if(etime!=null && !etime.equals("")){
			   sql+=" and e.ENDTIME<='"+etime+"' ";
		}
		if(merchants_id!=null && !merchants_id.equals("") && !merchants_id.equals("-99")){
			   sql+=" and b.id="+Integer.parseInt(merchants_id);
		}
		if(state!=0){
			  sql+=" and a.state="+state;
		}
		if(user_id!=0){
			  sql+=" and a.openid="+user_id;
		}
		sql+=" order by a.order_id desc limit #{beginNum},#{ps} ";
		return sql;
	}
	
	public String get_order_count(Map<String, Object> map){
		String stime=(String) map.get("stime");
		String etime=(String) map.get("etime");
		String merchants_id=(String) map.get("merchants_id");
		int state=(Integer) map.get("state");
		int user_id=(Integer) map.get("user_id");
		String sql =" SELECT count(a.order_id) ";
	       sql+=" FROM h_order a,m_hotel b,h_user c,h_house d,m_house_users e,h_house_type f ";
	       sql+=" where a.House_id=d.house_Id and d.ht_id=f.ht_id and f.hotel_id=b.id   ";
	       sql+=" and a.openid=c.openid and e.HOUSEID=a.House_id  ";
	    if(stime!=null && !stime.equals("")){
			   sql+=" and e.STARTTIME>='"+stime+"' ";
		}
		if(etime!=null && !etime.equals("")){
			   sql+=" and e.ENDTIME<='"+etime+"' ";
		}
	    if(merchants_id!=null && !merchants_id.equals("") && !merchants_id.equals("-99")){
		   sql+=" and b.id="+Integer.parseInt(merchants_id);
	    }
	    if(state!=0){
		  sql+=" and a.state="+state;
	    }
	    if(user_id!=0){
		  sql+=" and a.openid="+user_id;
	    }
		return sql;
	}
	
	/**
	 * 所有商户
	 * @return
	 */
	public String get_merchants(){
		String sql="select Merchants_id,m_name from h_merchants";
		return sql;
	}
	
	/**
	 * 商户列表
	 * @return
	 */
	public String get_merchants_list(Map<String, Object> map){
		String stime=(String) map.get("stime");
		String etime=(String) map.get("etime");
		String m_state=(String) map.get("m_state");
		String sql =" select * from h_merchants where 1=1 ";
		if(stime!=null && !stime.equals("")){
			   sql+=" and M_ctime>='"+stime+"' ";
		}
		if(etime!=null && !etime.equals("")){
			   sql+=" and M_ctime<='"+etime+"' ";
		}
		if(m_state!=null && !m_state.equals("") && !m_state.equals("-99")){
			   sql+=" and m_state="+m_state;
		}
		sql+=" order by Merchants_id desc limit #{beginNum},#{ps} ";
		return sql;
	}
	
	public String get_merchants_count(Map<String, Object> map){
		String stime=(String) map.get("stime");
		String etime=(String) map.get("etime");
		String m_state=(String) map.get("m_state");
		String sql =" select count(Merchants_id) from h_merchants where 1=1 ";
		if(stime!=null && !stime.equals("")){
			   sql+=" and M_ctime>='"+stime+"' ";
		}
		if(etime!=null && !etime.equals("")){
			   sql+=" and M_ctime<='"+etime+"' ";
		}
		if(m_state!=null && !m_state.equals("") && !m_state.equals("-99")){
			   sql+=" and m_state="+Integer.parseInt(m_state);
		}
		return sql;
	}
	
	/**
	 * 修改商户状态
	 * @return
	 */
	public String edit_merchants(){
		String sql=" update h_merchants set m_state=#{m_state} where Merchants_id=#{id}";
		return sql;
	}
	
	/**
	 * 用户列表
	 * @param map
	 * @return
	 */
	public String get_user_list(Map<String, String > map){
		String stime=map.get("stime");
		String etime=map.get("etime");
		String sql=" select * from h_user where 1=1 ";
		if(stime!=null && !stime.equals("")){
			   sql+=" and createtime>='"+stime+"'";
		}
		if(etime!=null && !etime.equals("")){
			   sql+=" and createtime<='"+etime+"'";
		}
		sql+=" order by user_id desc limit #{beginNum},#{ps} ";
		return sql;
	}
	

	public String get_user_count(Map<String, String > map){
		String stime=map.get("stime");
		String etime=map.get("etime");
		String sql=" select count(user_id) from h_user where 1=1 ";
		if(stime!=null && !stime.equals("")){
			   sql+=" and createtime>='"+stime+"'";
		}
		if(etime!=null && !etime.equals("")){
			   sql+=" and createtime<='"+etime+"'";
		}
		return sql;
	}
	
	/**
	 * 添加锁类型
	 * @return
	 */
	public String add_lock(){
		String sql="insert into h_lock_type(lock_name,is_idcard,is_pwd) values(#{lock_name},#{is_idcard},#{is_pwd})";
		return sql;
	}
	
	/**
	 * 所列表
	 * @return
	 */
	public String get_lock_list(){
		String sql=" select * from h_lock_type order by lock_id desc limit #{beginNum},#{ps}  ";
		return sql;
	}
	
	
	public String get_lock_count(){
		String sql=" select count(lock_id) from h_lock_type  ";
		return sql;
	}
	
	public String del_lock(){
		String sql="delete  from h_lock_type where lock_id=#{id}";
		return sql;
	}
	
	
	public String get_lock(){
		String sql="select * from h_lock_type where lock_id=#{id}";
		return sql;
	}
	
	public String edit_lock(){
		String sql="update h_lock_type set lock_name=#{lock_name},is_idcard=#{is_idcard},is_pwd=#{is_pwd},state=#{state} where lock_id=#{id}";
		return sql;
	}
	
	public String get_hotel_list(){
		String sql=" SELECT a.id, a.name FROM folk_house.m_hotel a,h_merchants b where a.merchants_id=b.Merchants_id and b.M_state=1 ";
		return sql;
	}
	
	
	
	
	
	
	
}
