package com.yishu.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceUtil {
	private static final Logger logger= LoggerFactory.getLogger(DeviceUtil.class);

	public static String getDeviceInfo(String ownerPhoneNumber) {
//		String ip="112.25.233.122";
		int sign=16;
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
		String result=HttpUtil.postData(data);
		logger.info("util-DeviceInfo"+result);
	//覆盖数据，测试用
		return result;
	}

	//Jackson
	public static String getUnlockAccountInfo(String ownerPhoneNumber,String gatewayCode, String lockCode) {
		String result=null;
//		String ip="112.25.233.122";
		String certiPostData=" {\"sign\":\""+17+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
		String pwdPostData=" {\"sign\":\""+20+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
		String certiResult=HttpUtil.postData(certiPostData);
		String pwdResult=HttpUtil.postData(pwdPostData);
		logger.info("Util-UnlockAccount:"+certiResult+';'+pwdResult);
		
		JsonGenerator jsonGenerator=null;
		ObjectMapper objectMapper=new ObjectMapper();
		try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
		List<LinkedHashMap<String, Object>> authList=new ArrayList();
		//List<LinkedHashMap<String, Object>> list = objectMapper.readValue(json, List.class);
		LinkedHashMap<String, Object> certiMap = null;
		try {
			certiMap = objectMapper.readValue(certiResult, LinkedHashMap.class);
		} catch (JsonParseException e2) {
			e2.printStackTrace();
		} catch (JsonMappingException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		if((Integer)certiMap.get("result")==0){
			List<LinkedHashMap<String, Object>> certiList=(ArrayList) certiMap.get("userList");
			LinkedHashMap<String, Object> newCertiMap=null;
			for(int i=0;i<certiList.size();i++){
				newCertiMap=new LinkedHashMap<String, Object>();
				newCertiMap.put("name",certiList.get(i).get("name"));
				newCertiMap.put("cardNumb",certiList.get(i).get("cardNumb"));
				newCertiMap.put("password",certiList.get(i).get("password"));
				newCertiMap.put("startTime",certiList.get(i).get("startTime"));
				newCertiMap.put("endTime",certiList.get(i).get("endTime"));
				newCertiMap.put("serviceNumb",certiList.get(i).get("serviceNumb"));
				authList.add(newCertiMap);
			}			
		}
		LinkedHashMap<String, Object> pwdMap = null;
		try {
			pwdMap = objectMapper.readValue(pwdResult, LinkedHashMap.class);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if((Integer)pwdMap.get("result")==0){
			List<LinkedHashMap<String, Object>> pwdList=(ArrayList) pwdMap.get("passwordList");
			LinkedHashMap<String, Object> newPwdMap=null;
			for(int i=0;i<pwdList.size();i++){
				newPwdMap=new LinkedHashMap<String, Object>();
//				newCertiList.add(e);
				newPwdMap.put("name",pwdList.get(i).get("name"));
				newPwdMap.put("cardNumb",pwdList.get(i).get("cardNumb"));
				newPwdMap.put("password",pwdList.get(i).get("password"));
				newPwdMap.put("startTime",pwdList.get(i).get("startTime"));
				newPwdMap.put("endTime",pwdList.get(i).get("endTime"));
				newPwdMap.put("serviceNumb",pwdList.get(i).get("serviceNumb"));
				authList.add(newPwdMap);
			}
		}
		LinkedHashMap authMap=new LinkedHashMap();
		authMap.put("totals", authList.size());
		authMap.put("rows", authList);
		try {
			result=objectMapper.writeValueAsString(authMap);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
            if (jsonGenerator != null) {
                jsonGenerator.flush();
            }
            if (!jsonGenerator.isClosed()) {
                jsonGenerator.close();
            }
            jsonGenerator = null;
            objectMapper = null;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return result;
	}
	
	public static String getCertiAuthInfo(String ownerPhoneNumber,String gatewayCode, String lockCode) {
//		String ip="112.25.233.122";
		int sign=17;
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
		String result=HttpUtil.postData(data);
		return result;
	}
	
	public static String getPwdAuthInfo(String ownerPhoneNumber,String gatewayCode, String lockCode) {
//		String ip="112.25.233.122";
		int sign=20;
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
		String result=HttpUtil.postData(data);
		return result;
	}
	
	public static String doCertiAuth(String ownerPhoneNumber,String gatewayCode, String lockCode,String name,String cardNumb,String startTime,String endTime) {
//		String ip="112.25.233.122";
		int sign=18;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		String timetag=simpleDateFormat.format(new Date());
		long num=(long) Math.floor(Math.random()*10000000);
		String serviceNumb=timetag+ownerPhoneNumber+String.valueOf(num);
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"name\":\""+name+"\",\"cardNumb\":\""+cardNumb+"\",\"dnCode\":\"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"timetag\":\""+timetag+"\"}";
		System.out.println("util-doCertiAuth-data:"+data);
		String result=HttpUtil.postData(data);
		return result;
	}
	
	public static String doPwdAuth(String ownerPhoneNumber,String gatewayCode, String lockCode,String password,String startTime,String endTime) {
//		String ip="112.25.233.122";
		int sign=21;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		String timetag=simpleDateFormat.format(new Date());
		long num=(long) Math.floor(Math.random()*10000000);
		String serviceNumb=timetag+ownerPhoneNumber+String.valueOf(num);
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"password\":\""+password+"\",\"dnCode\":\"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"timetag\":\""+timetag+"\"}";
		logger.info("util-doCertiAuth-data:"+data);
		String result=HttpUtil.postData(data);
		return result;
	}
	
	public static String doCertiCancelAuth(String ownerPhoneNumber,String lockCode,String serviceNumb, String cardNumb) {
//		String ip="112.25.233.122";
		int sign=19;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		String timetag=simpleDateFormat.format(new Date());
		long num=(long) Math.floor(Math.random()*10000000);
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"cardNumb\":\""+cardNumb+"\",\"timetag\":\""+timetag+"\"}";
		logger.info("util-doCertiCancelAuth-data:"+data);
		String result=HttpUtil.postData(data);
		logger.info("util-doCertiCancelAuth-result:"+result);
		return result;
	}
	
	public static String doPwdCancelAuth(String ownerPhoneNumber, String lockCode, String gatewayCode, String serviceNumb) {
//		String ip="112.25.233.122";
		int sign=22;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		String timetag=simpleDateFormat.format(new Date());
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"gatewayCode\":\""+gatewayCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"timetag\":\""+timetag+"\"}";
		logger.info("util-doPwdCancelAuth-data:"+data);
		String result=HttpUtil.postData(data);
		return result;
	}
}
