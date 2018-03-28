/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.RoomTypeContainRoom;
import com.yishu.service.IRoomService;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("roomService")
public class RoomServiceImpl implements IRoomService {
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("RoomServiceImpl");

	/**
	 * 获取设备信息
	 * @param ownerPhoneNumber
	 * @return
	 */
	@Override
	public String getDeviceInfo(String ownerPhoneNumber) {
		int sign=16;
		LOG.info("sign:"+sign+" operation:getDeviceInfo");
		String data="{\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
		String result;
		result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String getUnlockRecord(String ownerPhoneNumber, String startTime, String endTime) {
		int sign=26;
		LOG.info("sign:"+sign+" operation:getUnlockRecord");
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String getIDAuth(String ownerPhoneNumber, String gatewayCode, String lockCode) {
		int sign=17;
		LOG.info("sign:"+sign+" operation:getIDAuth");
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String getPwdAuth(String ownerPhoneNumber, String gatewayCode, String lockCode) {
		int sign=20;
		LOG.info("sign:"+sign+" operation:getPwdAuth");
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String delIDAuth(String ownerPhoneNumber, String cardNumb, String serviceNumb, String lockCode) {
		int sign=19;
		LOG.info("sign:"+sign+" operation:delIDAuth");
		String timetag=getTimetag(new Date());
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"cardNumb\":\""+cardNumb+"\",\"timetag\":\""+timetag+"\"}";
		LOG.info("delIDAuthData:  "+data);
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String delPwdAuth(String ownerPhoneNumber, String gatewayCode, String serviceNumb, String lockCode) {
		int sign=22;
		LOG.info("sign:"+sign+" operation:delPwdAuth");
		String timetag=getTimetag(new Date());
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"gatewayCode\":\""+gatewayCode+"\",\"timetag\":\""+timetag+"\"}";
		LOG.info("delPwdAuthData:  "+data);
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String doIDAuth(String ownerPhoneNumber, String gatewayCode, String lockCode, String name, String cardNumb, String startTime, String endTime) {
		int sign=18;
		LOG.info("sign:"+sign+" operation:doIDAuth");
		String timetag=getTimetag(new Date());
		String serviceNumb=getServiceNumb(ownerPhoneNumber,timetag);
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"name\":\""+name+"\",\"cardNumb\":\""+cardNumb+"\",\"dnCode\":\"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"timetag\":\""+timetag+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String doPwdAuth(String ownerPhoneNumber, String gatewayCode, String lockCode, String password, String startTime, String endTime) {
		int sign=21;
		LOG.info("sign:"+sign+" operation:doPwdAuth");
		String timetag=getTimetag(new Date());
		String serviceNumb=getServiceNumb(ownerPhoneNumber,timetag);
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"password\":\""+password+"\",\"dnCode\":\"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"timetag\":\""+timetag+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String getOrderList(String ownerPhoneNumber, String startTime, String endTime) {
		int sign=2002;
		LOG.info("sign:"+sign+" operation:getOrderList");
		String timetag=getTimetag(new Date());
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"timetag\":\""+timetag+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String getRoomList(String ownerPhoneNumber) {
		int sign=2005;
		LOG.info("sign:"+sign+" operation:getRoomList");
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public List<RoomTypeContainRoom> getRoom(String ownerPhoneNumber) {
		int sign=2005;
		String data="{\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
		LOG.info("data : "+data);
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("result : "+result);

		ObjectMapper objectMapper=new ObjectMapper();
		JsonNode rootNode= null;
		List<RoomTypeContainRoom> roomTypeCRList = null;
		try {
			rootNode = objectMapper.readTree(result);
			int respSign=rootNode.path("result").asInt();
			if (0!=respSign){//请求数据失败
				return null;
			}
			JsonNode roomListNode=rootNode.path("roomList");
			roomTypeCRList =objectMapper.readValue(roomListNode.traverse(), new TypeReference<List<RoomTypeContainRoom>>(){});
		} catch (IOException |NullPointerException e) {
			e.printStackTrace();
		}
		return roomTypeCRList;
	}

	@Override
	public String addRoomType(String ownerPhoneNumber, String roomType) {
		int sign=2006;
		LOG.info("sign:"+sign+" operation:addRoomType");
		String timetag=getTimetag(new Date());
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"roomType\":\""+roomType+"\",\"timetag\":\""+timetag+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}


	@Override
	public String getFreeLock(String ownerPhoneNumber) {
		int sign=2012;
		LOG.info("sign:"+sign+" operation:getFreeLock");
		String data="{\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
		String result;
		result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}


	@Override
	public String delRoom(String ownerPhoneNumber, String roomId) {
		int sign=2011;
		LOG.info("sign:"+sign+" operation:delRoom");
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"roomId\":\""+roomId+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}


	@Override
	public String resetRoom(String ownerPhoneNumber, String roomTypeId, String roomId, String newLockCode, String newRoomName) {
		int sign=2010;
		LOG.info("sign:"+sign+" operation:resetRoom");
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"roomTypeId\":\""+roomTypeId+"\",\"roomId\":\""+roomId+"\",\"newLockCode\":\""+newLockCode+"\",\"newRoomName\":\""+newRoomName+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String addRoom(String ownerPhoneNumber, String roomTypeId, String roomName, String lockCode, String gatewayCode) {
		int sign=2009;
		LOG.info("sign:"+sign+" operation:addRoom");
		String timetag=getTimetag(new Date());
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"roomTypeId\":\""+roomTypeId+"\",\"roomName\":\""+roomName+"\",\"lockCode\":\""+lockCode+"\",\"gatewayCode\":\""+gatewayCode+"\",\"timetag\":\""+timetag+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String addOrder(String ownerPhoneNumber, String startTime, String endTime, String roomTypeId, String roomId, String cardInfoList, String password) {
//		String cardInfoListJson= JSON.toJSONString(cardInfoList);
		int sign=2001;
		LOG.info("sign:"+sign+" operation:addOrder");
		String timetag=getTimetag(new Date());
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"roomTypeId\":\""+roomTypeId+"\",\"roomId\":\""+roomId+"\",\"password\":\""+password+"\",\"cardInfoList\":"+cardInfoList+",\"timetag\":\""+timetag+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String delOrder(String ownerPhoneNumber, String orderNumber) {
		int sign=2004;
		LOG.info("sign:"+sign+" operation:delOrder");
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"orderNumber\":\""+orderNumber+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String updateOrder(String ownerPhoneNumber, String orderNumber, String startTime, String endTime, String roomId, String cardInfoList, String password) {
		int sign=2003;
		LOG.info("sign:"+sign+" operation:updateOrder");
		String timetag=getTimetag(new Date());

		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"orderNumber\":\""+orderNumber+"\",\"roomId\":\""+roomId+"\",\"password\":\""+password+"\",\"cardInfoList\":"+cardInfoList+",\"timetag\":\""+timetag+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String getOrderContent(String ownerPhoneNumber, String orderNumber) {
		int sign=2013;
		LOG.info("sign:"+sign+" operation:getOrderContent");
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"orderNumber\":\""+orderNumber+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}

	@Override
	public String delRoomType(String ownerPhoneNumber, String roomTypeId) {
		int sign=2008;
		LOG.info("sign:"+sign+" operation:getOrderContent");
		String data=" {\"sign\":"+sign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"roomTypeId\":\""+roomTypeId+"\"}";
		String result= HttpUtil.httpsPostToGateway(data);
		LOG.info("data: "+data);
		LOG.info("result: "+result);
		return result;
	}


	/**
	 * 生成ServiceNumb
	 * @param ownerPhoneNumber
	 * @param timetag
	 * @return
	 */
	private String getServiceNumb(String ownerPhoneNumber, String timetag) {
		long num=(long) Math.floor(Math.random()*10000000);
		String serviceNumb=timetag+ownerPhoneNumber+String.valueOf(num);
		return serviceNumb;
	}


	/**
	 * 把Date转换成timetag
	 * @param date
	 * @return
	 */
	private String getTimetag(Date date) {
		String timetag;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		timetag=sdf.format(date);
		return timetag;
	}




}
