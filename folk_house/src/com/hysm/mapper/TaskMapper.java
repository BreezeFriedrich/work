package com.hysm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.hysm.domain.Order;
import com.hysm.mapper.sqlpro.TaskPro;

@Repository
public interface TaskMapper {

	/**
	 * 查找订单时间超过半小时的未支付订单
	 * @return
	 */
	
	@UpdateProvider(method="updateSchAndHU",type=TaskPro.class)
	void updateSchAndHU();
	@UpdateProvider(method="updateOrder",type=TaskPro.class)
	void updateOrder();

}
