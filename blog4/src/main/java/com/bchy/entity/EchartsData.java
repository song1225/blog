package com.bchy.entity;

public class EchartsData {
	private String name;
	
	private Integer value;
	
	public EchartsData(){
		
	}
	
	public EchartsData(String name, Integer value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
