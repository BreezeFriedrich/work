package com.yishu.service.impl;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.springframework.stereotype.Service;
import com.yishu.model.json.*;
import com.yishu.model.json.LockOperateData.RecordInfo;
import com.yishu.model.json.LockOperateJson.LockOperateRecord;
import com.yishu.service.LockOperateService;
import com.yishu.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

@Service("lockOperateService")
public class LockOperateServiceImpl implements LockOperateService{

	@Override
	public String findLockOperateRecordByTime(String ownerPhoneNumber,String startTime, String endTime) {
		Gson gson=new Gson();
		String JsonData=null;
		JsonData=gson.toJson(LockOperateUtil.getLockOperate(ownerPhoneNumber, startTime, endTime));
		System.out.println(JsonData);
		return JsonData;
	}
	
	@Override
	public String findLockOperateRecordByNodes(String ownerPhoneNumber,String startTime, String endTime,String treeNodes){
		Gson gson=new Gson();
		List<ServerTreeNode> list = null;
		list=gson.fromJson(treeNodes,new TypeToken<List<ServerTreeNode>>() {}.getType());
		LockOperateJson lockOperateJson=new LockOperateJson();
		LockOperateServiceImpl lockOperateServiceImpl=new LockOperateServiceImpl();
		System.out.println("list.size:"+gson.fromJson(treeNodes,new TypeToken<List<ServerTreeNode>>() {}.getType()));
		for(int i=0;i<list.size();i++){
			LockOperateJson Json=lockOperateServiceImpl.findLockOperateRecordByNode(ownerPhoneNumber, startTime, endTime, list.get(i));
			lockOperateJson.totals=lockOperateJson.totals+Json.totals;
			lockOperateJson.rows.addAll(Json.rows);
		}
		String JsonData=null;
		ObjectMapper mapper=new ObjectMapper();
		JsonData=gson.toJson(lockOperateJson);
		return JsonData;
		/*未成功！
		ObjectMapper mapper=new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ServerTreeNode.class);
		List<ServerTreeNode> list = null;
		try {
			list = (List<ServerTreeNode>)mapper.readValue(treeNodes, javaType);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		LockOperateJson lockOperateJson=new LockOperateJson();
		LockOperateServiceImpl lockOperateServiceImpl=new LockOperateServiceImpl();
		for(int i=0;i<list.size();i++){
			LockOperateJson Json=lockOperateServiceImpl.findLockOperateRecordByNode(ownerPhoneNumber, startTime, endTime, list.get(i));
			lockOperateJson.totals=lockOperateJson.totals+Json.totals;
			lockOperateJson.rows.addAll(Json.rows);
		}
		String JsonData=null;
		try {
			JsonData=mapper.writeValueAsString(lockOperateJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return JsonData;*/
	}
	public LockOperateJson findLockOperateRecordByNode(String ownerPhoneNumber,String startTime, String endTime,ServerTreeNode treeNode) {
		String treeNodeId=treeNode.id;System.out.println("treeNodeId:"+treeNodeId);
		Gson gson=new Gson();
		String JsonData=null;
		LockOperateJson newlockOperateJson=new LockOperateJson();
		newlockOperateJson.rows=new ArrayList<LockOperateRecord>();
		LockOperateJson lockOperateJson=LockOperateUtil.getLockOperate(ownerPhoneNumber, startTime, endTime);
		if(lockOperateJson.rows!=null){
			List<LockOperateRecord> list=lockOperateJson.rows;
			for(int i=0;i<list.size();i++){
				LockOperateRecord lockOperateRecord=list.get(i);System.out.println("list.get(i).gatewayCode:"+list.get(i).gatewayCode);
				if(treeNodeId==lockOperateRecord.gatewayCode){
					newlockOperateJson.rows.add(list.get(i));
				}else if(treeNodeId.equals(list.get(i).lockCode)){
//					System.out.println(Integer.parseInt(treeNodeId)==Integer.parseInt(list.get(i).lockCode));
//					System.out.println("lockOperateRecord.lockCode:"+lockOperateRecord.lockCode);
//					System.out.println("treeNodeId:"+treeNodeId);
					newlockOperateJson.rows.add(list.get(i));
					System.out.println("!!!!!!!!!!!newlockOperateJson.rows.size:"+newlockOperateJson.rows.size());
				}
			}
			System.out.println("newlockOperateJson.rows.size:"+newlockOperateJson.rows.size());
			newlockOperateJson.totals=newlockOperateJson.rows.size();
		}
//		JsonData=gson.toJson(newlockOperateJson);
//		System.out.println("JsonData:"+JsonData);
//		return JsonData;
		return newlockOperateJson;
	}
	public String findLockOperateRecordByNode(String ownerPhoneNumber,String startTime, String endTime,String treeNode) {
		String treeNodeId=treeNode.substring(1,7);System.out.println("treeNodeId:"+treeNodeId);
		Gson gson=new Gson();
		String JsonData=null;
		LockOperateJson newlockOperateJson=new LockOperateJson();
		newlockOperateJson.rows=new ArrayList<LockOperateRecord>();
		LockOperateJson lockOperateJson=LockOperateUtil.getLockOperate(ownerPhoneNumber, startTime, endTime);
		if(lockOperateJson.rows!=null){
			List<LockOperateRecord> list=lockOperateJson.rows;
			for(int i=0;i<list.size();i++){
				LockOperateRecord lockOperateRecord=list.get(i);System.out.println("list.get(i).gatewayCode:"+list.get(i).gatewayCode);
				if(treeNodeId==lockOperateRecord.gatewayCode){
					newlockOperateJson.rows.add(list.get(i));
				}else if(treeNodeId.equals(list.get(i).lockCode)){
//					System.out.println(Integer.parseInt(treeNodeId)==Integer.parseInt(list.get(i).lockCode));
//					System.out.println("lockOperateRecord.lockCode:"+lockOperateRecord.lockCode);
//					System.out.println("treeNodeId:"+treeNodeId);
					newlockOperateJson.rows.add(list.get(i));
					System.out.println("!!!!!!!!!!!newlockOperateJson.rows.size:"+newlockOperateJson.rows.size());
				}
			}
			System.out.println("newlockOperateJson.rows.size:"+newlockOperateJson.rows.size());
			newlockOperateJson.totals=newlockOperateJson.rows.size();
		}
		JsonData=gson.toJson(newlockOperateJson);
		System.out.println("JsonData:"+JsonData);
		return JsonData;
//		return newlockOperateJson;
	}

