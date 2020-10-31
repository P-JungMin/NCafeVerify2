package com.pic_e.ncafeverify2.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VerifyDTO {
	private String nickname;
	private String code;
	private String naverId;
	private String time;
	
	public VerifyDTO(String nickname, String code, String naverId) {
		this.nickname = nickname;
		this.code = code;
		this.naverId = naverId;
	}
	
	public VerifyDTO(String nickname, String code, String naverId, String time) {
		this(nickname, code, naverId);
		this.time = time;
	}
	
	public void setTime(Date date) {
		this.time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getNaverId() {
		return this.naverId;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	
	public String getTime() {
		return this.time;
	}
	
	public boolean nameCompare(String target) {
		return this.nickname.equals(target);
	}
}
