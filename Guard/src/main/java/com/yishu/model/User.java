package com.yishu.model;

import java.io.Serializable;

public class User implements Serializable{
	private String cn_user_id;
	private String cn_user_name;
	private String cn_user_password;
	private String cn_user_token;
	private String cn_user_desc;
	private String ip;
	private String loginTime;
	public String getCn_user_id() {
		return cn_user_id;
	}
	public void setCn_user_id(String cnUserId) {
		cn_user_id = cnUserId;
	}
	public String getCn_user_name() {
		return cn_user_name;
	}
	public void setCn_user_name(String cnUserName) {
		cn_user_name = cnUserName;
	}
	public String getCn_user_password() {
		return cn_user_password;
	}
	public void setCn_user_password(String cnUserPassword) {
		cn_user_password = cnUserPassword;
	}
	public String getCn_user_token() {
		return cn_user_token;
	}
	public void setCn_user_token(String cnUserToken) {
		cn_user_token = cnUserToken;
	}
	public String getCn_user_desc() {
		return cn_user_desc;
	}
	public void setCn_user_desc(String cnUserDesc) {
		cn_user_desc = cnUserDesc;
	}	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	
	public String toString(){
        return "cn_user_name:"+this.cn_user_name + ",cn_user_password:" + this.cn_user_password+",ip:"+this.ip+",loginTime:"+this.loginTime;
    }
}
