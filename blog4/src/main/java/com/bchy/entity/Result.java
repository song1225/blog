package com.bchy.entity;

public class Result {

	private Integer code;
 
	private String desc;
	
	private Object data;
	
	public Result(){}
	public Result(Integer code, String desc, Object data) {
		super();
		this.code = code;
		this.desc = desc;
		this.data = data;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}
