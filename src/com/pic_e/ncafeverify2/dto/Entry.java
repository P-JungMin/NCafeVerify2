package com.pic_e.ncafeverify2.dto;

public class Entry {
	private String key;
	private String value;
	
	public Entry(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getValue() {
		return this.value;
	}
}
