package com.yishu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yishu.model.NoteResult;
import com.yishu.model.User;
import com.yishu.model.json.LoginJSON;
import com.yishu.service.UserService;
import com.yishu.util.LockOperateUtil;
import com.yishu.util.NoteUtil;
import com.yishu.util.Tea;
import com.yishu.util.UserUtil;

import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Service("userService")
public class UserServiceImpl implements UserService{
	public NoteResult checkLogin(String name,String password){
		Tea tea=new Tea();
		String newPassword=tea.encryptByTea(password);
		String strJSON=UserUtil.CheckLogin(name, newPassword);
		NoteResult result = new NoteResult();
		Gson gson=new Gson();
		LoginJSON loginJSON=gson.fromJson(strJSON, LoginJSON.class);
		System.out.println("ServiceImpl-loginResult:"+loginJSON.getResult());System.out.println("ServiceImpl-ownerName:"+loginJSON.getOwnerName());
	if(loginJSON.getResult()==1){
		result.setStatus(1);
		result.setMsg("登陆失败");
	}else{
		result.setStatus(0);
		result.setMsg("登陆成功");
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("ownerName",loginJSON.getOwnerName());
		String token = NoteUtil.createToken();
		data.put("token", token);
		result.setData(data);
//		userDao.updateToken(data);
	}
	return result;
}
	
	public NoteResult checkLogin(String author) {
		String name = "";
		String password = "";
		try{
			
			String base_msg = author.split(" ")[1];
			
			byte[] bbs = Base64.decode(base_msg);
			String msg = new String(bbs,"UTF-8");
		//	System.out.println(msg);
			
			name = msg.split(":")[0];
			password = msg.split(":")[1];
		}catch(Exception ex){
			NoteResult result = new NoteResult();
			result.setStatus(-1);
			result.setMsg("登陆失败");
			return result;
		}
		
		return checkLogin(name,password);
	}

}
