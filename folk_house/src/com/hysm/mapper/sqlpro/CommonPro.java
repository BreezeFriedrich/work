package com.hysm.mapper.sqlpro;

import java.util.List;

import com.hysm.domain.Property;

public class CommonPro {

	public String get_provice(){
		String sql="select id,name,parent from h_domains where parent=0";
		return sql;
	}
	
	
	
	
	public String get_city(){
		String sql="select id,name,parent from h_domains where parent=#{id}";
		return sql;
	}
	
	
	
	public String get_house_provice(){
		String sql=" select id,name,parent from h_domains where id in(select parent from h_domains where id in(SELECT distinct b.city FROM h_house_type a,m_hotel b where a.hotel_id=b.id) order by id) order by id ";
		return sql;
	}
	
	public String get_house_city(){
		String sql=" select id,name,parent from h_domains where id in(SELECT distinct b.city FROM h_house_type a,m_hotel b where a.hotel_id=b.id)  order by id ";
		return sql;
	}
	
	public String get_pro(){
		String sql="select pro_Id,pro_name,ptype from h_property";
		return sql;
	}
	
	public String get_pro_value(){
		String sql="select v_id,pro_id,name from h_property_value";
		return sql;
	}
}
