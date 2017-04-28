package com.yishu.service;

import com.yishu.model.json.LockOperateJson;
import com.yishu.model.json.ServerTreeNode;

public interface LockOperateService {
	public String findLockOperateRecordByTime(String ownerPhoneNumber, String startTime, String endTime);
	public String findLockOperateRecordByNodes(String ownerPhoneNumber, String startTime, String endTime, String treeNodes);
	public String findLockOperateRecordByNode(String ownerPhoneNumber, String startTime, String endTime, String treeNode);
	public LockOperateJson findLockOperateRecordByNode(String ownerPhoneNumber, String startTime, String endTime, ServerTreeNode treeNode);
	public String findLockOperateRecordByID(String ownerPhoneNumber, String startTime, String endTime, String id);
	public String findLockOperateRecordByPwd(String ownerPhoneNumber, String startTime, String endTime, String pwd);
	public String getDeviceTree(String ownerPhoneNumber);
}
