package com.schedule.beans;

import com.schedule.beans.json.SResult;

public class Image implements SResult {

	private String id;
	private String path;
	
	public Image() {
	}
	public Image(String id, String path) {
		super();
		this.id = id;
		this.path = path;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
