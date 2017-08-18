package com.hysm.mytask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hysm.mapper.TaskMapper;
import com.hysm.util.DataUtil;

@Component
public class OrderTask {

	@Autowired
	private TaskMapper taskMapper;

	@Scheduled(cron = "0 0/30 * * * ?")
	public void updateOrderInfo() {
		//gengxi订单创建时间超过半小时的未支付订单
//		taskMapper.updateSchAndHU();
		//修改订单
		taskMapper.updateOrder();
		System.out.println(DataUtil.fromDate24H() + "系统修改过期订单");
	}
}
