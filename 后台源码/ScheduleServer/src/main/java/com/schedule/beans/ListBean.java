package com.schedule.beans;

import com.schedule.beans.json.SResult;

public class ListBean implements SResult{

	private String cId;
	private String name;
	
	
	
	public ListBean() {
	}
	public ListBean(String cId, String name) {
		this.cId = cId;
		this.name = name;
	}
	public String getcId() {
		return cId;
	}
	public void setcId(String cId) {
		this.cId = cId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
