/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yishu.service.RoomService;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RoomAction extends ActionSupport {
	public RoomAction() {
		LOG.info(">>>Initialization RoomAction......................................");
	}
	private Logger LOG = LoggerFactory.getLogger("RoomAction");

	@Autowired
	private RoomService roomService;

	private Object jsonResult;
	private String ownerPhoneNumber;
	private String startTime;
	private String endTime;
	private String gatewayCode;
	private String lockCode;
	private String cardNum;
	private String roomType;
	private String roomId;

	private String roomTypeId;
	private String cardInfoList;

	private String password;
	private String roomName;

	private String newRoomType;

	private String orderNumber;
	private String newLockCode;

	private String newRoomName;

	public String getCardInfoList() {
		return cardInfoList;
	}

	public void setCardInfoList(String cardInfoList) {
		this.cardInfoList = cardInfoList;
	}

	public Object getJsonResult() {
		return jsonResult;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getNewRoomType() {
		return newRoomType;
	}

	public void setNewRoomType(String newRoomType) {
		this.newRoomType = newRoomType;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getNewLockCode() {
		return newLockCode;
	}

	public void setNewLockCode(String newLockCode) {
		this.newLockCode = newLockCode;
	}

	public String getNewRoomName() {
		return newRoomName;
	}

	public void setNewRoomName(String newRoomName) {
		this.newRoomName = newRoomName;
	}

	public String getOwnerPhoneNumber() {
		return ownerPhoneNumber;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getGatewayCode() {
		return gatewayCode;
	}

	public void setGatewayCode(String gatewayCode) {
		this.gatewayCode = gatewayCode;
	}

	public String getLockCode() {
		return lockCode;
	}

	public void setLockCode(String lockCode) {
		this.lockCode = lockCode;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public void setOwnerPhoneNumber(String ownerPhoneNumber) {
		this.ownerPhoneNumber = ownerPhoneNumber;
	}

	public void setJsonResult(Object jsonResult) {
		this.jsonResult = jsonResult;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();

	public String getDeviceInfo(){
		checkPhoneNumber();
		String result=roomService.getDeviceInfo(ownerPhoneNumber);
		jsonResult=result;
		return  "json";
	}

	public String addRoomType(){
		checkPhoneNumber();
		String result=roomService.addRoomType(ownerPhoneNumber,roomType);
		jsonResult=result;
		return  "json";
	}

	public String addRoom(){
		checkPhoneNumber();
		String result=roomService.addRoom(ownerPhoneNumber,roomTypeId,roomName,lockCode,gatewayCode);
		jsonResult=result;
		return  "json";
	}

	public String getOrderList(){
		checkPhoneNumber();
		String result=roomService.getOrderList(ownerPhoneNumber,startTime,endTime);
		jsonResult=result;
		return  "json";
	}

	public String getRoomList(){
		checkPhoneNumber();
		String result=roomService.getRoomList(ownerPhoneNumber);
		jsonResult=result;
		return  "json";
	}

	public String getIDAuth(){
		checkPhoneNumber();
		String result=roomService.getIDAuth(ownerPhoneNumber,gatewayCode,lockCode);
		jsonResult=result;
		return  "json";
	}

	public String getPwdAuth(){
		checkPhoneNumber();
		String result=roomService.getPwdAuth(ownerPhoneNumber,gatewayCode,lockCode);
		jsonResult=result;
		return  "json";
	}

	public String delRoom(){
		checkPhoneNumber();
		String result=roomService.delRoom(ownerPhoneNumber,roomId);
		jsonResult=result;
		return  "json";
	}

	public String resetRoom(){
		checkPhoneNumber();
		String result=roomService.resetRoom(ownerPhoneNumber,roomTypeId,roomId,newLockCode,newRoomName);
		jsonResult=result;
		return  "json";
	}

	public String getFreeLock(){
		checkPhoneNumber();
		String result=roomService.getFreeLock(ownerPhoneNumber);
		jsonResult=result;
		return  "json";
	}

	public String addOrder(){
		checkPhoneNumber();
//		System.out.println("RoomAction cardinfolist.size:  "+cardInfoList);
		String result=roomService.addOrder(ownerPhoneNumber,startTime,endTime,roomTypeId,roomId,cardInfoList,password);
//		String result="";
		jsonResult=result;
		return  "json";
	}

	public String getUnlockRecord(){
		checkPhoneNumber();
		String result=roomService.getUnlockRecord(ownerPhoneNumber,startTime,endTime);
		jsonResult=result;
		return  "json";
	}

	public String delOrder(){
		checkPhoneNumber();
		String result=roomService.delOrder(ownerPhoneNumber,orderNumber);
		jsonResult=result;
		return "json";
	}

	public String updateOrder(){
		checkPhoneNumber();
		String result=roomService.updateOrder(ownerPhoneNumber,orderNumber,startTime,endTime,roomId,cardInfoList,password);
		jsonResult=result;
		return "json";
	}

	public String getOrderContent(){
		checkPhoneNumber();
		String result=roomService.getOrderContent(ownerPhoneNumber,orderNumber);
		jsonResult=result;
		return "json";
	}
	public String delRoomType(){
		checkPhoneNumber();
		String result=roomService.delRoomType(ownerPhoneNumber,roomTypeId);
		jsonResult=result;
		return "json";
	}

	public String setToSession(){
		LOG.info("setToSession>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String name=request.getParameter("sessionName");
		Object value=request.getParameter("sessionValue");
		System.out.println("sessionName:  "+name);
		System.out.println("sessionValue:  "+value);
		session.setAttribute(name,value);
		jsonResult=session.getAttribute(request.getParameter("sessionName"));
		return "json";
	}

	public String getFromSession(){
		LOG.info("getFromSession>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		jsonResult=session.getAttribute(request.getParameter("sessionName"));
		System.out.println("sessionName:  "+request.getParameter("sessionName"));
		System.out.println("sessionValue:  "+jsonResult);
		return "json";
	}

	private void checkPhoneNumber(){
		if ("".equals(ownerPhoneNumber)|| null==ownerPhoneNumber){
			ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
		}
	}
}
