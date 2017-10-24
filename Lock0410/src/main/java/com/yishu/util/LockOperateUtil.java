package com.yishu.util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.yishu.model.json.*;
import com.yishu.model.json.LockOperateData.RecordInfo;
import com.yishu.model.json.LockOperateJson.LockOperateRecord;
import com.yishu.model.json.DeviceData.DeviceInfo;
import com.yishu.model.json.DeviceData.DeviceInfo.LockInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.yishu.model.json.DeviceData;
import com.yishu.model.json.LockOperateJson;
import com.yishu.model.json.ServerTreeNode;

public class LockOperateUtil {

	//用于findAll.do的json的String操作
	public static String getLockOperate(){

		int sign=26;
		String ownerPhoneNumber="18255683932";
		String startTime="201001010000";
		String endTime="201801010000";
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}";
		String result=HttpUtil.postData(data);
		return result;
		/*
		return "{\"totals\":\"48\",\"rows\":[{\"lockCode\":\"001007\",\"gatewayCode\": \"888888\",\"openMode\":1,\"timetag\":\"20170210122030\",\"name\":\"陈新浪\",\"cardNumb\":\"3209999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153534182556839323061898\"},{\"lockCode\":\"001001\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170120152030\",\"name\":\"启旭\",\"cardNumb\":\"39999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153554182556839328756212\"},{\"lockCode\":\"002005\",\"gatewayCode\":\"777777\",\"openMode\":2,\"timetag\":\"20170216152030\",\"serviceNumb\":\"20170210171824182556839326297963\",\"password\":\"455885\"},{\"lockCode\":\"002003\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170308122030\",\"name\":\"巴啦啦小魔仙\",\"cardNumb\":\"546254756425476845\",\"dnCode\":\"\",\"serviceNumb\":\"20170308153534182556839323061898\"},{\"lockCode\":\"002010\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170408152030\",\"name\":\"芝麻开门\",\"cardNumb\":\"365412546875445624\",\"dnCode\":\"\",\"serviceNumb\":\"20170408153554182556839328756212\"},{\"lockCode\":\"002006\",\"gatewayCode\":\"777777\",\"openMode\":2,\"timetag\":\"20170406152030\",\"serviceNumb\":\"20170406171824182556839326297963\",\"password\":\"651454\"},{\"lockCode\":\"001002\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20150706122030\",\"name\":\"青弋江\",\"cardNumb\":\"236512199611314213\",\"dnCode\":\"\",\"serviceNumb\":\"20150706153534182556839323061898\"},{\"lockCode\":\"001005\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20140605152030\",\"name\":\"大椿\",\"cardNumb\":\"346524200106120420\",\"dnCode\": \"\",\"serviceNumb\":\"20140605153554182556839328756212\"},{\"lockCode\":\"001006\",\"gatewayCode\":\"888888\",\"openMode\": 2,\"timetag\":\"20130902152030\",\"serviceNumb\":\"20130902171824182556839326297963\",\"password\":\"354167\"},{\"lockCode\":\"002004\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170609122030\",\"name\":\"唛哩唛哩轰\",	\"cardNumb\":\"246551321465224561\",\"dnCode\":\"\",\"serviceNumb\":\"20170609153534182556839323061898\"},{\"lockCode\":\"002008\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170806152030\",\"name\":\"青枫浦\",\"cardNumb\":\"82645135486247\",\"dnCode\":\"\",\"serviceNumb\":\"20170806153554182556839328756212\"},{\"lockCode\":\"002009\",\"gatewayCode\":\"777777\",\"openMode\":2,\"timetag\":\"20160709152030\",\"serviceNumb\":\"20160709171824182556839326297963\",\"password\":\"176542\"},{\"lockCode\":\"001003\",\"gatewayCode\": \"888888\",\"openMode\":1,\"timetag\":\"20170210122030\",\"name\":\"陈新浪\",\"cardNumb\":\"3209999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153534182556839323061898\"},{\"lockCode\":\"002001\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170120152030\",\"name\":\"启旭\",\"cardNumb\":\"39999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153554182556839328756212\"},{\"lockCode\":\"002007\",\"gatewayCode\":\"777777\",\"openMode\":2,\"timetag\":\"20170216152030\",\"serviceNumb\":\"20170210171824182556839326297963\",\"password\":\"455885\"},{\"lockCode\":\"001004\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170308122030\",\"name\":\"巴啦啦小魔仙\",\"cardNumb\":\"546254756425476845\",\"dnCode\":\"\",\"serviceNumb\":\"20170308153534182556839323061898\"},{\"lockCode\":\"001003\",\"gatewayCode\":\"444444\",\"openMode\":1,\"timetag\":\"20170408152030\",\"name\":\"芝麻开门\",\"cardNumb\":\"365412546875445624\",\"dnCode\":\"\",\"serviceNumb\":\"20170408153554182556839328756212\"},{\"lockCode\":\"001004\",\"gatewayCode\":\"888888\",\"openMode\":2,\"timetag\":\"20170406152030\",\"serviceNumb\":\"20170406171824182556839326297963\",\"password\":\"651454\"},{\"lockCode\":\"002008\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20150706122030\",\"name\":\"青弋江\",\"cardNumb\":\"236512199611314213\",\"dnCode\":\"\",\"serviceNumb\":\"20150706153534182556839323061898\"},{\"lockCode\":\"002005\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20140605152030\",\"name\":\"大椿\",\"cardNumb\":\"346524200106120420\",\"dnCode\": \"\",\"serviceNumb\":\"20140605153554182556839328756212\"},{\"lockCode\":\"002006\",\"gatewayCode\":\"888888\",\"openMode\": 2,\"timetag\":\"20130902152030\",\"serviceNumb\":\"20130902171824182556839326297963\",\"password\":\"354167\"},{\"lockCode\":\"001001\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170609122030\",\"name\":\"唛哩唛哩轰\",	\"cardNumb\":\"246551321465224561\",\"dnCode\":\"\",\"serviceNumb\":\"20170609153534182556839323061898\"},{\"lockCode\":\"001002\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170806152030\",\"name\":\"青枫浦\",\"cardNumb\":\"82645135486247\",\"dnCode\":\"\",\"serviceNumb\":\"20170806153554182556839328756212\"},{\"lockCode\":\"001006\",\"gatewayCode\":\"888888\",\"openMode\":2,\"timetag\":\"20160709152030\",\"serviceNumb\":\"20160709171824182556839326297963\",\"password\":\"176542\"},{\"lockCode\":\"002007\",\"gatewayCode\": \"777777\",\"openMode\":1,\"timetag\":\"20170210122030\",\"name\":\"陈新浪\",\"cardNumb\":\"3209999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153534182556839323061898\"},{\"lockCode\":\"001004\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170120152030\",\"name\":\"启旭\",\"cardNumb\":\"39999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153554182556839328756212\"},{\"lockCode\":\"002010\",\"gatewayCode\":\"777777\",\"openMode\":2,\"timetag\":\"20170216152030\",\"serviceNumb\":\"20170210171824182556839326297963\",\"password\":\"455885\"},{\"lockCode\":\"001002\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170308122030\",\"name\":\"巴啦啦小魔仙\",\"cardNumb\":\"546254756425476845\",\"dnCode\":\"\",\"serviceNumb\":\"20170308153534182556839323061898\"},{\"lockCode\":\"002006\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170408152030\",\"name\":\"芝麻开门\",\"cardNumb\":\"365412546875445624\",\"dnCode\":\"\",\"serviceNumb\":\"20170408153554182556839328756212\"},{\"lockCode\":\"001006\",\"gatewayCode\":\"888888\",\"openMode\":2,\"timetag\":\"20170406152030\",\"serviceNumb\":\"20170406171824182556839326297963\",\"password\":\"651454\"},{\"lockCode\":\"002004\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20150706122030\",\"name\":\"青弋江\",\"cardNumb\":\"236512199611314213\",\"dnCode\":\"\",\"serviceNumb\":\"20150706153534182556839323061898\"},{\"lockCode\":\"002010\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20140605152030\",\"name\":\"大椿\",\"cardNumb\":\"346524200106120420\",\"dnCode\": \"\",\"serviceNumb\":\"20140605153554182556839328756212\"},{\"lockCode\":\"002008\",\"gatewayCode\":\"777777\",\"openMode\": 2,\"timetag\":\"20130902152030\",\"serviceNumb\":\"20130902171824182556839326297963\",\"password\":\"354167\"},{\"lockCode\":\"001003\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170609122030\",\"name\":\"唛哩唛哩轰\",	\"cardNumb\":\"246551321465224561\",\"dnCode\":\"\",\"serviceNumb\":\"20170609153534182556839323061898\"},{\"lockCode\":\"002005\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170806152030\",\"name\":\"青枫浦\",\"cardNumb\":\"82645135486247\",\"dnCode\":\"\",\"serviceNumb\":\"20170806153554182556839328756212\"},{\"lockCode\":\"001003\",\"gatewayCode\":\"888888\",\"openMode\":2,\"timetag\":\"20160709152030\",\"serviceNumb\":\"20160709171824182556839326297963\",\"password\":\"176542\"},{\"lockCode\":\"001002\",\"gatewayCode\": \"888888\",\"openMode\":1,\"timetag\":\"20170210122030\",\"name\":\"陈新浪\",\"cardNumb\":\"3209999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153534182556839323061898\"},{\"lockCode\":\"002001\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170120152030\",\"name\":\"启旭\",\"cardNumb\":\"39999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153554182556839328756212\"},{\"lockCode\":\"002002\",\"gatewayCode\":\"777777\",\"openMode\":2,\"timetag\":\"20170216152030\",\"serviceNumb\":\"20170210171824182556839326297963\",\"password\":\"455885\"},{\"lockCode\":\"002006\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170308122030\",\"name\":\"巴啦啦小魔仙\",\"cardNumb\":\"546254756425476845\",\"dnCode\":\"\",\"serviceNumb\":\"20170308153534182556839323061898\"},{\"lockCode\":\"001005\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170408152030\",\"name\":\"芝麻开门\",\"cardNumb\":\"365412546875445624\",\"dnCode\":\"\",\"serviceNumb\":\"20170408153554182556839328756212\"},{\"lockCode\":\"001001\",\"gatewayCode\":\"888888\",\"openMode\":2,\"timetag\":\"20170406152030\",\"serviceNumb\":\"20170406171824182556839326297963\",\"password\":\"651454\"},{\"lockCode\":\"001004\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20150706122030\",\"name\":\"青弋江\",\"cardNumb\":\"236512199611314213\",\"dnCode\":\"\",\"serviceNumb\":\"20150706153534182556839323061898\"},{\"lockCode\":\"002005\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20140605152030\",\"name\":\"大椿\",\"cardNumb\":\"346524200106120420\",\"dnCode\": \"\",\"serviceNumb\":\"20140605153554182556839328756212\"},{\"lockCode\":\"002003\",\"gatewayCode\":\"777777\",\"openMode\": 2,\"timetag\":\"20130902152030\",\"serviceNumb\":\"20130902171824182556839326297963\",\"password\":\"354167\"},{\"lockCode\":\"001003\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170609122030\",\"name\":\"唛哩唛哩轰\",	\"cardNumb\":\"246551321465224561\",\"dnCode\":\"\",\"serviceNumb\":\"20170609153534182556839323061898\"},{\"lockCode\":\"002004\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170806152030\",\"name\":\"青枫浦\",\"cardNumb\":\"82645135486247\",\"dnCode\":\"\",\"serviceNumb\":\"20170806153554182556839328756212\"},{\"lockCode\":\"001007\",\"gatewayCode\":\"888888\",\"openMode\":2,\"timetag\":\"20160709152030\",\"serviceNumb\":\"20160709171824182556839326297963\",\"password\":\"176542\"}]}";
		*/
	}
	public static LockOperateJson getLockOperate(String ownerPhoneNumber,String startTime, String endTime){
		int sign=26;
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}";
		String result=HttpUtil.postData(data);
		System.out.print('!'+result);
		
		Gson gson=new Gson();
		LockOperateData lockOperateData=gson.fromJson(result, LockOperateData.class);
		
		if(lockOperateData.result!=0){
			System.out.println("获取记录失败");
		}else{
			LockOperateJson lockOperateJson=new LockOperateJson();
			lockOperateJson.rows=new ArrayList();
			List<RecordInfo> list = new ArrayList();
			list.addAll(lockOperateData.recordList);
			lockOperateJson.totals=list.size();
			Iterator it=list.iterator();
			
			while(it.hasNext()){
				RecordInfo recordInfo=(RecordInfo) (it.next());
				LockOperateRecord lockOperateRecord = lockOperateJson.new LockOperateRecord();
				lockOperateRecord.lockCode=recordInfo.lockCode;
				lockOperateRecord.gatewayCode=recordInfo.gatewayCode;
				lockOperateRecord.openMode=recordInfo.openMode;
				lockOperateRecord.timetag=recordInfo.timetag;
				if(recordInfo.openMode==1){
					lockOperateRecord.name=recordInfo.cardInfo.name;
					lockOperateRecord.cardNumb=recordInfo.cardInfo.cardNumb;
					lockOperateRecord.dnCode=recordInfo.cardInfo.dnCode;
					lockOperateRecord.serviceNumb=recordInfo.cardInfo.serviceNumb;
				}else{
					lockOperateRecord.password=recordInfo.passwordInfo.password;
					lockOperateRecord.serviceNumb=recordInfo.passwordInfo.serviceNumb;
				}
				System.out.println("lockOperateRecord="+lockOperateRecord);
				lockOperateJson.rows.add(lockOperateRecord);
			}
			return lockOperateJson;
		}
		return null;
		/*
		String data="{\"totals\":\"48\",\"rows\":[{\"lockCode\":\"001007\",\"gatewayCode\": \"888888\",\"openMode\":1,\"timetag\":\"20170210122030\",\"name\":\"陈新浪\",\"cardNumb\":\"3209999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153534182556839323061898\"},{\"lockCode\":\"001001\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170120152030\",\"name\":\"启旭\",\"cardNumb\":\"39999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153554182556839328756212\"},{\"lockCode\":\"002005\",\"gatewayCode\":\"777777\",\"openMode\":2,\"timetag\":\"20170216152030\",\"serviceNumb\":\"20170210171824182556839326297963\",\"password\":\"455885\"},{\"lockCode\":\"002003\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170308122030\",\"name\":\"巴啦啦小魔仙\",\"cardNumb\":\"546254756425476845\",\"dnCode\":\"\",\"serviceNumb\":\"20170308153534182556839323061898\"},{\"lockCode\":\"002010\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170408152030\",\"name\":\"芝麻开门\",\"cardNumb\":\"365412546875445624\",\"dnCode\":\"\",\"serviceNumb\":\"20170408153554182556839328756212\"},{\"lockCode\":\"002006\",\"gatewayCode\":\"777777\",\"openMode\":2,\"timetag\":\"20170406152030\",\"serviceNumb\":\"20170406171824182556839326297963\",\"password\":\"651454\"},{\"lockCode\":\"001002\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20150706122030\",\"name\":\"青弋江\",\"cardNumb\":\"236512199611314213\",\"dnCode\":\"\",\"serviceNumb\":\"20150706153534182556839323061898\"},{\"lockCode\":\"001005\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20140605152030\",\"name\":\"大椿\",\"cardNumb\":\"346524200106120420\",\"dnCode\": \"\",\"serviceNumb\":\"20140605153554182556839328756212\"},{\"lockCode\":\"001006\",\"gatewayCode\":\"888888\",\"openMode\": 2,\"timetag\":\"20130902152030\",\"serviceNumb\":\"20130902171824182556839326297963\",\"password\":\"354167\"},{\"lockCode\":\"002004\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170609122030\",\"name\":\"唛哩唛哩轰\",	\"cardNumb\":\"246551321465224561\",\"dnCode\":\"\",\"serviceNumb\":\"20170609153534182556839323061898\"},{\"lockCode\":\"002008\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170806152030\",\"name\":\"青枫浦\",\"cardNumb\":\"82645135486247\",\"dnCode\":\"\",\"serviceNumb\":\"20170806153554182556839328756212\"},{\"lockCode\":\"002009\",\"gatewayCode\":\"777777\",\"openMode\":2,\"timetag\":\"20160709152030\",\"serviceNumb\":\"20160709171824182556839326297963\",\"password\":\"176542\"},{\"lockCode\":\"001003\",\"gatewayCode\": \"888888\",\"openMode\":1,\"timetag\":\"20170210122030\",\"name\":\"陈新浪\",\"cardNumb\":\"3209999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153534182556839323061898\"},{\"lockCode\":\"002001\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170120152030\",\"name\":\"启旭\",\"cardNumb\":\"39999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153554182556839328756212\"},{\"lockCode\":\"002007\",\"gatewayCode\":\"777777\",\"openMode\":2,\"timetag\":\"20170216152030\",\"serviceNumb\":\"20170210171824182556839326297963\",\"password\":\"455885\"},{\"lockCode\":\"001004\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170308122030\",\"name\":\"巴啦啦小魔仙\",\"cardNumb\":\"546254756425476845\",\"dnCode\":\"\",\"serviceNumb\":\"20170308153534182556839323061898\"},{\"lockCode\":\"001003\",\"gatewayCode\":\"444444\",\"openMode\":1,\"timetag\":\"20170408152030\",\"name\":\"芝麻开门\",\"cardNumb\":\"365412546875445624\",\"dnCode\":\"\",\"serviceNumb\":\"20170408153554182556839328756212\"},{\"lockCode\":\"001004\",\"gatewayCode\":\"888888\",\"openMode\":2,\"timetag\":\"20170406152030\",\"serviceNumb\":\"20170406171824182556839326297963\",\"password\":\"651454\"},{\"lockCode\":\"002008\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20150706122030\",\"name\":\"青弋江\",\"cardNumb\":\"236512199611314213\",\"dnCode\":\"\",\"serviceNumb\":\"20150706153534182556839323061898\"},{\"lockCode\":\"002005\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20140605152030\",\"name\":\"大椿\",\"cardNumb\":\"346524200106120420\",\"dnCode\": \"\",\"serviceNumb\":\"20140605153554182556839328756212\"},{\"lockCode\":\"002006\",\"gatewayCode\":\"888888\",\"openMode\": 2,\"timetag\":\"20130902152030\",\"serviceNumb\":\"20130902171824182556839326297963\",\"password\":\"354167\"},{\"lockCode\":\"001001\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170609122030\",\"name\":\"唛哩唛哩轰\",	\"cardNumb\":\"246551321465224561\",\"dnCode\":\"\",\"serviceNumb\":\"20170609153534182556839323061898\"},{\"lockCode\":\"001002\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170806152030\",\"name\":\"青枫浦\",\"cardNumb\":\"82645135486247\",\"dnCode\":\"\",\"serviceNumb\":\"20170806153554182556839328756212\"},{\"lockCode\":\"001006\",\"gatewayCode\":\"888888\",\"openMode\":2,\"timetag\":\"20160709152030\",\"serviceNumb\":\"20160709171824182556839326297963\",\"password\":\"176542\"},{\"lockCode\":\"002007\",\"gatewayCode\": \"777777\",\"openMode\":1,\"timetag\":\"20170210122030\",\"name\":\"陈新浪\",\"cardNumb\":\"3209999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153534182556839323061898\"},{\"lockCode\":\"001004\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170120152030\",\"name\":\"启旭\",\"cardNumb\":\"39999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153554182556839328756212\"},{\"lockCode\":\"002010\",\"gatewayCode\":\"777777\",\"openMode\":2,\"timetag\":\"20170216152030\",\"serviceNumb\":\"20170210171824182556839326297963\",\"password\":\"455885\"},{\"lockCode\":\"001002\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170308122030\",\"name\":\"巴啦啦小魔仙\",\"cardNumb\":\"546254756425476845\",\"dnCode\":\"\",\"serviceNumb\":\"20170308153534182556839323061898\"},{\"lockCode\":\"002006\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170408152030\",\"name\":\"芝麻开门\",\"cardNumb\":\"365412546875445624\",\"dnCode\":\"\",\"serviceNumb\":\"20170408153554182556839328756212\"},{\"lockCode\":\"001006\",\"gatewayCode\":\"888888\",\"openMode\":2,\"timetag\":\"20170406152030\",\"serviceNumb\":\"20170406171824182556839326297963\",\"password\":\"651454\"},{\"lockCode\":\"002004\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20150706122030\",\"name\":\"青弋江\",\"cardNumb\":\"236512199611314213\",\"dnCode\":\"\",\"serviceNumb\":\"20150706153534182556839323061898\"},{\"lockCode\":\"002010\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20140605152030\",\"name\":\"大椿\",\"cardNumb\":\"346524200106120420\",\"dnCode\": \"\",\"serviceNumb\":\"20140605153554182556839328756212\"},{\"lockCode\":\"002008\",\"gatewayCode\":\"777777\",\"openMode\": 2,\"timetag\":\"20130902152030\",\"serviceNumb\":\"20130902171824182556839326297963\",\"password\":\"354167\"},{\"lockCode\":\"001003\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170609122030\",\"name\":\"唛哩唛哩轰\",	\"cardNumb\":\"246551321465224561\",\"dnCode\":\"\",\"serviceNumb\":\"20170609153534182556839323061898\"},{\"lockCode\":\"002005\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170806152030\",\"name\":\"青枫浦\",\"cardNumb\":\"82645135486247\",\"dnCode\":\"\",\"serviceNumb\":\"20170806153554182556839328756212\"},{\"lockCode\":\"001003\",\"gatewayCode\":\"888888\",\"openMode\":2,\"timetag\":\"20160709152030\",\"serviceNumb\":\"20160709171824182556839326297963\",\"password\":\"176542\"},{\"lockCode\":\"001002\",\"gatewayCode\": \"888888\",\"openMode\":1,\"timetag\":\"20170210122030\",\"name\":\"陈新浪\",\"cardNumb\":\"3209999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153534182556839323061898\"},{\"lockCode\":\"002001\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170120152030\",\"name\":\"启旭\",\"cardNumb\":\"39999999\",\"dnCode\":\"\",\"serviceNumb\":\"20170210153554182556839328756212\"},{\"lockCode\":\"002002\",\"gatewayCode\":\"777777\",\"openMode\":2,\"timetag\":\"20170216152030\",\"serviceNumb\":\"20170210171824182556839326297963\",\"password\":\"455885\"},{\"lockCode\":\"002006\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170308122030\",\"name\":\"巴啦啦小魔仙\",\"cardNumb\":\"546254756425476845\",\"dnCode\":\"\",\"serviceNumb\":\"20170308153534182556839323061898\"},{\"lockCode\":\"001005\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170408152030\",\"name\":\"芝麻开门\",\"cardNumb\":\"365412546875445624\",\"dnCode\":\"\",\"serviceNumb\":\"20170408153554182556839328756212\"},{\"lockCode\":\"001001\",\"gatewayCode\":\"888888\",\"openMode\":2,\"timetag\":\"20170406152030\",\"serviceNumb\":\"20170406171824182556839326297963\",\"password\":\"651454\"},{\"lockCode\":\"001004\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20150706122030\",\"name\":\"青弋江\",\"cardNumb\":\"236512199611314213\",\"dnCode\":\"\",\"serviceNumb\":\"20150706153534182556839323061898\"},{\"lockCode\":\"002005\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20140605152030\",\"name\":\"大椿\",\"cardNumb\":\"346524200106120420\",\"dnCode\": \"\",\"serviceNumb\":\"20140605153554182556839328756212\"},{\"lockCode\":\"002003\",\"gatewayCode\":\"777777\",\"openMode\": 2,\"timetag\":\"20130902152030\",\"serviceNumb\":\"20130902171824182556839326297963\",\"password\":\"354167\"},{\"lockCode\":\"001003\",\"gatewayCode\":\"888888\",\"openMode\":1,\"timetag\":\"20170609122030\",\"name\":\"唛哩唛哩轰\",	\"cardNumb\":\"246551321465224561\",\"dnCode\":\"\",\"serviceNumb\":\"20170609153534182556839323061898\"},{\"lockCode\":\"002004\",\"gatewayCode\":\"777777\",\"openMode\":1,\"timetag\":\"20170806152030\",\"name\":\"青枫浦\",\"cardNumb\":\"82645135486247\",\"dnCode\":\"\",\"serviceNumb\":\"20170806153554182556839328756212\"},{\"lockCode\":\"001007\",\"gatewayCode\":\"888888\",\"openMode\":2,\"timetag\":\"20160709152030\",\"serviceNumb\":\"20160709171824182556839326297963\",\"password\":\"176542\"}]}";
		Gson gson=new Gson();
		return gson.fromJson(data, LockOperateJson.class);
		*/
	}
	public static List<ServerTreeNode> getDeviceTree(String ownerPhoneNumber){
		String result=DeviceUtil.getDeviceInfo(ownerPhoneNumber);
		Gson gson=new Gson();
		DeviceData deviceData=gson.fromJson(result, DeviceData.class);
		List<ServerTreeNode> NodeList=new ArrayList<ServerTreeNode>();
		if(deviceData.result!=0){
			System.out.println("获取记录失败");
		}else{
			List<DeviceInfo> deviceList=deviceData.devices;
			
			List<ServerTreeNode> gatewayNodeList=new ArrayList<ServerTreeNode>();
			for(int j=0;!deviceList.isEmpty()&&j<deviceList.size();j++){
				DeviceInfo gateway=deviceList.get(j);
				ServerTreeNode gatewayNode=new ServerTreeNode();
				gatewayNode.id=gateway.gatewayCode;
				gatewayNode.text=gateway.gatewayName;
				List<LockInfo> locks=new ArrayList<LockInfo>();
				locks.addAll(gateway.lockLists);
				List<ServerTreeNode> lockNodeList=new ArrayList<ServerTreeNode>();
				gatewayNode.children=new ArrayList<ServerTreeNode>();
				for(int i=0;!locks.isEmpty()&&i<locks.size();i++){
					LockInfo lock=locks.get(i);
					lockNodeList.add(i, new ServerTreeNode());
					lockNodeList.get(i).id=lock.lockCode;
					lockNodeList.get(i).text=lock.lockName;
					gatewayNode.children.add(lockNodeList.get(i));
				}
				NodeList.add(gatewayNode);
			}			
			return NodeList;
		}
		return null;
	}	
	
}