	@Override
	public String findLockOperateRecordByID(String ownerPhoneNumber,String startTime, String endTime, String id) {
		Gson gson=new Gson();System.out.println("SerImpl-id:"+id);
		String JsonData=null;
		LockOperateJson newlockOperateJson=new LockOperateJson();
		newlockOperateJson.rows=new ArrayList<LockOperateRecord>();
		LockOperateJson lockOperateJson=LockOperateUtil.getLockOperate(ownerPhoneNumber, startTime, endTime);
		
		if(lockOperateJson.rows!=null){
			List<LockOperateRecord> list=lockOperateJson.rows;
			for(int i=0;i<list.size();i++){
				LockOperateRecord lockOperateRecord=list.get(i);				
				if(lockOperateRecord.cardNumb!=null&&lockOperateRecord.cardNumb.equals(id)){
					newlockOperateJson.rows.add(list.get(i));
					System.out.println("SerImpl-newlockOperateJson.rows.size:"+newlockOperateJson.rows.size());
				}
			}
			System.out.println("newlockOperateJson.rows.size:"+newlockOperateJson.rows.size());
			newlockOperateJson.totals=newlockOperateJson.rows.size();
		}
		JsonData=gson.toJson(newlockOperateJson);
		System.out.println("JsonData:"+JsonData);
		return JsonData;
	}
	
