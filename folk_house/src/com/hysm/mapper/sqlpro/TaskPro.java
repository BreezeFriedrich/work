package com.hysm.mapper.sqlpro;

public class TaskPro {

	public String updateSchAndHU(){
		
		String sql="update h_order a,h_house_schedule b,m_house_users c set b.STATE=101,c.STATE=101 where b.ORDERID=a.order_id and c.order_id=a.order_id and a.createtime<DATE_SUB(NOW(),INTERVAL 30 MINUTE) and a.state=1 ";
		return sql;
	}
	
	public String updateOrder(){
		String sql="update h_order a  set a.state=5,a.cancel_time=NOW() where  a.createtime<DATE_SUB(NOW(),INTERVAL 30 MINUTE) and a.state=1 ";
		return sql;
	}
	/*public String queryOverOrder(){
		
		String sql="select * from h_order where createtime<DATE_SUB(NOW(),INTERVAL 30 MINUTE) and state=1";
		return sql;
		
	}*/
}
