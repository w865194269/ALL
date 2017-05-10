package com.schedule.beans.json;

import java.util.ArrayList;
import java.util.List;

public class JsonList<T>{

	private int code=200;//返回结果码，200表示成功，-100表示失败，此时需要完善msg提示
	private String msg;
	
	private List<T> result=null;

	public void addCourse(T t){
		if (this.result==null) {
			result=new ArrayList<T>();
		}
		this.result.add(t);
	}
	
	public JsonList() {
		super();
	}

	public JsonList(List<T> result) {
		super();
		this.result = result;
		if (result==null) {
			code=-100;
			msg="未找到"; 
		}
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<T> getResult() {
		if (this.result==null) {
			result=new ArrayList<T>();
		}
		return result;
	}

	public void setResult(List<T> list) {
		this.result = list;
	}
	
}