	@Override
	public String findLockOperateRecordByPwd(String ownerPhoneNumber,String startTime, String endTime, String pwd) {
		Gson gson=new Gson();System.out.println("SerImpl-id:"+pwd);
		String JsonData=null;
		LockOperateJson newlockOperateJson=new LockOperateJson();
		newlockOperateJson.rows=new ArrayList<LockOperateRecord>();
		LockOperateJson lockOperateJson=LockOperateUtil.getLockOperate(ownerPhoneNumber, startTime, endTime);
		
		if(lockOperateJson.rows!=null){
			List<LockOperateRecord> list=lockOperateJson.rows;
			for(int i=0;i<list.size();i++){
				LockOperateRecord lockOperateRecord=list.get(i);				
				if(lockOperateRecord.password!=null&&lockOperateRecord.password.equals(pwd)){
					newlockOperateJson.rows.add(list.get(i));
					System.out.println("SerImpl-newlockOperateJson.rows.size:"+newlockOperateJson.rows.size());
				}
			}
			System.out.println("newlockOperateJson.rows.size:"+newlockOperateJson.rows.size());
			newlockOperateJson.totals=newlockOperateJson.rows.size();
		}
		JsonData=gson.toJson(newlockOperateJson);
		System.out.println("JsonData:"+JsonData);
		return JsonData;
	}
	@Override
	public String getDeviceTree(String ownerPhoneNumber){
		Gson gson=new Gson();
		String JsonData=null;
		JsonData=gson.toJson(LockOperateUtil.getDeviceTree(ownerPhoneNumber));
		System.out.println("JsonData:"+JsonData);
		return JsonData;
	}
	
	/*
	public String findAllLockOperate() throws JsonSyntaxException, ClassNotFoundException{
	// 自己的数据库，Jackson封装.
		public String RecordLockOperate() throws JsonGenerationException, JsonMappingException, IOException, JsonSyntaxException, ClassNotFoundException{
		
			ObjectMapper objectMapper = new ObjectMapper();
	        Map map=new HashMap();
	        List<LockOperate> list=new ArrayList<LockOperate>();
	        list.addAll(lockOperateService.findByUserid("RuJia_Num00000001"));
	                
	        map.put("rows",list);
	        map.put("total",list.size());
	        List footerList=new ArrayList();
	        Map footMap=new HashMap();
	        footMap.put("lockcode", "总计");
	        footMap.put("timetag", "总数");
	        Map footMap2=new HashMap();
	        footMap2.put("lockcode", "总计");
	        footMap2.put("timetag",map.get("total"));
	        footerList.add(0, footMap);
	        footerList.add(1, footMap2);
	        map.put("footer",footerList);
	        System.out.println(map);
	        
	        Iterator it = list.iterator();
	        while(it.hasNext()){
	        	LockOperate str=(LockOperate) it.next();
	        System.out.println(str);
	        System.out.println(str.getOpenmode());
	        }
	        return objectMapper.writeValueAsString(map);
			
	//公司数据库，拼接Json字串
		
			String str=LockOperateUtil.getData();
			String strJson = null;
			boolean result=str.contains("\"result\":0");
			if(result){
				String str1=str.split("\\[")[1];
				String str2=str1.split("\\]")[0];
				String str3=str2.substring(0, str2.lastIndexOf("}"));System.out.println("str3"+str3);
				String[] strArr=null;
				
				strArr=str3.split("\\}\\,");
				String[] strArr2=new String[strArr.length];
				int i=0;
				for(String x:strArr){
					System.out.println(x);
					String t=x.replace("\"cardInfo\":{","");
					strArr2[i]=t.replace("\"passwordInfo\":{","");
					System.out.println(strArr2[i]);
					i++;
				}
				StringBuffer data=new StringBuffer("{\"totals\":"+strArr2.length+','+"\"rows\":[");
				for(int j=0;j<20;j++){
				for(String x:strArr2){				
					data.append(x+",");
				}
				}
				data.deleteCharAt(data.length()-1);
				data.append("]}");
				System.out.println("data:"+data);
				strJson=new String(data);
			}
			return strJson;
	}
	*/
	
}
