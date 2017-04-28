package com.yishu.model;

import java.io.Serializable;

public class OwnerInfo implements Serializable {
	
	private String ownername;
	private String ownerpassword;
	private String phonenumber;
	private String mail;
	private String wechat;
	private String qq;
	private int superior;
	private int grade;
	private String dbip;
	private String superip;
	private String timetag;
	
	public String getOwnername() {
		return ownername;
	}
	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}
	public String getOwnerpassword() {
		return ownerpassword;
	}
	public void setOwnerpassword(String ownerpassword) {
		this.ownerpassword = ownerpassword;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public int getSuperior() {
		return superior;
	}
	public void setSuperior(int superior) {
		this.superior = superior;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getDbip() {
		return dbip;
	}
	public void setDbip(String dbip) {
		this.dbip = dbip;
	}
	public String getSuperip() {
		return superip;
	}
	public void setSuperip(String superip) {
		this.superip = superip;
	}
	public String getTimetag() {
		return timetag;
	}
	public void setTimetag(String timetag) {
		this.timetag = timetag;
	}
	
}
