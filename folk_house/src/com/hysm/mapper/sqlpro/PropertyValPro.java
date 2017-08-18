package com.hysm.mapper.sqlpro;

public class PropertyValPro {
	
	
	public String add_ProVal(){
		String sql="insert into h_property_value(pro_id,name) values(#{pro_id},#{name}) ";
		return sql;
	}
	
	
	public String get_propertyVal_list(){
		String sql=" select h1.*,h2.pro_name from h_property_value h1,h_property h2  where h1.pro_id=h2.pro_id order by h1.V_Id desc limit #{beginNum},#{ps}";
		return sql;
	}
	
	
	public String get_propertyVal_count(){
		String sql="select count(v_Id) from h_property_value";
		return sql;
	}
	
	
	public String edit_propertyVal(){
		String sql="update h_property_value set pro_id=#{pro_id},name=#{name} where v_id=#{v_id}";
		return sql;
	}
	
	
	public String del_propertyVal(){
		String sql="delete from h_property_value where v_id=#{v_id}";
		return sql;
	}
	
	
	public String get_propertyVal(){
		String sql=" select * from h_property_value where v_id=#{v_id}";
		return sql;
	}
	
	public String get_propertyVal_name(){
		String sql="select name from h_property_value ";
		return sql;
	}
	
	
}
