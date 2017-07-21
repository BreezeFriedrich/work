package com.yishu.service.impl;

import com.alibaba.fastjson.JSON;
import com.yishu.model.json.DeviceData;
import com.yishu.model.json.TreeNode;
import org.springframework.stereotype.Service;
import com.yishu.service.DeviceService;
import com.yishu.util.DeviceUtil;

import java.util.ArrayList;
import java.util.List;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService{
	@Override
	public String getDeviceInfo(String ownerPhoneNumber) {
		String JsonData=null;
		JsonData=DeviceUtil.getDeviceInfo(ownerPhoneNumber);
		return JsonData;
	}

	@Override
	public List<TreeNode> getDeviceTree(String ownerPhoneNumber) {
		String JsonData=null;
		JsonData=DeviceUtil.getDeviceInfo(ownerPhoneNumber);
		DeviceData deviceData=JSON.parseObject(JsonData, DeviceData.class);
		if(0!=deviceData.result)return null;
		List<DeviceData.DeviceInfo> deviceInfolist=deviceData.devices;
		List<TreeNode> treeNodeList=new ArrayList<>();
		TreeNode treeNode;
		int xIndex=0;
		int yIndex=deviceInfolist.size();
		for (DeviceData.DeviceInfo x:deviceInfolist){
			xIndex++;
			treeNode=new TreeNode();
			treeNode.setId(xIndex);
			treeNode.setpId(0);
			treeNode.setName(x.gatewayName);
			treeNode.setTitle(x.gatewayCode);
			treeNode.setParent(true);
			treeNode.setIcon("./zTree/css/zTreeStyle/img/diy/gateway.png");
//			treeNode.setIconSkin("gateway");
			treeNode.setOpen(true);
			treeNode.setHidden(false);
			treeNode.setNocheck(false);//设置节点是否隐藏 checkbox / radio
			treeNodeList.add(treeNode);

			for(DeviceData.DeviceInfo.LockInfo y:x.lockLists){
				yIndex++;
				treeNode=new TreeNode();
				treeNode.setId(yIndex);
				treeNode.setpId(xIndex);
				treeNode.setName(y.lockName);
				treeNode.setTitle(y.lockCode);
				treeNode.setParent(false);
				treeNode.setIcon("./zTree/css/zTreeStyle/img/diy/doorlock.png");
//				treeNode.setIconSkin("lock");
				treeNode.setOpen(false);
				treeNode.setHidden(false);
				treeNode.setNocheck(false);
				treeNodeList.add(treeNode);
			}
		}
		return treeNodeList;
	}

	@Override
	public String getUnlockAccountInfo(String ownerPhoneNumber, String gatewayCode,String lockCode) {
		String JsonData=null;
		JsonData=DeviceUtil.getUnlockAccountInfo(ownerPhoneNumber,gatewayCode,lockCode);
		System.out.println("JsonData:"+JsonData);
		return JsonData;
	}
	
	@Override
	public String getCertiAuthInfo(String ownerPhoneNumber, String gatewayCode,String lockCode) {
		String JsonData=null;
		JsonData=DeviceUtil.getCertiAuthInfo(ownerPhoneNumber,gatewayCode,lockCode);
		System.out.println("JsonData:"+JsonData);
		return JsonData;
	}

	@Override
	public String getPwdAuthInfo(String ownerPhoneNumber, String gatewayCode,String lockCode) {
		String JsonData=null;
		JsonData=DeviceUtil.getPwdAuthInfo(ownerPhoneNumber,gatewayCode,lockCode);
		System.out.println("JsonData:"+JsonData);
		return JsonData;
	}

	@Override
	public int doCertiAuth(String ownerPhoneNumber, String gatewayCode,String lockCode, String name, String cardNumb, String startTime,String endTime) {
		int result=1;
		String data=DeviceUtil.doCertiAuth(ownerPhoneNumber, gatewayCode, lockCode, name, cardNumb, startTime, endTime);
//		result=Integer.parseInt(data.substring(data.indexOf(':')+1, data.indexOf('}')));
//		System.out.println("doCertiAuthResult:"+result);
		System.out.println("doCertiAuthData:"+data);
		return 0;
	}
	
	@Override
	public int doPwdAuth(String ownerPhoneNumber, String gatewayCode,String lockCode, String password, String startTime,String endTime) {
		int result=1;
		String data=DeviceUtil.doPwdAuth(ownerPhoneNumber, gatewayCode, lockCode,password, startTime, endTime);
		result=Integer.parseInt(data.substring(data.indexOf(':')+1, data.indexOf('}')));
		System.out.println("result:"+result);
		return result;
	}

	@Override
	public int doCertiCancelAuth(String ownerPhoneNumber, String lockCode,
			String serviceNumb, String cardNumb) {
		int result=1;
		String data=DeviceUtil.doCertiCancelAuth(ownerPhoneNumber, lockCode, serviceNumb, cardNumb);
		result=Integer.parseInt(data.substring(data.indexOf(':')+1, data.indexOf('}')));
		System.out.println("result:"+result);
		return result;
	}

	@Override
	public int doPwdCancelAuth(String ownerPhoneNumber, String lockCode,
			String gatewayCode, String serviceNumb) {
		int result=1;
		String data=DeviceUtil.doPwdCancelAuth(ownerPhoneNumber, lockCode, gatewayCode, serviceNumb);
		result=Integer.parseInt(data.substring(data.indexOf(':')+1, data.indexOf('}')));
		System.out.println("result:"+result);
		return result;
	}
}
