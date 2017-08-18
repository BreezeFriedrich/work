package com.hysm.mapper.sqlpro;

public class PropertyPro {
	
	/**
	 * �������
	 */
	public String add_Pro(){
		String sql="insert into h_property(pro_name,selectType) values(#{pro_name},#{ptype}) ";
		return sql;
	}
	
	/**
	 * ��ҳ��ʾ����
	 */
	public String get_property_list(){
		String sql=" select * from h_property order by pro_Id desc limit #{beginNum},#{ps}";
		return sql;
	}
	
	/**
	 * ���Ը���
	 */
	public String get_property_count(){
		String sql="select count(pro_Id) from h_property";
		return sql;
	}
	
	/**
	 * ��������
	 * @return
	 */
	public String edit_property(){
		String sql="update h_property set pro_name=#{pro_name},state=#{state} where pro_id=#{pro_id}";
		return sql;
	}
	
	/**
	 * ɾ������
	 * @return
	 */
	public String del_property(){
		String sql="delete from h_property where pro_id=#{pro_id}";
		return sql;
	}
	
	/**
	 * �õ�����
	 * @return
	 */
	public String get_property(){
		String sql=" select * from h_property where pro_id=#{pro_id}";
		return sql;
	}
	
	public String get_property_name(){
		String sql="select pro_name,pro_id from h_property";
		return sql;
	}
	
}
