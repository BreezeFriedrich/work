package com.yishu.model;

import java.io.Serializable;

public class LockOperate implements Serializable{
	
	private String lockid;
	private String gatewayid;
	private String userid;
	private String operatorid;
	private int openmode;
	private String operatememo;
	private int seqid;
	private String timetag;
	private String timedetail;
	
	public String getLockid() {
		return lockid;
	}

	public void setLockid(String lockid) {
		this.lockid = lockid;
	}

	public String getGatewayid() {
		return gatewayid;
	}

	public void setGatewayid(String gatewayid) {
		this.gatewayid = gatewayid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOperatorid() {
		return operatorid;
	}

	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}

	public int getOpenmode() {
		return openmode;
	}

	public void setOpenmode(int openmode) {
		this.openmode = openmode;
	}

	public String getOperatememo() {
		return operatememo;
	}

	public void setOperatememo(String operatememo) {
		this.operatememo = operatememo;
	}

	public int getSeqid() {
		return seqid;
	}

	public void setSeqid(int seqid) {
		this.seqid = seqid;
	}

	public String getTimetag() {
		return timetag;
	}

	public void setTimetag(String timetag) {
		this.timetag = timetag;
	}

	public String getTimedetail() {
		return timedetail;
	}

	public void setTimedetail(String timedetail) {
		this.timedetail = timedetail;
	}

	public String toString(){
        return "lockid:"+this.lockid + ",gatewayid:" + this.gatewayid+",userid:"+this.userid+",operatorid:"+this.operatorid+",openmode:"+this.openmode+",timetag:"+this.timetag;
    }
	
}
